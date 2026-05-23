public class RendimentoCategoriaH extends Rendimento {
    private static final double DEDUCAO_FIXA = 4462.15;

    public RendimentoCategoriaH(double valorBruto, double retencaoFonte, double segurancaSocial) {
        super(CategoriaRendimento.H, valorBruto, retencaoFonte, segurancaSocial);
    }

    @Override
    public double getRendimentoColetavel() {
        return Math.max(0, getValorBruto() - DEDUCAO_FIXA);
    }
}
