import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;


public class MessageValidator {
    public static void main(String[] args) {

        Dataset datasetEx = new Dataset();
        datasetEx.param_int = 12;
        datasetEx.param_string = "string";
        datasetEx.param_boolean = true;
        datasetEx.param_double = 12.0;
        Map<String, String> mapEx = new HashMap<>();
        mapEx.put("firstName", "Ivan");
        mapEx.put("lastName", "Ivanov");
        datasetEx.param_array = mapEx;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(datasetEx));
        String JSONString = gson.toJson(datasetEx);

        File f=new File("C:\\Users\\CX\\IdeaProjects\\Brainland\\src\\jsonString.txt");
        try(FileReader reader = new FileReader(f))
        {
            char[] buffer = new char[(int)f.length()];
            // считаем файл полностью
            reader.read(buffer);
            String JsonString = buffer.toString();

            /*Dataset dataset = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
                    .fromJson(JsonString, Dataset.class);*/


            //System.out.println(JsonString);



            parse(JSONString);

        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }

    }

    public static int check(){
        return 0;
    }

    public static void parse(String jsonLine) {

        JsonParser parser = new JsonParser();
        JsonObject jobject = parser.parse(jsonLine).getAsJsonObject();
        System.out.println(jobject.get("param_int").getAsInt());
        System.out.println(jobject.get("param_string").getAsString());
        System.out.println(jobject.get("param_boolean").getAsBoolean());
        System.out.println(jobject.get("param_double").getAsDouble());

        JsonObject array = jobject.getAsJsonObject("param_array");
        System.out.println(array.get("firstName").getAsString());
        System.out.println(array.get("lastName").getAsString());
       
    }

    public static class Dataset {
        public int param_int;
        public String param_string;
        public boolean param_boolean;
        public double param_double;
        public Map<String, String> param_array;
    }
}
