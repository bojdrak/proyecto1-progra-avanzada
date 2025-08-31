# Sistema de Consultas Ciudadanas

Sistema de votación digital para consultas ciudadanas organizadas por temas y preguntas específicas.


## Compilación y Ejecución

**Compilación**
```bash
javac files/*.java
````

**Ejecución**

```bash
java -cp . files Main
```

## Estructura del Proyecto

* `Main.java` -> Clase principal con menú de consola
* `SistemaConsultas.java` -> Núcleo del sistema de gestión
* `ConsultaCiudadana.java` -> Representación de consultas
* `Tema.java` -> Gestión de temas dentro de consultas
* `Pregunta.java` -> Modelo de preguntas individuales
* `Votante.java` -> Datos de ciudadanos habilitados



## Características Técnicas

* Programación Orientada a Objetos
* Colecciones Java (`ArrayList`, `HashMap`)
* Manejo de fechas con `LocalDate`
* Entrada/Salida por consola
* Validaciones básicas de entrada
* Sistema de votación anónimo pero registrado



### Notas de Uso

* Los votantes deben registrarse antes de votar
* Las consultas deben crearse con anticipación
* Cada votante puede votar una vez por consulta
* Los resultados se muestran de forma agregada
* El sistema no persiste datos entre ejecuciones



### Requisitos

* Java JDK 11 o superior
* Terminal o línea de comandos
* Conocimientos básicos de consola



## Problemas conocidos

* El codigo se cae en caso de ingresar incorrectamente los datos (ie. Ingresar numeros en vez de texto)

## Comandos del Sistema

### Menú Principal

```
=== SISTEMA DE CONSULTAS CIUDADANAS ===
1. Gestión de Votantes
2. Gestión de Consultas y Temas
3. Registrar Voto
4. Mostrar Resultados
5. Salir
```

### Gestión de Votantes

```
=== GESTIÓN DE VOTANTES ===
1. Agregar votante
2. Listar votantes
3. Volver al menú principal
```

### Gestión de Consultas

```
=== GESTIÓN DE CONSULTAS ===
1. Agregar consulta
2. Agregar tema a consulta
3. Agregar pregunta a tema
4. Listar consultas
5. Volver al menú principal
```

##  Ejemplo de Flujo de Votación

Aquí hay un ejemplo de como se deberían ingresar los datos. 
**Registro de Votante**

```
RUT: 11222333-4
Nombre: Ana López
Edad: 28
Dirección: Av. de Ejemplo 456
```

**Creación de Consulta**

```
Nombre de la consulta: Consulta Ambiental 2025
Descripción: Consulta sobre políticas ambientales
Año: 2025
Mes: 8
Día: 30
```

