package puppy.code.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.proyecto.flappy.FlappyGame;


/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new FlappyGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Flappy SpaceNav 2024");

        // Tamaño de ventana según GameConfig (modo ventana)
        configuration.setWindowedMode(800, 480);

        // FPS & Vsync
        configuration.useVsync(true);
        configuration.setForegroundFPS(60);

        // No usar íconos (evita crash)
        // Si luego agregas un icono, puedes restaurar esta línea:
        // configuration.setWindowIcon("icon128.png", "icon64.png", "icon32.png", "icon16.png");

        return configuration;
    }
}
