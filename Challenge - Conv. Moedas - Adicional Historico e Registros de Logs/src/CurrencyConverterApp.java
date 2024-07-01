import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class CurrencyConverterApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> history = new ArrayList<>();

    public static void main(String[] args) {
        try {
            String jsonResponse = ExchangeRateAPI.getRates();
            JsonObject jsonObject = ExchangeRateParser.parseRates(jsonResponse);
            ExchangeRateParser.savePrettyJsonToFile(jsonObject, "taxas_cambio.json");
            CurrencyConverter converter = new CurrencyConverter(jsonObject);

            boolean running = true;
            while (running) {
                printMenu();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        performConversion(converter, "USD", "BRL");
                        break;
                    case 2:
                        performConversion(converter, "BRL", "USD");
                        break;
                    case 3:
                        performConversion(converter, "JPY", "BRL");
                        break;
                    case 4:
                        performConversion(converter, "BRL", "JPY");
                        break;
                    case 5:
                        performConversion(converter, "EUR", "CAD");
                        break;
                    case 6:
                        performConversion(converter, "CAD", "EUR");
                        break;
                    case 7:
                        performConversion(converter, "SGD", "EUR");
                        break;
                    case 8:
                        performConversion(converter, "EUR", "SGD");
                        break;
                    case 9:
                        System.out.println("\nSaindo...");
                        running = false;
                        break;
                    default:
                        System.out.println("\nOpção inválida, tente novamente. ");
                }
                if (running) {
                    printHistory();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("\n————————————————————————————————————————————————————————");
        System.out.println("  ㅤㅤ\uD83D\uDCB1ㅤSeja bem-vindo(a) ao Conversor de Moeda 😃");
        System.out.println("————————————————————————————————————————————————————————");
        System.out.println("\n ㅤㅤㅤ ㅤㅤㅤㅤㅤㅤ Moedas disponíveis:  ㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤ\n");
        System.out.println("1) Dólar             ㅤ——>ㅤ Real brasileiro");
        System.out.println("2) Real brasileiro   ㅤ——>ㅤ Dólar");
        System.out.println("3) Iene              ㅤ——>ㅤ Real brasileiro");
        System.out.println("4) Real brasileiro   ㅤ——>ㅤ Iene");
        System.out.println("5) Euro              ㅤ——>ㅤ Dólar canadense");
        System.out.println("6) Dólar canadense   ㅤ——>ㅤ Euro");
        System.out.println("7) Dólar de Singapuraㅤ——>ㅤ Euro");
        System.out.println("8) Euro              ㅤ——>ㅤ Dólar de Singapura");
        System.out.println("9) Sair");
        System.out.println("————————————————————————————————————————————————————————");
        System.out.print("Escolha uma opção válida: ");
    }

    private static void performConversion(CurrencyConverter converter, String fromCurrency, String toCurrency) {
        System.out.print("\nDigite o valor a ser convertido: ");
        double amount = scanner.nextDouble();
        double convertedAmount = converter.convert(fromCurrency, toCurrency, amount);
        LocalDateTime now = LocalDateTime.now();
        String log = String.format("%s: %.2f %s -> %.2f %s", now, amount, fromCurrency, convertedAmount, toCurrency);
        history.add(log);
        System.out.println(log);
        saveLogToFile(log);
    }

    private static void printHistory() {
        System.out.println("\nHistórico de Conversões:");
        for (String log : history) {
            System.out.println(log);
        }
    }

    private static void saveLogToFile(String log) {
        try (FileWriter writer = new FileWriter("log.txt", true)) { // 'true' para append
            writer.write(log + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o log: " + e.getMessage());
        }
    }
}
