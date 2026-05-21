public class RendimentoCategoriaA extends Rendimento {
    private static final double DEDUCAO_FIXA = 4104.00;

    public RendimentoCategoriaA(double valorBruto, double retencaoFonte, double segurancaSocial) {
        super(CategoriaRendimento.A, valorBruto, retencaoFonte, segurancaSocial);
    }

    @Override
    public double getRendimentoColetavel() {
        return getValorBruto() - DEDUCAO_FIXA;
    }
}
