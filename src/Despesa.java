import java.io.Serializable;

public class Despesa implements Imprimivel, Serializable {
    private static final long serialVersionUID = 1L;
    private TipoDespesa tipoDespesa;
    private double valor;
    private String nifComerciante;

    public Despesa(TipoDespesa tipoDespesa, double valor, String nifComerciante) {
        this.tipoDespesa = tipoDespesa;
        this.valor = valor;
        this.nifComerciante = nifComerciante;
    }

    public TipoDespesa getTipoDespesa(){ return tipoDespesa; }

    public double getValor() { return valor; }

    public String getNifComerciante() { return nifComerciante; }

    public void setTipoDespesa() { this.tipoDespesa = tipoDespesa; }

    public void setValor() { this.valor = valor; }

    public void setNifComerciante() { this.nifComerciante = nifComerciante; }

    @Override
    public void print() {
        System.out.println(this.toString());
    }
    @Override
    public String toString() {
        return "Despesas (Tipo de Despesa: " + tipoDespesa +
                ", Valor: " + valor +
                ", Nif do Comerciante: " + nifComerciante +")";
    }
}
