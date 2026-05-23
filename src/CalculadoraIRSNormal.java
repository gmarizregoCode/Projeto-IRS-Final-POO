import java.util.ArrayList;
import java.util.List;

public class CalculadoraIRSNormal implements EstrategiaFiscal {
    private List<Escalao> tabelaEscaloes;
    // O valor correto da Dedução Específica para coincidir com o simulador (Deve ser atualizado também em RendimentoCategoriaA)
    private final double deducaoCategA = 4462.15;

    public CalculadoraIRSNormal(){
        this.tabelaEscaloes = new ArrayList<>();
        // Integração dos 9 escalões oficiais
        this.tabelaEscaloes.add(new Escalao(0, 8059, 0.125, 0));
        this.tabelaEscaloes.add(new Escalao(8059, 12160, 0.16, 282.07));
        this.tabelaEscaloes.add(new Escalao(12160, 17233, 0.215, 950.91));
        this.tabelaEscaloes.add(new Escalao(17233, 22306, 0.244, 1450.67));
        this.tabelaEscaloes.add(new Escalao(22306, 28400, 0.314, 3011.98));
        this.tabelaEscaloes.add(new Escalao(28400, 41629, 0.349, 4006.10));
        this.tabelaEscaloes.add(new Escalao(41629, 44987, 0.431, 7419.54));
        this.tabelaEscaloes.add(new Escalao(44987, 83696, 0.446, 8094.51));
        this.tabelaEscaloes.add(new Escalao(83696, Double.MAX_VALUE, 0.48, 10939.90));
    }

    private double calcularRendimentoColetavel(Declaracao d){
        double totalRC = 0;
        for (Rendimento r : d.getRendimentos()) {
            totalRC += r.getRendimentoColetavel();
        }
        return totalRC;
    }

    private Escalao encontrarEscalao(double rc){
        for(Escalao e: tabelaEscaloes){
            if(e.isDentro(rc)){
                return e;
            }
        }
        return null;
    }

    private double calcularColetaBruta(double rc, Escalao e){
        return (rc * e.getTaxaMarginal()) - e.getParcelaAbater();
    }

    private double calcularDeducoes(Declaracao d) {
        double deducaoSaude = 0;
        double deducaoEducacao = 0;
        double deducaoHabitacao = 0;
        double deducaoGeral = 0;

        for (Despesa desp : d.getDespesas()) {
            switch (desp.getTipoDespesa()) {
                case SAUDE: deducaoSaude += desp.getValor() * 0.15; break;
                case EDUCACAO: deducaoEducacao += desp.getValor() * 0.30; break;
                case HABITACAO: deducaoHabitacao += desp.getValor() * 0.15; break;
                case GERAL: deducaoGeral += desp.getValor() * 0.35; break;
            }
        }

        deducaoSaude = Math.min(deducaoSaude, 1000.0);
        deducaoEducacao = Math.min(deducaoEducacao, 800.0);
        deducaoHabitacao = Math.min(deducaoHabitacao, 296.0);
        deducaoGeral = Math.min(deducaoGeral, 250.0);

        return deducaoSaude + deducaoEducacao + deducaoHabitacao + deducaoGeral;
    }

    @Override
    public double calcularImpostoFinal(Declaracao d){
        double rc = calcularRendimentoColetavel(d);

        if (rc <= 0) {
            return -d.somarRetencaoTotal();
        }

        Escalao escalaoCorreto = encontrarEscalao(rc);
        if (escalaoCorreto == null) {
            return -d.somarRetencaoTotal();
        }

        double coletaBruta = calcularColetaBruta(rc, escalaoCorreto);

        double totalDeducoesFaturas = calcularDeducoes(d);
        double coletaLiquida = coletaBruta - totalDeducoesFaturas;

        if (coletaLiquida < 0) {
            coletaLiquida = 0; // A coleta líquida nunca pode ser negativa (o Estado não te dá dinheiro só por teres faturas)
        }

        return coletaLiquida - d.somarRetencaoTotal();
    }
}
