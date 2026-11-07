package com.fti.regla110_turing.model;

/*
Clase que modela formalmente las reglas de transicion representado por:
(Estado actual, símbolo leído, próximo estado, símbolo a escribir y dirección).
*/
public record Transition(
    String currentState,
    char readSymbol,
    String nextState,
    char writeSymbol,
    Direction direction
) {}
