package com.fti.regla110_turing.dto;

/*
 * Clase que representa un paso de la simulación de la máquina de Turing.
 */
public class SimulationStepDto {

    private int step; // Representa el número de paso en la simulación.
    private String contentTape; // Representa el contenido de la cinta en el paso actual de la simulación.
    private int headPosition; // Representa la posición del cabezal en la cinta en el paso actual de la simulación.
    private String state; // Representa el estado actual de la máquina de Turing en el paso actual de la simulación.

    /*
     * Constructor de la clase SimulationStepDto.
     * @param step El número de paso en la simulación.
     * @param contentTape El contenido de la cinta en el paso actual de la simulación.
     * @param headPosition La posición del cabezal en la cinta en el paso actual de la simulación.
     * @param state El estado actual de la máquina de Turing en el paso actual de la simulación.
     */
    public SimulationStepDto(int step, String contentTape, int headPosition, String state) {
        this.step = step;
        this.contentTape = contentTape;
        this.headPosition = headPosition;
        this.state = state;
        }

    /*
     * Obtiene el número de paso en la simulación.
     * @return El número de paso.
     */
    public int getStep() {
        return step;
    }

    /*
     * Obtiene el contenido de la cinta en el paso actual de la simulación.
     * @return El contenido de la cinta.
     */
    public String getContentTape() {
        return contentTape;
    }

    /*
     * Obtiene la posición del cabezal en la cinta en el paso actual de la simulación.
     * @return La posición del cabezal.
     */
    public int getHeadPosition() {
        return headPosition;
    }

    /*
     * Obtiene el estado actual de la máquina de Turing en el paso actual de la simulación.
     * @return El estado actual.
     */
    public String getState() {
        return state;
    }
}
