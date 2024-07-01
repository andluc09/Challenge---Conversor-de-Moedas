import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class ExchangeRateParser {
    public static JsonObject parseRates(String jsonResponse) {
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, JsonObject.class);
    }

    public static void savePrettyJsonToFile(JsonObject jsonObject, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever o arquivo JSON", e);
        }
    }
}
