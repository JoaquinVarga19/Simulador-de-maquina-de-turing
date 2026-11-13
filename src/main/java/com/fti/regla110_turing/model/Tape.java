package com.fti.regla110_turing.model;

/*
Clase que representa la cinta infinita de la máquina de Turing
y la posiciòn actual del cabezal. 
 */
public class Tape {
    private final StringBuilder content; // Representa el contenido de la cinta como un StringBuilder para permitir modificaciones.
    private int headPosition; // Representa la posición actual del cabezal en la cinta. Puede ser negativa para simular una cinta infinita hacia la izquierda.

    /*
     * Constructor de la clase Tape.
     * @param initialContent El contenido inicial de la cinta.
     */
    public Tape(String initialContent) {
        this.content = new StringBuilder(initialContent);
        this.headPosition = 0;
    }

    /*
     * Lee el símbolo en la posición actual del cabezal.
     * @return El símbolo en la posición actual del cabezal, o '_' si la posición está fuera de los límites de la cinta.
     */
    public char read() {
        if (headPosition < 0 || headPosition >= content.length()) { 
            return '_'; // Símbolo en blanco para posiciones fuera de la cinta
        }
        return content.charAt(headPosition);
    }

    /*
     * Escribe un símbolo en la posición actual del cabezal. si la pisicion del cabezal es negativa, se agrega el símbolo al final de la cinta.
     * o si no se sobreescribe el símbolo en la posición actual del cabezal.
     * @param symbol El símbolo a escribir.
     */
    public void write(char symbol) {
        if (headPosition < 0) {
            content.insert(0, symbol);
            headPosition = 0; // Mantiene la posición del cabezal en 0 después de agregar un símbolo al inicio.
        } else if (headPosition >= content.length()) { 
            content.append(symbol);
        } else {
            content.setCharAt(headPosition, symbol);
        }
    }

    /*
     * Mueve el cabezal en la dirección especificada.
     * @param direction La dirección en la que mover el cabezal (LEFT, RIGHT o NONE).
     */
    public void move(Direction direction) {
        if (direction == Direction.LEFT) {
            headPosition--;
            if (headPosition < 0) {
                content.insert(0, '_'); // Agrega un símbolo en blanco al inicio de la cinta si el cabezal se mueve a la izquierda fuera de los límites.
                headPosition = 0; // Mantiene la posición del cabezal en 0 después de agregar un símbolo en blanco al inicio.
            }
        } else if (direction == Direction.RIGHT) {
            headPosition++;
            if (headPosition >= content.length()) {
                content.append('_'); // Agrega un símbolo en blanco al final de la cinta si el cabezal se mueve a la derecha fuera de los límites.
            }
        }
    }


    /*
     *Macro L: Mueve el cabezal a la izquierda.
     */
    public void moveLeft() {
        move(Direction.LEFT);
    }

    /*
     *Macro R: Mueve el cabezal a la derecha.
     */
    public void moveRight() {
        move(Direction.RIGHT);
    }

    /*
    Macro Rx: Se desplaza a la derecha hasta encontrar la primera aparición del símbolo 'target'.
    */
    public void moveRightUntil(char target) {
        while (read() != target) {
            moveRight();
        }
    }

    /*
    Macro Lx: Se desplaza a la izquierda hasta encontrar la primera aparición del símbolo 'target'.
    */
    public void moveLeftUntil(char target) {
        while (read() != target) {
            moveLeft();
        }
    }

    /*
    Macro SR (Shift Right): Corre un lugar a la derecha la cadena de caracteres
     * que se encuentra a la izquierda desde la posición actual hasta el primer blanco ('_').
    */
    public void shiftRight() {
        char carry = '_';
        // Buscamos el primer blanco hacia la izquierda para empezar a arrastrar hacia la derecha
        int startPos = headPosition;
        moveLeftUntil('_');
        
        // Avanzamos hacia la derecha copiando el símbolo anterior hasta volver a startPos
        while (headPosition <= startPos) {
            char temp = read();
            write(carry);
            carry = temp;
            if (headPosition == startPos) {
                break;
            }
            moveRight();
        }
    }    


    /*
    Macro SL (Shift Left): Corre un lugar a la izquierda la cadena de caracteres
     * que se encuentra a la derecha desde la posición actual hasta el primer blanco ('_').
    */
    public void shiftLeft() {
        char carry = '_';
        int startPos = headPosition;
        moveRightUntil('_');

        while (headPosition >= startPos) {
            char temp = read();
            write(carry);
            carry = temp;
            if (headPosition == startPos) {
                break;
            }
            moveLeft();
        }

    }

    /*
     * Obtiene el contenido de la cinta.
     * @return El contenido de la cinta como una cadena de caracteres.
     */
    public String getContent() {
        return content.toString();
    }

    /*
     * Obtiene la posición del cabezal.
     * @return La posición del cabezal.
     */
    public int getHeadPosition() {
        return headPosition;
    }
}
