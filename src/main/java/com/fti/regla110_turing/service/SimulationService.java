package com.fti.regla110_turing.service;

import com.fti.regla110_turing.dto.SimulationStepDto;
import com.fti.regla110_turing.model.Tape;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {

    /*
    * Simula la evolución de la cinta celular según la regla 110 durante K generaciones.
    * @param inputTape La cinta de entrada en el formato <K>#<cinta>#, donde K es el número de generaciones
    * a simular y <cinta> es la cadena que representa el estado inicial de la cinta celular.
    * @return Una lista de objetos SimulationStepDto que representan el estado de la cinta y la posición del cabezal 
    * en cada paso de la simulación.
    * @throws IllegalArgumentException Si el formato de la cinta de entrada es inválido 
     */
    public List<SimulationStepDto> simulate(String inputTape) {
        List<SimulationStepDto> history = new ArrayList<>();

        String[] parts = inputTape.split("#");
        if (parts.length != 2) {
            throw new IllegalArgumentException("\"Formato de cinta inválido. Debe ser <K>#<cinta>#");   
        }

        String kBinary = parts[0];
        String cellularTape = parts[1];

        int k = Integer.parseInt(kBinary, 2); //convertir k de binario a decimal    

        Tape tape = new Tape(cellularTape);
        int step = 0;

        //estado inicial de la maquina de turing
        history.add(new SimulationStepDto(step, tape.getContent(), tape.getHeadPosition(), "q0"));

        //simulacion por generaciones K
        for (int gen = 1; gen <= k; gen++) {
            step++;
            cellularTape = computeNextGeneration(cellularTape);
            String currentTapeState = Integer.toBinaryString(k - gen) + "#" + cellularTape + "#";
            tape = new Tape(currentTapeState);

            history.add(new SimulationStepDto(gen, tape.getContent(), 0, "q_compute_gen" + gen));
        }

        step++;
        history.add(new SimulationStepDto(++step, tape.getContent(), 0, "q_halt"));
        
        return history;
    }

    /**
    * Calcula la siguiente generación de la cinta celular aplicando la regla 110.
    * @param current La cadena que representa el estado actual de la cinta celular.
    * @return La cadena que representa el estado siguiente de la cinta celular.
     */
    private String computeNextGeneration(String current) {
        int n = current.length();
        StringBuilder next = new StringBuilder(n);

        for (int i = 0 ; i < n; i++) {
            //condiciones de frontera para la regla 110, considerando que la cinta es circular
            char left = current.charAt((i - 1 + n) % n); 
            char center = current.charAt(i);
            char right = current.charAt((i + 1) % n);

            String neighborhood = "" + left + center + right;
            char nextState = applyRule110(neighborhood);
            next.append(nextState);
        }
        return next.toString();
    }

    /**
    * Aplica la regla 110 a un vecindario de tres celdas y devuelve el estado resultante.
    */
    private char applyRule110(String neighborhood) {
        return switch (neighborhood) {
            case "111", "100", "000" -> '0';
            case "110", "101", "011", "010", "001" -> '1';
            default -> '0';
        };
    }
}