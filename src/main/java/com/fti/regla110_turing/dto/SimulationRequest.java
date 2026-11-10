package com.fti.regla110_turing.dto;

/*
Clase que representa la solicitud de simulación de la máquina de Turing.
 */
public class SimulationRequest {

    /*
     * La cinta de entrada para la simulación.
     */
    private String inputTape;

    /*
     * Obtiene la cinta de entrada para la simulación.
     * @return La cinta de entrada.
     */
    public String getInputTape() {
        return inputTape;
    }

    /*
     * Establece la cinta de entrada para la simulación.
     * @param inputTape La cinta de entrada.
     */
    public void setInputTape(String inputTape) {
        this.inputTape = inputTape;
    }
}
