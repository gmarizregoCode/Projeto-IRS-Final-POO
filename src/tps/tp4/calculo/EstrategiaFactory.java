package tps.tp4.calculo;

import tps.tp4.modelo.Contribuinte;
import tps.tp4.modelo.EstadoCivil;

public class EstrategiaFactory {

    public static EstrategiaFiscal criarEstrategia(Contribuinte c) {
        if (c.getEstadoCivil() == EstadoCivil.CASADO) {
            return new CalculadoraIRSCasal();
        }

        if (c.getIdade() >= 18 && c.getIdade() <= 30) {
            return new CalculadoraIRSJovem();
        }

        return new CalculadoraIRSNormal();
    }
}