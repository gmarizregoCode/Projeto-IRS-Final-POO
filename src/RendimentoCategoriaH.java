public class RendimentoCategoriaH extends Rendimento {
    private static final double DEDUCAO_FIXA = 4104.00;

    public RendimentoCategoriaH(double valorBruto, double retencaoFonte, double segurancaSocial) {
        super(CategoriaRendimento.H, valorBruto, retencaoFonte, segurancaSocial);
    }

    @Override
    public double getRendimentoColetavel() {
        return getValorBruto() - DEDUCAO_FIXA;
    }
}