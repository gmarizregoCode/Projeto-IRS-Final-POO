public class CalculadoraIRSJovem extends CalculadoraIRSNormal {

    @Override
    public double calcularImpostoFinal(Declaracao d) {
        double impostoNormal = super.calcularImpostoFinal(d);

        if (impostoNormal > 0) {
            System.out.println("BENEFÍCIO IRS JOVEM ATIVO: 30% de desconto no imposto!");
            return impostoNormal * 0.70;
        }


        return impostoNormal;
    }
}