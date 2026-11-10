package com.fti.regla110_turing.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fti.regla110_turing.dto.SimulationRequest;
import com.fti.regla110_turing.service.SimulationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fti.regla110_turing.dto.SimulationStepDto;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Controlador para la simulación de la regla 110.
 */
@RestController 
@RequestMapping("/simulation")
@CrossOrigin(origins = "*")
public class SimulationController {

    private final SimulationService simulationService; // Servicio de simulación que se inyecta en el controlador.

    /**
     * Constructor de la clase SimulationController.
     * @param simulationService Servicio de simulación que se inyecta en el controlador.
     */
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    /**
     * Simula la regla 110 con la cinta de entrada proporcionada.
     * @param request La solicitud de simulación que contiene la cinta de entrada.
     * @return Una lista de pasos de simulación.
     */
    @PostMapping("/simulate")
    public ResponseEntity<List<SimulationStepDto>> simulate(@RequestBody SimulationRequest request) {
        List<SimulationStepDto> result = simulationService.simulate(request.getInputTape());
        return ResponseEntity.ok(result);
    }
}