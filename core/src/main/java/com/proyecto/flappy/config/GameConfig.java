package com.proyecto.flappy.config;  
  
public class GameConfig {  
      
    // Instancia única (Singleton)  
    private static GameConfig instance;  
      
    // Dimensiones del mundo de juego  
    public final int WORLD_WIDTH  = 800;  
    public final int WORLD_HEIGHT = 480;  
  
    // Bird  
    public final float BIRD_WIDTH = 90f;   // ajusta según tu sprite bird.png  
    public final float BIRD_HEIGHT = 90f;  // ajusta según tu sprite bird.png  
    public final float BIRD_START_X = 160f;  
    public final float BIRD_START_Y = WORLD_HEIGHT / 2f;
    // Tubos  
    public final float PIPE_WIDTH   = 90f;  
    public final float PIPE_HEIGHT  = 320f;  
  
    // Gap (hueco)  
    public final float PIPE_GAP_START = 150f;  
    public final float PIPE_GAP_MIN   = 120f;  
   
    // Márgenes  
    public final float GAP_MARGIN_TOP    = 40f;  
    public final float GAP_MARGIN_BOTTOM = 60f;  
  
    // Movimiento base  
    public final float PIPE_BASE_SPEED = 140f;  
  
    // Spawn  
    public final float PIPE_SPAWN_EVERY = 1.35f;  
  
    // Dificultad progresiva  
    public final float DIFF_EVERY_SEC = 12f;  
    public final float SPEED_STEP  = 12f;  
    public final float SPEED_MAX   = 220f;  
    public final float GAP_STEP    = -4f;  
  
    // Constructor privado (previene instanciación externa)  
    private GameConfig() {}  
      
    // Método de acceso global (Lazy initialization)  
    public static GameConfig getInstance() {  
        if (instance == null) {  
            instance = new GameConfig();  
        }  
        return instance;  
    }  
}
