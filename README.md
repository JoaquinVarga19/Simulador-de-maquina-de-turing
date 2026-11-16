# Simulador-de-maquina-de-turing
Simulador web desarrollado como proyecto académico que implementa la evolución de un autómata celular (Regla 110) utilizando la lógica de una Máquina de Turing, construido con una arquitectura backend en Java (Spring Boot) y un frontend liviano con HTML5 y Bootstrap
---
## 1. Definición Formal y Marco Teórico
El simulador procesa una cinta de entrada bajo un formato estricto que combina una Máquina de Turing con un autómata celular:
**`[K]#[cinta_celular>#`** *(Ejemplo: `100#000000010000000#`)*
* **$K$ (Contador de Generaciones):** Representado en formato binario a la izquierda del primer `#`. En cada generación, la Máquina de Turing decrementa este valor en 1 mediante restas sucesivas hasta llegar a `0` (`000`), momento en el cual la máquina se detiene en el estado de parada (`q_halt`).
* **Cinta Celular:** Cadena binaria delimitada por símbolos `#`. Las celdas evolucionan aplicando la **Regla 110**, utilizando **condiciones de frontera circulares** (el extremo derecho se conecta con el izquierdo mediante aritmética modular) para evitar pérdidas en los bordes.
---
## 2. Arquitectura del Sistema
El proyecto sigue una arquitectura por capas tradicional de Spring Boot conectada a una interfaz web ligera y desacoplada:
```text
com.fti.regla110_turing/
│
├── controller/         # Controladores REST (Manejo de peticiones HTTP /simulate)
    └── SimulationController.java    # Controlador para la simulación de la regla 110.
├── service/            # Lógica de negocio (Máquina de Turing y Regla 110)
    └── SimulationService.java    # Servicio para la simulación de la evolución de la cinta celular según la regla 110.
├── model/              # Modelos de dominio y estructuras de cinta
    ├── Direction.java    # Dirección de movimiento de la cabeza de la máquina de Turing. Puede ser hacia la izquierda (LEFT), hacia la derecha (RIGHT) o sin movimiento (NONE).
    ├── Tape.java         # Representa la cinta infinita de la máquina de Turing y la posiciòn actual del cabezal. 
    └── Transition.java   # Reglas de transicion representado por: (Estado actual, símbolo leído, próximo estado, símbolo a escribir y dirección).
├── dto/                # Objetos de Transferencia de Datos (SimulationStepDto)
    ├── SimulationRequest.java   # Representa la solicitud de simulación de la máquina de Turing. 
    └── SimulationStepDto.java   # Representa un paso de la simulación de la máquina de Turing.
└── resources/
    └── static/         # Frontend (index.html con Bootstrap y JavaScript nativo)
        └── Index.html  # Interfaz basica
```
---
## 3. Detalle de Clases por Componente
* Capa de Controladores (controller/)
    * SimulationController.java: Es el controlador para la simulacion de la regla 110.
      Actúa como el punto de entrada de la API REST. Recibe las peticiones HTTP (por ejemplo, el método POST para /simulate), delega el procesamiento         al servicio de negocio y retorna el historial estructurado de la simulación.
* Capa de Lógica de Negocio (service/)
    * SimulationService: Simula la evolucion de la cinta segun la regla 110 durante K generaciones. Tenemos el "inputTape" que es la cinta de entrada
    en el formato <K>#<cinta>#, donde K es el número de generaciones a simular y <cinta> es la cadena que representa el estado inicial de la cinta          celular. Retorna una lista de objetos SimulationStepDto que representan el estado de la cinta y la posición del cabezal en cada paso de la              simulacion.
