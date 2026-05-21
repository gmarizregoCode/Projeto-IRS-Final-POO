import java.util.Scanner;

public class LeitorSeguro {
    private Scanner scanner;

    public LeitorSeguro() {
        this.scanner = new Scanner(System.in);
    }

    public String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input); // Tenta converter o texto para Inteiro
            } catch (NumberFormatException e) {
                System.out.println("Erro de input: Por favor, introduza apenas números inteiros.");
            }
        }
    }

    public double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                // Substitui vírgulas por pontos automaticamente para evitar erros de formatação
                String input = scanner.nextLine().replace(",", ".").trim();
                return Double.parseDouble(input); // Tenta converter para Decimal
            } catch (NumberFormatException e) {
                System.out.println("Erro de input: Por favor, introduza um valor numérico válido (ex: 1500.50).");
            }
        }
    }

    /**
     * Garante que o utilizador não deixa a String em branco (só dar Enter).
     */
    public String lerTextoNaoVazio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Erro: Este campo é de preenchimento obrigatório.");
        }
    }

    /**
     * Garante que o valor numérico é estritamente positivo (ex: para faturas ou rendimentos).
     */
    public double lerDoublePositivo(String mensagem) {
        while (true) {
            double valor = lerDouble(mensagem); // Usa o método que já tínhamos feito!
            if (valor >= 0) {
                return valor;
            }
            System.out.println("Erro: O valor não pode ser negativo.");
        }
    }

    /**
     * Excelente para Menus! Obriga o utilizador a escolher uma opção dentro de um limite.
     */
    public int lerInteiroComLimites(String mensagem, int min, int max) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.println("Erro: Opção inválida. Escolha um número entre " + min + " e " + max + ".");
        }
    }
}
