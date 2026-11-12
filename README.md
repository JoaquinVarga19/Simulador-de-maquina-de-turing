# Simulador-de-maquina-de-turing
Simulador web desarrollado como proyecto académico que implementa la evolución de un autómata celular (Regla 110) utilizando la lógica de una Máquina de Turing, construido con una arquitectura backend en Java (Spring Boot) y un frontend liviano con HTML5 y Bootstrap
---
## 📋 1. Definición Formal y Marco Teórico
El simulador procesa una cinta de entrada bajo un formato estricto que combina una Máquina de Turing con un autómata celular:
**`[K]#[cinta_celular>#`** *(Ejemplo: `100#000000010000000#`)*
* **$K$ (Contador de Generaciones):** Representado en formato binario a la izquierda del primer `#`. En cada generación, la Máquina de Turing decrementa este valor en 1 mediante restas sucesivas hasta llegar a `0` (`000`), momento en el cual la máquina se detiene en el estado de parada (`q_halt`).
* **Cinta Celular:** Cadena binaria delimitada por símbolos `#`. Las celdas evolucionan aplicando la **Regla 110**, utilizando **condiciones de frontera circulares** (el extremo derecho se conecta con el izquierdo mediante aritmética modular) para evitar pérdidas en los bordes.
---
## ⚙️ 2. Arquitectura del Sistema
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
## 🛠️ 3. Requisitos e Instalación
Para compilar y ejecutar este proyecto en tu entorno local, asegurate de contar con las siguientes herramientas instaladas:
* **Java Development Kit (JDK):** Versión 17 o superior requerida (compatible con JDK 17, 21, etc.).
* **Maven:** Gestor de dependencias y empaquetado (el proyecto incluye el script de Maven Wrapper `./mvnw`, por lo que no es estrictamente necesario tenerlo instalado de forma global).
### Clonación y Ejecución
1. **Clonar el repositorio:**
```bash
git clone [https://github.com/JoaquinVarga19/Simulador-de-maquina-de-turing.git](https://github.com/JoaquinVarga19/Simulador-de-maquina-de-turing.git)
cd Simulador-de-maquina-de-turing
```
2. **Compilar y empaquetar el proyecto (opcional):**
```bash
./mvnw clean package
```
3. Ejecutar la aplicación Spring Boot:
```bash
./mvnw spring-boot:run
```
4. **Acceder a la interfaz web:**
Una vez iniciado el servidor backend, abrí tu navegador e ingresá a la siguiente URL:
```bash
http://localhost:8080/
```
