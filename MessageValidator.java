import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.logging.*;


public class MessageValidator {
    public static void main(String[] args) {
        File f=new File("C:\\Users\\CX\\IdeaProjects\\Brainland\\src\\jsonString.txt");
        try(FileReader reader = new FileReader(f))
        {
            char[] buffer = new char[(int)f.length()];
            // считаем файл полностью
            reader.read(buffer);
            String JsonString = buffer.toString();

            Dataset dataset = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
                    .fromJson(JsonString, Dataset.class);


            //System.out.println(JsonString);



            //parse(JsonString);

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
        JsonArray jItem = jobject.getAsJsonArray("param_array");

        for (JsonElement user : jItem) {

            JsonObject userObject = user.getAsJsonObject();
            System.out.println(userObject.get("firstName"));
            System.out.println(userObject.get("lastName"));
        }

    }

    public class Dataset {
        public int param_int;
        public String param_string;
        public boolean param_boolean;
        public double param_double;
        public Map<String, String> param_array;
    }
}
