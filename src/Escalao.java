public class Escalao {
    private double limiteMinimo;
    private double limiteMaximo;
    private double taxaMarginal;
    private double parcelaAbater;

    public Escalao(double limiteMinimo, double limiteMaximo, double taxaMarginal, double parcelaAbater) {
        this.limiteMinimo = limiteMinimo;
        this.limiteMaximo = limiteMaximo;
        this.taxaMarginal = taxaMarginal;
        this.parcelaAbater = parcelaAbater;
    }

    public double getLimiteMinimo() {
        return limiteMinimo;
    }

    public double getLimiteMaximo() {
        return limiteMaximo;
    }

    public double getTaxaMarginal() {
        return taxaMarginal;
    }

    public double getParcelaAbater() {
        return parcelaAbater;
    }
    public boolean isDentro(double valor) {
        return valor > limiteMinimo && valor <= limiteMaximo;
    }
}
