import java.util.ArrayList;
import java.util.List;

public class MotorDeCalculo {
    private List<Escalao> tabelaEscaloes;
    private final double deducaoCategA = 4104.00;

    public MotorDeCalculo(){
        this.tabelaEscaloes = new ArrayList<>();
        // Adicionar 3 escalões fictícios para podermos testar a matemática
        this.tabelaEscaloes.add(new Escalao(0, 10000, 0.145, 0));
        this.tabelaEscaloes.add(new Escalao(10000, 20000, 0.23, 850));
        this.tabelaEscaloes.add(new Escalao(20000, Double.MAX_VALUE, 0.35, 3250));
    }
    private double calcularRendimentoColetavel(Declaracao d){
        double totalB = d.somarRendimentosBrutos();
        return totalB - deducaoCategA;
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
        double txm = e.getTaxaMarginal();
        double tab = e.getParcelaAbater();
        double m = rc*txm;
        double vfinal = m-tab;
        return vfinal;
    }
    public double calcularResultadoFinal(Declaracao d){
        double rc = calcularRendimentoColetavel(d);
        Escalao escalaoCorreto = encontrarEscalao(rc);
        double coletaBruta = calcularColetaBruta(rc, escalaoCorreto);
        double srt = d.somarRetencaoTotal();
        double sd = d.somarDespesas();
        double stotal = srt + sd;

        return coletaBruta - stotal;
    }
}
