public class CalculadoraIRSCasal implements EstrategiaFiscal {

    private final EstrategiaFiscal calculadoraBase = new CalculadoraIRSNormal();

    @Override
    public double calcularImpostoFinal(Declaracao d) {
        double impostoBase = calculadoraBase.calcularImpostoFinal(d);
        System.out.println("⚖️ TRIBUTAÇÃO CONJUNTA: quociente familiar aplicado.");
        return impostoBase * 2;
    }
}
