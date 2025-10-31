package com.proyecto.flappy.config;

public class GameConfig {
    public static final int WORLD_WIDTH  = 800;
    public static final int WORLD_HEIGHT = 480;

    // --- pájaro (mejor más alto al inicio)
    public static final float BIRD_START_X = 160f;
    public static final float BIRD_START_Y = WORLD_HEIGHT * 0.60f; // antes 0.50f

    // --- tubos
    public static final float PIPE_WIDTH   = 52f;
    public static final float PIPE_HEIGHT  = 320f;

    // --- gap
    public static final float PIPE_GAP_START = 160f; // +10 px de respiro
    public static final float PIPE_GAP_MIN   = 130f;

    // --- márgenes (eleva bastante el fondo para no “rozar” la nave rosa)
    public static final float GAP_MARGIN_TOP    = 44f;
    public static final float GAP_MARGIN_BOTTOM = 150f; // ¡clave! (antes 60)

    // --- movimiento/base
    public static final float PIPE_BASE_SPEED = 140f;
    public static final float PIPE_SPAWN_EVERY = 1.35f;

    // --- progresión
    public static final float DIFF_EVERY_SEC = 12f;
    public static final float SPEED_STEP  = 10f;
    public static final float SPEED_MAX   = 220f;
    public static final float GAP_STEP    = -4f;

    private GameConfig() {}
}
