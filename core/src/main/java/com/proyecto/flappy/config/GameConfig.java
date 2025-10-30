package com.proyecto.flappy.config;

public class GameConfig {
    public static final float WORLD_WIDTH = 480f;
    public static final float WORLD_HEIGHT = 800f;

    public static final float BIRD_START_X = 100f;
    public static final float BIRD_START_Y = WORLD_HEIGHT / 2;

    public static final float PIPE_WIDTH = 80f;
    public static final float PIPE_GAP = 150f;
    public static final float PIPE_SPEED = 120f;

    private GameConfig() {} // evita instanciación
}
