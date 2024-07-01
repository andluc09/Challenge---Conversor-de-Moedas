import java.util.Map;
import java.util.Scanner;

public class Menu {
    private final ConsultaAPI consultaAPI;
    private final Map<String, Double> taxasCambio;
    private final Moeda[] moedas = {
            new Moeda("USD", "Dólar Americano"),
            new Moeda("BRL", "Real Brasileiro"),
            new Moeda("JPY", "Iene"),
            new Moeda("EUR", "Euro"),
            new Moeda("CAD", "Dólar Canadense"),
            new Moeda("SGD", "Dólar de Singapura")
    };

    public Menu() {
        consultaAPI = new ConsultaAPI();
        taxasCambio = consultaAPI.obterTaxasCambio();
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("————————————————————————————————————————————————————————");
            System.out.println("  ㅤㅤ\uD83D\uDCB1ㅤSeja bem-vindo(a) ao Conversor de Moeda 😃ㅤ ");
            System.out.println("————————————————————————————————————————————————————————");
            System.out.println("\n ㅤㅤㅤ ㅤㅤㅤㅤㅤㅤ Moedas disponíveis:  ㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤ\n");

            for (int i = 0; i < moedas.length; i++) {
                System.out.printf("%d) %s\n", i + 1, moedas[i].codigo() + " – " + moedas[i].nome());
            }
            System.out.println((moedas.length + 1) + ") Sair");
            System.out.println("————————————————————————————————————————————————————————");
            System.out.print("Escolha uma opção válida: ");
            int opcao = scanner.nextInt();

            if (opcao == moedas.length + 1) {
                System.out.println("\nSaindo...");
                break;
            }

            if (opcao < 1 || opcao > moedas.length) {
                System.out.println("\nOpção inválida, tente novamente.");
                continue;
            }

            Moeda moedaOrigem = moedas[opcao - 1];
            System.out.print("\nDigite o valor em " + moedaOrigem.nome() + ": ");
            double valorOrigem = scanner.nextDouble();

            System.out.println("————————————————————————————————————————————————————————");
            System.out.println("\nConverter para:");
            for (int i = 0; i < moedas.length; i++) {
                if (i != opcao - 1) {
                    System.out.printf("%d) %s\n", i + 1,  moedas[i].codigo() + " – " + moedas[i].nome());
                }
            }

            System.out.println("————————————————————————————————————————————————————————");
            System.out.print("Escolha uma opção válida: ");
            int opcaoDestino = scanner.nextInt();

            if (opcaoDestino < 1 || opcaoDestino > moedas.length || opcaoDestino == opcao) {
                System.out.println("\nOpção inválida, tente novamente.");
                continue;
            }

            Moeda moedaDestino = moedas[opcaoDestino - 1];
            double taxaOrigem = taxasCambio.get(moedaOrigem.codigo());
            double taxaDestino = taxasCambio.get(moedaDestino.codigo());
            double valorConvertido = converterMoeda(valorOrigem, taxaOrigem, taxaDestino);

            System.out.printf("\n %.2f %s (%s) = %.2f %s (%s) \n", valorOrigem, moedaOrigem.nome(),
                    moedaOrigem.codigo(), valorConvertido, moedaDestino.nome(), moedaDestino.codigo());
        }
    }

    private double converterMoeda(double valorOrigem, double taxaOrigem, double taxaDestino) {
        return valorOrigem * (taxaDestino / taxaOrigem);
    }
}
