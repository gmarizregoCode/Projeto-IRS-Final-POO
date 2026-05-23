package tps.tp4.calculo;

import tps.tp4.modelo.Declaracao;

/**
 * Interface que define o contrato para qualquer estratégia de cálculo de IRS.
 * Padrão de Desenho: Strategy
 */
public interface EstrategiaFiscal {

    /**
     * Calcula o imposto total a pagar (ou a reembolsar) para um determinado contribuinte e a sua declaração.
     */
    double calcularImpostoFinal(Declaracao declaracao);

}