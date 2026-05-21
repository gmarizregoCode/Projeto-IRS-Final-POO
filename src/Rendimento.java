import java.io.Serializable;

public class Rendimento implements Imprimivel, Serializable {
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

    public CategoriaRendimento getCategoriaRendimento() {
        return categoriaRendimento;
    }

    public double getValorBruto() {
        return valorBruto;
    }

    public double getRetencaoFonte() {
        return retencaoFonte;
    }

    public double getSegurancaSocial() {
        return segurancaSocial;
    }

    public void setCategoriaRendimento(CategoriaRendimento categoriaRendimento) {
        this.categoriaRendimento = categoriaRendimento;
    }

    public void setValorBruto(double valorBruto) {
        this.valorBruto = valorBruto;
    }

    public void setRetencaoFonte(double retencaoFonte) {
        this.retencaoFonte = retencaoFonte;
    }

    public void setSegurancaSocial(double segurancaSocial) {
        this.segurancaSocial = segurancaSocial;
    }

    public double getRendimentoColetavel(double valorBruto, double segurancaSocial) {
        return this.valorBruto - this.segurancaSocial;
    }

    @Override
    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Rendimento (Categoria de Rendimento: " + categoriaRendimento +
                ", Valor Bruto: " + valorBruto +
                ", Retenção de Fonte: " + retencaoFonte +
                ", Segurança Social: " + segurancaSocial +")";
    }
}