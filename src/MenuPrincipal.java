import java.util.Scanner;

public class MenuPrincipal {
    private MotorDeCalculo motorcal;
    private Declaracao declarAtual;
    private Scanner teclado;

    public MenuPrincipal(){
        this.teclado = new Scanner(System.in);
        this.motorcal = new MotorDeCalculo();
        this.declarAtual = null;
    }

    public static void main(String[] args){
        MenuPrincipal menu = new MenuPrincipal();
        menu.iniciar();
    }
    public void iniciar(){
        int opcao =-1;
        do {
            System.out.println("\n=========================================");
            System.out.println("    SISTEMA DE GESTÃO E SIMULAÇÃO DE IRS ");
            System.out.println("=========================================");
            System.out.println("1 - Registar Contribuinte e Criar Declaração");
            System.out.println("2 - Adicionar Recibo de Vencimento (Rendimento)");
            System.out.println("3 - Adicionar Fatura (e-Fatura Manual)");
            System.out.println("4 - Simular IRS Final");
            System.out.println("5 - Guardar Declaração no Disco");
            System.out.println("6 - Carregar Declaração do Disco");
            System.out.println("0 - Sair do Programa");
            System.out.println("=========================================");
            System.out.print("Escolha uma opção: ");

            if (teclado.hasNextInt()) {
                opcao = teclado.nextInt();
                teclado.nextLine();

                switch (opcao) {
                    case 1:
                        acaoRegistarContribuinte();
                        break;
                    case 2:
                        acaoAdicionarRendimento();
                        break;
                    case 3:
                        acaoAdicionarDespesa();
                        break;
                    case 4:
                        acaoSimularIRS();
                        break;
                    case 5:
                        acaoGuardarDeclaracao();
                        break;
                    case 6:
                        acaoCarregarDeclaracao();
                        break;
                    case 0:
                        System.out.println("A sair do programa... Até à próxima!");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } else {
                System.out.println("Erro: Deve introduzir um número inteiro.");
                teclado.nextLine();
            }

        } while (opcao != 0);
    }
    private void acaoRegistarContribuinte() {
        System.out.println("\n--- REGISTAR CONTRIBUINTE ---");
        System.out.print("Introduza o Nome Completo: ");
        String nome = teclado.nextLine();

        System.out.print("Introduza o NIF: ");
        String nif = teclado.nextLine();

        System.out.print("Número de Dependentes a Cargo: ");
        int dependentes = teclado.nextInt();
        teclado.nextLine();

        // Mini-menu para mapear o Enum EstadoCivil
        System.out.println("Selecione o Estado Civil:");
        System.out.println("1 - Solteiro | 2 - Casado | 3 - Divorciado | 4 - Viúvo");
        System.out.print("Opção: ");
        int escolhaCivil = teclado.nextInt();
        teclado.nextLine(); // Limpa o buffer

        EstadoCivil estadoCivil;
        switch (escolhaCivil) {
            case 2: estadoCivil = EstadoCivil.CASADO; break;
            case 3: estadoCivil = EstadoCivil.DIVORCIADO; break;
            case 4: estadoCivil = EstadoCivil.VIUVO; break;
            default: estadoCivil = EstadoCivil.SOLTEIRO;
        }

        // Criação dos objetos encadeados
        Contribuinte titular = new Contribuinte(nif, nome, estadoCivil, dependentes);
        this.declarAtual = new Declaracao(titular, 2026); // Ano fiscal corrente

        System.out.println("Perfil do Contribuinte e Declaração de 2026 criados com sucesso!");
    }

    /**
     * Opção 2: Adiciona um rendimento à lista da declaração ativa.
     */
    private void acaoAdicionarRendimento() {
        if (validarDeclaracaoAtiva()) {
            System.out.println("\n--- ADICIONAR RENDIMENTO ---");

            System.out.println("Selecione a Categoria do Rendimento:");
            System.out.println("1 - Categoria A (Trabalho Dependente)");
            System.out.println("2 - Categoria B (Trabalho Independente)");
            System.out.println("3 - Categoria H (Pensões)");
            System.out.print("Opção: ");
            int escolhaCat = teclado.nextInt();
            teclado.nextLine();

            CategoriaRendimento categoria;
            switch (escolhaCat) {
                case 2: categoria = CategoriaRendimento.B; break;
                case 3: categoria = CategoriaRendimento.C; break;
                default: categoria = CategoriaRendimento.A;
            }

            System.out.print("Introduza o Valor Bruto Total (€): ");
            double valorBruto = teclado.nextDouble();

            System.out.print("Introduza o Valor Retido na Fonte (€): ");
            double retencao = teclado.nextDouble();

            System.out.print("Introduza os Descontos para a Segurança Social (€): ");
            double segSocial = teclado.nextDouble();
            teclado.nextLine();

            Rendimento rendimento = new Rendimento(categoria, valorBruto, retencao, segSocial);
            declarAtual.adicionarRendimento(rendimento);

            System.out.println("Rendimento registado e acoplado à declaração!");
        }
    }

    private void acaoAdicionarDespesa() {
        if (validarDeclaracaoAtiva()) {
            System.out.println("\n--- e-FATURA (Inserção Manual) ---");
            System.out.print("Introduza o NIF do Comerciante: ");
            String nifComerciante = teclado.nextLine();

            System.out.print("Introduza o Valor Total da Fatura (€): ");
            double valor = teclado.nextDouble();
            teclado.nextLine(); // Limpa o buffer

            System.out.println("Selecione o Tipo de Despesa:");
            System.out.println("1 - Saúde | 2 - Educação | 3 - Habitação | 4 - Geral / Outros");
            System.out.print("Opção: ");
            int escolhaTipo = teclado.nextInt();
            teclado.nextLine();

            TipoDespesa tipo;
            switch (escolhaTipo) {
                case 1: tipo = TipoDespesa.SAUDE; break;
                case 2: tipo = TipoDespesa.EDUCACAO; break;
                case 3: tipo = TipoDespesa.HABITACAO; break;
                default: tipo = TipoDespesa.GERAL;
            }

            Despesa despesa = new Despesa(tipo, valor, nifComerciante);
            declarAtual.adicionarDespesas(despesa);

            System.out.println(" Fatura registada e validada no e-Fatura local!");
        }
    }

    /**
     * Opção 4: Comunica com o MotorDeCalculo e exibe o veredicto.
     */
    private void acaoSimularIRS() {
        if (validarDeclaracaoAtiva()) {
            System.out.println("\n--- SIMULAÇÃO FINAL DE IRS ---");

            // Invoca o Maestro da Fase 2
            double resultadoFinal = motorcal.calcularResultadoFinal(declarAtual);

            System.out.println("-----------------------------------------");
            System.out.println("Contribuinte: " + declarAtual.getContribuinte().getNome());
            System.out.println("NIF: " + declarAtual.getContribuinte().getNif());
            System.out.println("-----------------------------------------");

            if (resultadoFinal < 0) {
                // Se der negativo, o Estado reembolsa o cidadão
                double reembolso = Math.abs(resultadoFinal);
                System.out.printf("CÁLCULO TERMINADO: Tem direito a REEMBOLSO.%n");
                System.out.printf("Valor a receber do Estado: %.2f €%n", reembolso);
            } else {
                System.out.printf("CÁLCULO TERMINADO: Tem imposto em falta.%n");
                System.out.printf("Valor a pagar ao Estado: %.2f €%n", resultadoFinal);
            }
            System.out.println("-----------------------------------------");
        }
    }


    private boolean validarDeclaracaoAtiva() {
        if (this.declarAtual == null) {
            System.out.println(" Erro: Deve primeiro registar o Contribuinte (Opção 1) antes de prosseguir.");
            return false;
        }
        return true;
    }
    private void acaoGuardarDeclaracao() {
        if (validarDeclaracaoAtiva()) {
            boolean sucesso = GestorFicheiros.guardarDeclaracao(declarAtual);
            if (sucesso) {
                System.out.println("Declaração e todas as faturas guardadas com sucesso no disco!");
            }
        }
    }

    private void acaoCarregarDeclaracao() {
        Declaracao carregada = GestorFicheiros.carregarDeclaracao();
        if (carregada != null) {
            this.declarAtual = carregada;
            System.out.println("Bem-vindo de volta, " + declarAtual.getContribuinte().getNome() + "! Dados carregados.");
        } else {
            System.out.println("Nenhum dado encontrado. Tem a certeza que já guardou alguma declaração?");
        }
    }
}