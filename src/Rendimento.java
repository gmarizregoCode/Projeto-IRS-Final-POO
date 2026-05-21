import java.io.Serializable;

public abstract class Rendimento implements Imprimivel, Serializable {
    private static final long serialVersionUID = 1L;
    private CategoriaRendimento categoriaRendimento;
    private double valorBruto;
    private double retencaoFonte;
    private double segurancaSocial;

    public Rendimento(CategoriaRendimento categoriaRendimento, double valorBruto, double retencaoFonte, double segurancaSocial) {
        this.categoriaRendimento = categoriaRendimento;
        this.valorBruto = valorBruto;
        this.retencaoFonte = retencaoFonte;
        this.segurancaSocial = segurancaSocial;
    }

    public CategoriaRendimento getCategoriaRendimento() { return categoriaRendimento; }
    public double getValorBruto() { return valorBruto; }
    public double getRetencaoFonte() { return retencaoFonte; }
    public double getSegurancaSocial() { return segurancaSocial; }

    // O MÉTODO ABSTRATO QUE CUMPRE O REQUISITO DA HERANÇA
    public abstract double getRendimentoColetavel();

    @Override
    public void print() { System.out.println(this.toString()); }

    @Override
    public String toString() {
        return "Rendimento (Categoria: " + categoriaRendimento +
                ", Valor Bruto: " + valorBruto +
                ", Retenção: " + retencaoFonte +
                ", Seg. Social: " + segurancaSocial +")";
    }
}