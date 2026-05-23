package tps.tp4.modelo;

import java.io.Serializable;

public class Escalao implements Serializable {
    private static final long serialVersionUID = 1L;
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
