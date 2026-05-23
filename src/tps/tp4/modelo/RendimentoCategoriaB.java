package tps.tp4.modelo;

public class RendimentoCategoriaB extends Rendimento {
    public RendimentoCategoriaB(double valorBruto, double retencaoFonte, double segurancaSocial) {
        super(CategoriaRendimento.B, valorBruto, retencaoFonte, segurancaSocial);
    }

    @Override
    public double getRendimentoColetavel() {
        return getValorBruto() - getSegurancaSocial(); // Trabalhador independente abate a SS
    }
}