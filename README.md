# Sistema de Consultas Ciudadanas

Sistema de votación digital para consultas ciudadanas organizadas por temas y preguntas específicas, con interfaz gráfica de usuario (GUI) desarrollada en Swing.
## Estructura del Proyecto

```
src/
├── main/
│   └── Main.java                 # Clase principal que inicia la aplicación
├── gui/
│   ├── VentanaPrincipal.java     # Ventana principal con menú de navegación
│   ├── VentanaGestionVotantes.java
│   ├── VentanaGestionConsultas.java
│   ├── VentanaRegistrarVoto.java
│   ├── VentanaResultados.java
│   ├── VentanaFiltrarVotantes.java
│   └── VentanaBuscarElemento.java
├── exceptions/
│   ├── EdadInvalidaException.java   
│   ├── VotanteYaExisteException.java
└── sistema/
    ├── SistemaConsultas.java     # Núcleo del sistema de gestión
    ├── ConsultaCiudadana.java    # Representación de consultas ciudadanas
    ├── Tema.java                 # Gestión de temas dentro de consultas
    ├── Pregunta.java             # Modelo de preguntas individuales
    ├── Votante.java              # Datos de ciudadanos habilitados
    └── PersistenciaDatos.java    # Manejo de persistencia de datos

```

## Compilación y Ejecución
### Compilación

```bash
javac -encoding UTF-8 -d bin -sourcepath src src/main/Main.java
```
### Ejecución

```bash
java -cp bin Main
```

## Características del Sistema

### Funcionalidades Principales

* Gestión de Votantes: Registro, listado y filtrado de votantes

* Gestión de Consultas: Creación y administración de consultas ciudadanas

* Sistema de Votación: Registro seguro de votos con control de duplicados

* Resultados y Estadísticas: Visualización de resultados y análisis estadísticos

* Persistencia de Datos: Guardado automático de datos en archivos binarios

* Reportes CSV: Exportación de datos de votantes a formato CSV

### Características Técnicas

* Interfaz Gráfica: Desarrollada con Java Swing
* Programación Orientada a Objetos: Diseño modular y extensible
* Colecciones Java: Uso de ArrayList, HashMap, HashSet
* Manejo de Fechas: Utilización de LocalDate para fechas de consultas
* Persistencia: Serialización de objetos para guardar el estado del sistema
* Manejo de Excepciones: Sistema robusto de manejo de errores
* Validaciones: Control de edad, RUT duplicados y votaciones múltiples

## Funcionalidades Detalladas

### Gestión de Votantes

* Registro de nuevos votantes (mayores de 18 años)
* Validación de RUT único
* Filtrado por edad y comuna
* Exportación a CSV

### Gestión de Consultas

* Creación de consultas ciudadanas con fecha específica
* Organización por temas y preguntas
* Tipos de respuesta predefinidos (Si/No, Alta/Media/Baja, etc.)

### Sistema de Votación

* Control de votación por consulta (cada votante puede votar una vez por consulta)
* Validación de RUT registrado
* Registro anónimo pero controlado de votos
* Resultados y Análisis
* Estadísticas por rangos de edad (18-25, 26-40, 41-60, 61+)
* Visualización de resultados agregados
* Exportación de resultados

### Flujo de Trabajo Típico

1. Registro de Votantes: Agregar ciudadanos habilitados para votar
2. Creación de Consultas: Definir consultas con temas y preguntas
3. Proceso de Votación: Los votantes registrados participan en las consultas disponibles
4. Análisis de Resultados: Revisar estadísticas y resultados agregados

### Datos Iniciales

El sistema incluye datos de ejemplo:

* 5 votantes pre-registrados
* 2 consultas ciudadanas con temas y preguntas
* "Consulta de Desarrollo Urbano 2025" (Transporte Público y Áreas Verdes)
* "Consulta Educacional 2025" (Infraestructura Escolar)

### Manejo de Datos
* Archivo de datos: datos_consultas.dat (generado automáticamente)
* Guardado automático: Al cerrar la aplicación
* Carga inicial: Se cargan datos existentes o se crean ejemplos

### Requisitos del Sistema

* Java JDK 11 o superior
* Sistema operativo: Windows, Linux o macOS
* Memoria: Mínimo 512 MB RAM recomendado


### Consideraciones de Seguridad

* Validación de edad mínima (18 años)
* Control de votación duplicada por consulta
* Verificación de RUT registrado antes de votar
* Persistencia segura de datos

### Notas de Uso

* Los votantes deben registrarse antes de participar en cualquier consulta
* Cada consulta puede contener múltiples temas y preguntas
* Los resultados se muestran de forma agregada para mantener el anonimato
* El sistema pregunta confirmación antes de salir para guardar los datos

