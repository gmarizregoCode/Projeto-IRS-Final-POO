import java.time.Year;
import java.util.List;

public class MenuPrincipal {

    private BaseDeDados bd;
    private Contribuinte utilizadorLogado;
    private Declaracao declarAtual;
    private LeitorSeguro teclado;

    public MenuPrincipal() {
        this.teclado = new LeitorSeguro();


        // 1. A MAGIA ACONTECE AQUI: Ao arrancar, o programa vai ao disco
        // e carrega o HashMap inteiro com todos os utilizadores!
        this.bd = GestorFicheiros.carregarBaseDeDados();
        this.utilizadorLogado = null;
        this.declarAtual = null;
    }

    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.iniciar();
    }

    public void iniciar() {
        int opcao = -1;
        do {
            // Se não houver ninguém logado, mostra o ecrã de Login
            if (utilizadorLogado == null) {
                opcao = mostrarMenuAutenticacao();
            } else {
                // Se alguém fez login, mostra o menu do IRS
                opcao = mostrarMenuSessao();
            }
        } while (opcao != 0);

        // 2. SEGURANÇA: Quando o utilizador pressiona '0' para sair,
        // o programa grava toda a Base de Dados no disco automaticamente.
        GestorFicheiros.guardarBaseDeDados(bd);
        System.out.println("A sair do programa... Dados guardados em segurança. Até à próxima!");
    }

    // ==========================================
    // ECRÃ 1: AUTENTICAÇÃO (LOGIN / REGISTO)
    // ==========================================
    private int mostrarMenuAutenticacao() {
        System.out.println("\n=========================================");
        System.out.println("    PORTAL DAS FINANÇAS - ACESSO");
        System.out.println("=========================================");
        System.out.println("1 - Iniciar Sessão (Login)");
        System.out.println("2 - Registar Novo Contribuinte");
        System.out.println("0 - Sair do Programa");

        int opcao = teclado.lerInteiro("Escolha uma opção: ");

        switch (opcao) {
            case 1: acaoLogin(); break;
            case 2: acaoRegistarContribuinte(); break;
            case 0: break;
            default: System.out.println("Opção inválida!");
        }
        return opcao;
    }

    private void acaoLogin() {
        String nif = teclado.lerTexto("Introduza o NIF: ");
        String senha = teclado.lerTexto("Introduza a Senha: ");

        Contribuinte c = bd.autenticar(nif, senha);

        if (c != null) {
            this.utilizadorLogado = c;
            System.out.println("Login efetuado com sucesso! Bem-vindo(a), " + c.getNome());

            int anoAtual = Year.now().getValue();

            // Procura a declaração do ano atual no histórico desta pessoa
            this.declarAtual = null;
            for (Declaracao d : c.getHistoricoDeclaracoes()) {
                if (d.getAnoFiscal() == anoAtual) {
                    this.declarAtual = d;
                    break;
                }
            }
            // Se a pessoa é nova e não tem declaração do ano atual, cria uma automaticamente
            if (this.declarAtual == null) {
                this.declarAtual = new Declaracao(c, anoAtual);
                c.adicionarDeclaracao(this.declarAtual);
            }
        } else {
            System.out.println("Erro: NIF inexistente ou senha incorreta.");
        }
    }

    private void acaoRegistarContribuinte() {
        System.out.println("\n--- REGISTAR NOVO CONTRIBUINTE ---");
        String nif = teclado.lerTexto("NIF: ");
        String senha = teclado.lerTexto("Crie uma Senha: ");
        String nome = teclado.lerTexto("Nome Completo: ");
        int idade = teclado.lerInteiro("Idade: ");
        int dependentes = teclado.lerInteiro("Número de Dependentes a Cargo: ");

        System.out.println("Estado Civil (1-Solteiro | 2-Casado | 3-Divorciado | 4-Viúvo): ");
        int escolhaCivil = teclado.lerInteiro("Opção: ");

        EstadoCivil estadoCivil;
        switch (escolhaCivil) {
            case 2: estadoCivil = EstadoCivil.CASADO; break;
            case 3: estadoCivil = EstadoCivil.DIVORCIADO; break;
            case 4: estadoCivil = EstadoCivil.VIUVO; break;
            default: estadoCivil = EstadoCivil.SOLTEIRO;
        }

        Contribuinte titular = new Contribuinte(nome, nif, idade, senha, estadoCivil, dependentes);

        try {
            bd.registarContribuinte(titular);
            System.out.println("Conta criada com sucesso! Pode agora fazer Login com o seu NIF.");
            GestorFicheiros.guardarBaseDeDados(bd); // Salva imediatamente no disco

        } catch (NifInvalidoException | NifDuplicadoException e) {
            // Apanha QUALQUER UM dos teus dois erros customizados e mostra a mensagem vermelha!
            System.out.println("Erro de Registo: " + e.getMessage());
        }
    }

    // ==========================================
    // ECRÃ 2: SESSÃO INICIADA (MENU DE IRS)
    // ==========================================
    private int mostrarMenuSessao() {
        int anoAtual = Year.now().getValue();
        System.out.println("\n=========================================");
        System.out.println("   BEM-VINDO, " + utilizadorLogado.getNome().toUpperCase());
        System.out.println("=========================================");
        System.out.println("1 - Adicionar Recibo de Vencimento (Rendimento)");
        System.out.println("2 - Adicionar Fatura (e-Fatura Manual)");
        System.out.println("3 - Importar e-Fatura (Ficheiro XML)");
        System.out.println("4 - Simular IRS Final (" + anoAtual + ")");
        System.out.println("9 - Terminar Sessão (Logout)");
        System.out.println("=========================================");

        int opcao = teclado.lerInteiro("Escolha uma opção: ");

        switch (opcao) {
            case 1: acaoAdicionarRendimento(); break;
            case 2: acaoAdicionarDespesa(); break;
            case 3: acaoImportarXML(); break;
            case 4: acaoSimularIRS(); break;
            case 9:
                System.out.println("A encerrar sessão de " + utilizadorLogado.getNome() + "...");
                this.utilizadorLogado = null;
                this.declarAtual = null;
                break;
            default: System.out.println("Opção inválida!");
        }
        // Retornamos -1 para evitar que o programa saia (só sai se for 0 no menu principal)
        return -1;
    }

    private void acaoAdicionarRendimento() {
        System.out.println("\n--- ADICIONAR RENDIMENTO ---");
        System.out.println("Categoria (1-Cat A | 2-Cat B | 3-Cat H): ");
        int escolhaCat = teclado.lerInteiro("");

        double valorBruto = teclado.lerDouble("Valor Bruto Total (€): ");
        double retencao = teclado.lerDouble("Valor Retido na Fonte (€): ");
        double segSocial = teclado.lerDouble("Descontos para a Segurança Social (€): ");

        Rendimento rendimento;
        switch (escolhaCat) {
            case 2: rendimento = new RendimentoCategoriaB(valorBruto, retencao, segSocial); break;
            case 3: rendimento = new RendimentoCategoriaH(valorBruto, retencao, segSocial); break;
            default: rendimento = new RendimentoCategoriaA(valorBruto, retencao, segSocial);
        }
        declarAtual.adicionarRendimento(rendimento);
        System.out.println("Rendimento acoplado à sua declaração!");
    }

    private void acaoAdicionarDespesa() {
        System.out.println("\n--- INSERIR FATURA MANUAL ---");
        String nifComerciante = teclado.lerTexto("NIF do Comerciante: ");
        double valor = teclado.lerDouble("Valor Total (€): ");

        System.out.println("Tipo (1-Saúde | 2-Educação | 3-Habitação | 4-Geral): ");
        int escolhaTipo = teclado.lerInteiro("");

        TipoDespesa tipo;
        switch (escolhaTipo) {
            case 1: tipo = TipoDespesa.SAUDE; break;
            case 2: tipo = TipoDespesa.EDUCACAO; break;
            case 3: tipo = TipoDespesa.HABITACAO; break;
            default: tipo = TipoDespesa.GERAL;
        }

        Despesa despesa = new Despesa(tipo, valor, nifComerciante);
        declarAtual.adicionarDespesas(despesa);
        System.out.println("Fatura registada com sucesso!");
    }

    private void acaoImportarXML() {
        System.out.println("\n--- A INICIAR IMPORTAÇÃO DO E-FATURA ---");
        List<Despesa> faturasLidas = ImportadorXML.importarFaturas("faturas.xml");

        if (faturasLidas.isEmpty()) {
            System.out.println("Não foi possível importar faturas.");
        } else {
            for (Despesa d : faturasLidas) {
                declarAtual.adicionarDespesas(d);
            }
            System.out.println("SUCESSO: " + faturasLidas.size() + " faturas associadas ao seu NIF!");
        }
    }

    private void acaoSimularIRS() {
        System.out.println("\n--- SIMULAÇÃO DE IRS: " + utilizadorLogado.getNome() + " ---");
        // 1. A Fábrica analisa o Contribuinte e devolve o motor de cálculo perfeito para ele!
        EstrategiaFiscal motorDeCalculo = EstrategiaFactory.criarEstrategia(utilizadorLogado);

        // 2. Usamos o motor (seja ele o Jovem ou o Normal) para calcular o imposto final
        double resultadoFinal = motorDeCalculo.calcularImpostoFinal(declarAtual);

        if (resultadoFinal < 0) {
            System.out.printf("Tem direito a REEMBOLSO de: %.2f €%n", Math.abs(resultadoFinal));
        } else {
            System.out.printf("Tem imposto A PAGAR de: %.2f €%n", resultadoFinal);
        }
        System.out.println("-----------------------------------------");
    }
}
