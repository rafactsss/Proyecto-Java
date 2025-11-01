# SpaceNav2024

Videojuego desarrollado con LibGDX. Este documento describe los requisitos y el procedimiento para ejecutar correctamente el programa utilizando Gradle.

## Requisitos

Antes de ejecutar el proyecto se debe contar con lo siguiente:

- JDK 11 o JDK 17 instalado en el sistema
- Git (opcional, solo si se desea clonar el repositorio)
- No es necesario instalar Gradle, ya que el proyecto incluye Gradle Wrapper

## Ejecución del programa

El proyecto debe ejecutarse mediante Gradle con el fin de garantizar la correcta carga de todos los recursos (imágenes, sonidos, etc.). No se recomienda utilizar “Run As Java Application” en el IDE.

### Ejecución desde terminal o consola

Ubicarse en la carpeta raíz del proyecto y ejecutar los siguientes comandos:

Windows:
gradlew :lwjgl3:run

yaml
Copiar código

macOS / Linux:
./gradlew :lwjgl3:run

yaml
Copiar código

Esto iniciará el videojuego en una ventana independiente.

### Ejecución desde Eclipse

1. Importar el proyecto como:
   File → Import → Gradle → Existing Gradle Project

2. Abrir la vista de tareas de Gradle:
   Window → Show View → Other → Gradle → Gradle Tasks

3. Ejecutar la tarea correspondiente:
   lwjgl3 → application → run

## Solución de problemas frecuentes

Si aparecen errores relacionados con dependencias o recursos:

- Actualizar la configuración del proyecto en Eclipse:
  Click derecho sobre el proyecto → Gradle → Refresh Gradle Project
  Project → Clean

- Verificar que el JDK configurado en el IDE corresponda a JDK 11 o JDK 17

Si el juego no muestra imágenes o sonidos:
- Confirmar que los recursos se encuentren en:
  `lwjgl3/src/main/resources/`

Con estas indicaciones el programa debería ejecutarse correctamente en cualquier equipo compatible.
