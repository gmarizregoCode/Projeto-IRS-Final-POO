public class EstrategiaFactory {

    public static EstrategiaFiscal criarEstrategia(Contribuinte c) {
        if (c.getIdade() >= 18 && c.getIdade() <= 30) {
            return new CalculadoraIRSJovem();
        }

        return new CalculadoraIRSNormal();
    }

}