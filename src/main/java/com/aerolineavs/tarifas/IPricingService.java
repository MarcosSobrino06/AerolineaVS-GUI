package com.aerolineavs.tarifas;

/**
 * Servicio para calcular la tarifa aplicable a un cliente.
 */
public interface IPricingService {

    /**
     * Calcula la tarifa según los datos del cliente.
     *
     * @param cliente datos del cliente
     * @return tarifa resultante junto con suposiciones
     */
    ResultadoTarifa calcular(ClientePotencial cliente);
}