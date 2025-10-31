package com.proyecto.flappy.config;

public class GameConfig {

    // Dimensiones del mundo de juego
    public static final int WORLD_WIDTH  = 800;
    public static final int WORLD_HEIGHT = 480;

    // Bird
    public static final float BIRD_START_X = 160f;
    public static final float BIRD_START_Y = WORLD_HEIGHT / 2f;

    // Tubos
    public static final float PIPE_WIDTH   = 52f;
    public static final float PIPE_HEIGHT  = 320f;

    // Gap (hueco)
    public static final float PIPE_GAP_START = 150f;
    public static final float PIPE_GAP_MIN   = 120f;

    // Márgenes (evitan huecos imposibles)
    public static final float GAP_MARGIN_TOP    = 40f;
    public static final float GAP_MARGIN_BOTTOM = 60f;

    // Movimiento base
    public static final float PIPE_BASE_SPEED = 140f; // px/s

    // Spawn (cada cuántos segundos sale un tubo)
    public static final float PIPE_SPAWN_EVERY = 1.35f;

    // Dificultad progresiva
    public static final float DIFF_EVERY_SEC = 12f;
    public static final float SPEED_STEP  = 12f;  // acelera
    public static final float SPEED_MAX   = 220f; // tope
    public static final float GAP_STEP    = -4f;  // acorta hueco

    private GameConfig() {}
}

