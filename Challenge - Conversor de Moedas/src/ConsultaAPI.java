import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ConsultaAPI {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/1159e2ffcc8ab9eb123ad05b/latest/USD";

    public Map<String, Double> obterTaxasCambio() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
            JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter fileWriter = new FileWriter("retorno.json")) {
                gson.toJson(jsonResponse, fileWriter);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao escrever arquivo JSON", e);
            }

            try (FileWriter fileWriter = new FileWriter("taxas_cambio.json")) {
                gson.toJson(rates, fileWriter);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao escrever arquivo JSON", e);
            }

            return gson.fromJson(rates, Map.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao obter as taxas de câmbio", e);
        }
    }
}
