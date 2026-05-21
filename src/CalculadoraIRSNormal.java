import java.util.ArrayList;
import java.util.List;

public class CalculadoraIRSNormal implements EstrategiaFiscal {
    private List<Escalao> tabelaEscaloes;
    private final double deducaoCategA = 4104.00;

    public CalculadoraIRSNormal(){
        this.tabelaEscaloes = new ArrayList<>();
        // Adicionar 3 escalões fictícios para podermos testar a matemática
        this.tabelaEscaloes.add(new Escalao(0, 10000, 0.145, 0));
        this.tabelaEscaloes.add(new Escalao(10000, 20000, 0.23, 850));
        this.tabelaEscaloes.add(new Escalao(20000, Double.MAX_VALUE, 0.35, 3250));
    }
    private double calcularRendimentoColetavel(Declaracao d){
        double totalRC = 0;
        for (Rendimento r : d.getRendimentos()) {
            // Chama a matemática certa automaticamente graças ao Polimorfismo!
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
        double txm = e.getTaxaMarginal();
        double tab = e.getParcelaAbater();
        double m = rc*txm;
        double vfinal = m-tab;
        return vfinal;
    }
    @Override
    public double calcularImpostoFinal(Declaracao d){
        double rc = calcularRendimentoColetavel(d);

        // Proteção: Se RC for 0 ou negativo, devolvemos todo o imposto retido
        if (rc <= 0) {
            return -d.somarRetencaoTotal();
        }

        Escalao escalaoCorreto = encontrarEscalao(rc);
        if (escalaoCorreto == null) {
            return -d.somarRetencaoTotal(); // Dupla segurança
        }

        double coletaBruta = calcularColetaBruta(rc, escalaoCorreto);
        double stotal = d.somarRetencaoTotal() + d.somarDespesas();

        return coletaBruta - stotal;
    }
}
