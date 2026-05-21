import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Declaracao implements Imprimivel, Serializable {
    private static final long serialVersionUID = 1L;
    private Contribuinte contribuinte;
    private List<Rendimento> rendimentos;
    private List<Despesa> despesas;
    private int anoFiscal;

    public Declaracao(Contribuinte contribuinte,  int anoFiscal){
        this.contribuinte = contribuinte;
        this.anoFiscal = anoFiscal;
        this.rendimentos = new ArrayList<>();
        this.despesas = new ArrayList<>();
    }

    public Contribuinte getContribuinte(){ return contribuinte; }

    public List<Rendimento> getRendimentos(){ return rendimentos; }

    public List<Despesa> getDespesas(){ return despesas; }

    public int getAnoFiscal(){ return anoFiscal; }

    public void setContribuinte(Contribuinte contribuinte) { this.contribuinte = contribuinte; }

    public void setRendimentos(List<Rendimento> rendimentos) { this.rendimentos = rendimentos; }

    public void setDespesas(List<Despesa> despesas) { this.despesas = despesas; }

    public void setAnoFiscal(int anoFiscal) { this.anoFiscal = anoFiscal; }

    public void adicionarRendimento(Rendimento r){
        rendimentos.add(r);
    }

    public void adicionarDespesas(Despesa d){
        despesas.add(d);
    }

    public double somarRendimentosBrutos(){
        double soma = 0;
        for (Rendimento r : rendimentos) {
            soma += r.getValorBruto();
        }
        return soma;
    }

    public double somarRetencaoTotal(){
        double soma = 0;
        for (Rendimento r : rendimentos) {
            soma += r.getRetencaoFonte();
        }
        return soma;
    }
    public double somarDespesas(){
        double soma = 0;
        for(Despesa d : despesas){
            soma += d.getValor();
        }
        return soma;
    }

    @Override
    public void print() {
        System.out.println(this.toString());
    }
    @Override
    public String toString() {
        return "Declaração de IRS [" + anoFiscal + "]\n" +
                "Contribuinte: " + contribuinte.getNome() + " (NIF: " + contribuinte.getNif() + ")\n" +
                "Total de Rendimentos registados: " + rendimentos.size() + "\n" +
                "Total de Despesas registadas: " + despesas.size();
    }
}
