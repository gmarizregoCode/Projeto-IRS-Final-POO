public class CalculadoraIRSJovem implements EstrategiaFiscal {

    // Composição: A calculadora jovem "tem uma" calculadora normal lá dentro
    private final EstrategiaFiscal calculadoraBase = new CalculadoraIRSNormal();

    @Override
    public double calcularImpostoFinal(Declaracao d) {
        // Pede à calculadora base para fazer o trabalho pesado
        double impostoNormal = calculadoraBase.calcularImpostoFinal(d);

        if (impostoNormal > 0) {
            System.out.println("BENEFÍCIO IRS JOVEM ATIVO: 30% de desconto no imposto!");
            return impostoNormal * 0.70;
        }
        return impostoNormal;
    }
}