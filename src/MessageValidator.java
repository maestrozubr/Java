import com.google.gson.*;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;


public class MessageValidator {
    public static void main(String[] args) throws  Exception{

        String[] error_messages = {
                error_message.array_error_message,
                error_message.int_error_message,
                error_message.string_error_message,
                error_message.boolean_error_message,
                error_message.double_error_message,
                error_message.json_error_message
        };


        File source_file = new File("jsonString.txt");

        String jsonString = "";
        try(FileReader reader = new FileReader(source_file))
        {
            BufferedReader breader = new BufferedReader(reader);
            String s;
            while ((s = breader.readLine()) != null)
            {
                jsonString = jsonString + s;// + "\n";
            }

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        JsonObject jsonObject = parse(jsonString);

        int errorNumber = check(jsonObject);

        try
        {
            File log_errors = new File("error.log");
            BufferedWriter bwriter_log_errors = new BufferedWriter(new FileWriter(log_errors, true));
            // true - без перезаписи файла
            File log_access = new File("access.log");
            BufferedWriter bwriter_log_access = new BufferedWriter(new FileWriter(log_access, true));
            if (errorNumber != -1) {
                bwriter_log_errors.write((new Date()).toString() + "\n");
                bwriter_log_errors.write(error_messages[errorNumber] + "\n");
                bwriter_log_errors.flush();
            } else {
                bwriter_log_access.write((new Date()).toString() + "\n");
                bwriter_log_access.write("checking completed. No errors found\n");
                bwriter_log_access.flush();
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    public static int check(JsonObject jObject) {

        if (!jObject.get("param_int").getAsJsonPrimitive().isNumber()) {
            return error.int_error;
        } else {
            //первой проверки недостаточно, потому что число может быть не Integer

            double param_int = jObject.get("param_int").getAsJsonPrimitive().getAsDouble();

            if (!(Math.floor(param_int) == param_int)) {
                return error.int_error;
            }

        }

        // Все параметры по умолчанию строки, поэтому проверять на строку просто так бессмысленно, нужны критерии
        /*try {
            String param_string = jObject.get("param_string").getAsJsonPrimitive().getAsString();
            //здесь исключение никогда не выбросится
        } catch (ClassCastException cce) {
            System.out.println(cce.getMessage());
            return error.string_error;
        } catch (IllegalStateException ise) {
            System.out.println(ise.getMessage());
            return error.string_error;
        }*/

        if (!jObject.get("param_boolean").getAsJsonPrimitive().isBoolean()) {
            return error.boolean_error;
        }

        if (!jObject.get("param_int").getAsJsonPrimitive().isNumber()) {
            return error.double_error;
        }

        try
        {
            JsonArray array = jObject.getAsJsonArray("param_array");
        }
        catch (IllegalStateException ise)
        {
            return error.array_error;
        }

        //проверка ошибки парсинга

        System.out.println(jObject.get("param_json").toString());
        try
        {
            JsonParser parser = new JsonParser();
            JsonObject jobject = parser.parse(jObject.get("param_json").toString()).getAsJsonObject();
        }
        catch (Exception e)
        {
            return error.json_error;
        }

        return -1;
    }

    public static JsonObject parse(String jsonLine) {
        JsonParser parser = new JsonParser();
        JsonObject jobject;

        //проверка ошибки парсинга
        try
        {
            jobject = parser.parse(jsonLine).getAsJsonObject();
        }
        catch (JsonSyntaxException jse)
        {
            System.out.println(jse.getMessage());
            jobject = null;
        }
        catch (JsonParseException jpe)
        {
            System.out.println(jpe.getMessage());
            jobject = null;
        }

        return jobject;

    }

    public static class error {
        public final static int array_error = 0;
        public final static int int_error = 1;
        public final static int string_error = 2;
        public final static int boolean_error = 3;
        public final static int double_error = 4;
        public final static int json_error = 5;
    }

    public static class error_message {
        public final static String array_error_message = "Array error: parameter must be of array type.";
        public final static String int_error_message = "Integer error: parameter must be of integer type.";
        public final static String string_error_message = "String error: parameter must be of string type.";
        public final static String boolean_error_message = "Boolean error: parameter must be of boolean type.";
        public final static String double_error_message = "Double error: parameter must be of double type.";
        public final static String json_error_message = "Json-string error: parameter must be a json-string.";

    }

}
