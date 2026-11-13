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

        if(inputTape == null || inputTape.isEmpty()) {
            throw new IllegalArgumentException("La cinta de entrada no puede estar vacía.");
        }

        inputTape = inputTape.trim(); // Eliminamos espacios en blanco al inicio y al final
        if (inputTape.startsWith("\"") && inputTape.endsWith("\"")) {
            inputTape = inputTape.substring(1, inputTape.length() - 1); // Eliminamos comillas si existen
        }

        String[] parts = inputTape.split("#");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato de cinta inválido. Debe ser <K>#<cinta>#");   
        }

        String kBinary = parts[0];  
        String cellularTape = parts[1];

        int k = Integer.parseInt(kBinary, 2); // Convertir k de binario a decimal    
        int maxKlength = kBinary.length(); // Longitud fija para mantener alineado el '#'

        // Estado inicial (Paso 0) con la cinta completa original
        Tape tape = new Tape(inputTape);
        int step = 0;
        history.add(new SimulationStepDto(step, tape.getContent(), tape.getHeadPosition(), "q0"));

        // Simulación por generaciones K
        for (int gen = 1; gen <= k; gen++) {
            step++; // Incrementamos nuestro contador de pasos secuencial
            cellularTape = computeNextGeneration(cellularTape);
            
            int kRemaining = k - gen;

            /*
             * Rellenamos con ceros a la izquierda para mantener la longitud de K constante y alineada con el '#'.
             */
            String rawBinaryK = Integer.toBinaryString(kRemaining);
            String paddedBinaryK = String.format("%" + maxKlength + "s", rawBinaryK).replace(' ', '0');
            
            // Creamos la cinta con el estado actual usando el binario padded
            tape = new Tape(paddedBinaryK + "#" + cellularTape + "#");

            // Usamos la macro Rx (moveRightUntil) para posicionarnos sobre el primer separador '#'
            tape.moveRightUntil('#');

            /*
             * Usamos la macro SR (shiftRight) cuando el binario requiere ajuste de espacios
             */
            if (paddedBinaryK.contains("0")) {
                tape.shiftRight(); 
            }

            // Obtenemos la posición real del cabezal dictada por los movimientos de las macros
            int headPosition = tape.getHeadPosition();

            // Guardamos el paso en el historial con su cabezal real
            history.add(new SimulationStepDto(step, tape.getContent(), headPosition, "q_compute_gen_" + gen));
        }

        // Estado de parada (Paso final)
        step++; 
        history.add(new SimulationStepDto(step, tape.getContent(), 0, "q_halt"));
        
        return history;
    }

    /**
     * Calcula la siguiente generación simulando el movimiento físico del cabezal 
     * de la Máquina de Turing barriendo los bloques celulares celda por celda.
     */
    private String computeNextGeneration(String currentCellularTape) {
        int n = currentCellularTape.length();
        StringBuilder nextGeneration = new StringBuilder();

        // Simulamos el movimiento del cabezal de izquierda a derecha, evaluando cada vecindario de tres celdas
        Tape cellTape = new Tape(currentCellularTape);

        // Simulamos el barrido celda por celda mediante desplazamientos del cabezal (Bloques)
        for (int i = 0; i < n; i++) {

            // Inspeciono la celda izquierda
            int leftIndex = (i - 1 + n) % n; // Índice de la celda izquierda (con envolvimiento)
            char left = getCellAtPosition(currentCellularTape, leftIndex);


            // Inspeciono la celda central
            char center = cellTape.read();

            // Inspeciono la celda derecha
            int rightIndex = (i + 1) % n; // Índice de la celda derecha (con envolvimiento)
            char right = getCellAtPosition(currentCellularTape, rightIndex);

            //aplico la funcion de transicion de la regla 110
            String neighborhood = "" + left + center + right;
            char newState = applyRule110(neighborhood);

            nextGeneration.append(newState);

            // Movemos el cabezal a la siguiente celda del bloque (Macro R / moveRight)
            cellTape.moveRight();
        }
        return nextGeneration.toString();
    }

    /*
    * Método auxiliar para consultar una posición específica simulando saltos de cabezal
    */
    private char getCellAtPosition(String tapeContent, int position) {
        if (position >= 0 && position < tapeContent.length()) {
            return tapeContent.charAt(position);
        } 
        return '_';
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