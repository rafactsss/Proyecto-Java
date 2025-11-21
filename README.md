# SpaceNav2024 - Flappy Bird Game

Un juego estilo Flappy Bird implementado con [LibGDX](https://libgdx.com/), generado con [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

## Descripción del Proyecto

SpaceNav2024 es un juego de escritorio que implementa la mecánica clásica de Flappy Bird con un tema espacial. El jugador controla una nave espacial que debe navegar a través de huecos entre obstáculos verticales (tuberías). El juego incluye:

- Sistema de dificultad progresiva
- Detección de colisiones precisa
- Sistema de puntuación
- Efectos de sonido
- Múltiples patrones de diseño (Singleton, Template Method, Strategy, Abstract Factory)

## Requisitos del Sistema

### Software Necesario

- **Java Development Kit (JDK)**: Versión 11 o superior
  - Verifica tu versión con: `java -version`
  - Descarga desde: [Adoptium](https://adoptium.net/) o [Oracle](https://www.oracle.com/java/technologies/downloads/)

- **Gradle**: No es necesario instalarlo manualmente, el proyecto incluye Gradle Wrapper

### Requisitos de Hardware

- **Sistema Operativo**: Windows, macOS, o Linux
- **RAM**: Mínimo 2GB
- **Espacio en disco**: ~100MB para el proyecto y dependencias

## Estructura del Proyecto

```
SpaceNav2024/
├── core/                          # Lógica del juego (independiente de plataforma)
│   └── src/main/java/com/proyecto/flappy/
│       ├── FlappyGame.java       # Clase principal de la aplicación
│       ├── config/
│       │   └── GameConfig.java   # Configuración del juego (Singleton)
│       ├── screens/
│       │   └── FlappyScreen.java # Pantalla principal y máquina de estados
│       ├── game/
│       │   ├── Entity.java       # Clase base abstracta (Template Method)
│       │   ├── Bird.java         # Entidad del jugador
│       │   ├── PipePair.java     # Obstáculos
│       │   ├── PipeSystem.java   # Sistema de gestión de tuberías
│       │   ├── CollisionSystem.java
│       │   ├── ScoreSystem.java
│       │   └── strategy/         # Patrón Strategy para movimiento
│       ├── factory/              # Patrón Abstract Factory para temas
│       └── audio/
│           └── SoundManager.java
├── lwjgl3/                        # Plataforma de escritorio (LWJGL3)
│   └── src/main/java/puppy/code/lwjgl3/
│       └── Lwjgl3Launcher.java   # Punto de entrada de la aplicación
├── assets/                        # Recursos del juego
│   ├── bird.png
│   ├── pipe_top.png
│   ├── pipe_bottom.png
│   └── sounds/
├── gradle/                        # Gradle Wrapper
├── gradlew                        # Script de Gradle para Unix/Mac
├── gradlew.bat                    # Script de Gradle para Windows
└── build.gradle                   # Configuración de construcción
```

## Plataformas

- **`core`**: Módulo principal con la lógica de la aplicación compartida por todas las plataformas
- **`lwjgl3`**: Plataforma de escritorio principal usando LWJGL3 (antes llamada 'desktop')

## Compilación y Ejecución

### Opción 1: Ejecutar Directamente (Recomendado)

#### En Windows:
```bash
gradlew.bat lwjgl3:run
```

#### En macOS/Linux:
```bash
./gradlew lwjgl3:run
```

Este comando:
1. Descarga automáticamente las dependencias necesarias
2. Compila el proyecto
3. Ejecuta el juego inmediatamente

### Opción 2: Generar JAR Ejecutable

#### Paso 1: Construir el JAR

**Windows:**
```bash
gradlew.bat lwjgl3:jar
```

**macOS/Linux:**
```bash
./gradlew lwjgl3:jar
```

#### Paso 2: Ejecutar el JAR

El archivo JAR se genera en: `lwjgl3/build/libs/SpaceNav2024-1.0.jar`

Para ejecutarlo:
```bash
java -jar lwjgl3/build/libs/SpaceNav2024-1.0.jar
```

### Opción 3: Importar en IDE

#### IntelliJ IDEA:
1. Abre IntelliJ IDEA
2. Selecciona `File > Open`
3. Navega a la carpeta del proyecto y selecciona el archivo `build.gradle`
4. Haz clic en `Open as Project`
5. Espera a que Gradle sincronice las dependencias
6. Ejecuta la clase `Lwjgl3Launcher` (ubicada en `lwjgl3/src/main/java/puppy/code/lwjgl3/Lwjgl3Launcher.java`)

#### Eclipse:
1. Genera los archivos de proyecto de Eclipse:
   ```bash
   ./gradlew eclipse
   ```
2. Abre Eclipse
3. Selecciona `File > Import > Existing Projects into Workspace`
4. Selecciona la carpeta del proyecto
5. Ejecuta la clase `Lwjgl3Launcher`

## Tareas de Gradle Útiles

### Construcción y Limpieza

| Comando | Descripción |
|---------|-------------|
| `./gradlew build` | Compila y archiva todos los módulos |
| `./gradlew clean` | Elimina las carpetas `build` con clases compiladas |
| `./gradlew lwjgl3:jar` | Construye el JAR ejecutable en `lwjgl3/build/libs/` |
| `./gradlew lwjgl3:run` | Inicia la aplicación directamente |

### IDE

| Comando | Descripción |
|---------|-------------|
| `./gradlew idea` | Genera archivos de proyecto de IntelliJ IDEA |
| `./gradlew eclipse` | Genera archivos de proyecto de Eclipse |
| `./gradlew cleanIdea` | Elimina datos de proyecto de IntelliJ |
| `./gradlew cleanEclipse` | Elimina datos de proyecto de Eclipse |

### Flags Útiles

| Flag | Descripción |
|------|-------------|
| `--continue` | Los errores no detendrán la ejecución de tareas |
| `--daemon` | Usa el daemon de Gradle para ejecutar tareas |
| `--offline` | Usa archivos de dependencias en caché |
| `--refresh-dependencies` | Fuerza la validación de todas las dependencias |

### Ejemplos de Uso

```bash
# Limpiar y construir todo el proyecto
./gradlew clean build

# Ejecutar con daemon para mejor rendimiento
./gradlew --daemon lwjgl3:run

# Construir sin conexión (usando caché)
./gradlew --offline lwjgl3:jar

# Limpiar solo el módulo core
./gradlew core:clean
```

## Controles del Juego

- **ESPACIO**: Iniciar juego / Hacer saltar la nave
- **R**: Reiniciar después de Game Over
- **ESC**: Cerrar el juego

## Configuración del Juego

La configuración del juego se encuentra en `core/src/main/java/com/proyecto/flappy/config/GameConfig.java`:

- Dimensiones de la ventana: 800x480 píxeles
- FPS objetivo: 60 FPS con VSync habilitado
- Dificultad progresiva: Aumenta cada