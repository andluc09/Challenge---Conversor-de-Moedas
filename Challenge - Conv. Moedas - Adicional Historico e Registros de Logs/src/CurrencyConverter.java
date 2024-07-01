import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    private Map<String, Double> rates;

    public CurrencyConverter(JsonObject ratesJson) {
        rates = new HashMap<>();
        rates.put("USD", ratesJson.getAsJsonObject("conversion_rates").get("USD").getAsDouble());
        rates.put("BRL", ratesJson.getAsJsonObject("conversion_rates").get("BRL").getAsDouble());
        rates.put("JPY", ratesJson.getAsJsonObject("conversion_rates").get("JPY").getAsDouble());
        rates.put("EUR", ratesJson.getAsJsonObject("conversion_rates").get("EUR").getAsDouble());
        rates.put("CAD", ratesJson.getAsJsonObject("conversion_rates").get("CAD").getAsDouble());
        rates.put("SGD", ratesJson.getAsJsonObject("conversion_rates").get("SGD").getAsDouble());
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        double rate = rates.get(toCurrency) / rates.get(fromCurrency);
        return amount * rate;
    }
}
