package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.proyecto.flappy.config.GameConfig;

import java.util.ArrayDeque;
import java.util.Deque;

public class PipeSystem {

    private final Deque<PipePair> pipes = new ArrayDeque<>();
    private float spawnTimer = 0f, diffTimer = 0f;

    private float currentSpeed = GameConfig.PIPE_BASE_SPEED;
    private float currentGap   = GameConfig.PIPE_GAP_START;

    // --- Márgenes de seguridad para un arranque sin “muerte instantánea” ---
    /** cuán lejos del borde derecho nace el 1er tubo (en píxeles) */
    private static final float SAFE_START_OFFSET = 280f;
    /** separación mínima entre tubos, por si el cálculo dinámico da demasiado poco */
    private static final float MIN_SPACING       = 240f;

    public PipeSystem() { reset(); }

    public void reset() {
        pipes.clear();
        spawnTimer = diffTimer = 0f;
        currentSpeed = GameConfig.PIPE_BASE_SPEED;
        currentGap   = GameConfig.PIPE_GAP_START;

        float x = GameConfig.WORLD_WIDTH + 160f;     // antes +60
        for (int i = 0; i < 3; i++) { pipes.add(createPair(x)); x += spacingPixels(); }
    }

    public void update(float dt) {
        for (PipePair p : pipes) p.update(dt);

        spawnTimer += dt;
        if (spawnTimer >= GameConfig.PIPE_SPAWN_EVERY) {
            spawnTimer -= GameConfig.PIPE_SPAWN_EVERY;
            pipes.add(createPair(nextSpawnX()));
        }

        // limpiar los que salieron completamente de pantalla
        while (!pipes.isEmpty() && pipes.peekFirst().isOffScreen()) {
            pipes.removeFirst();
        }

        // dificultad (más rápido / menos gap)
        diffTimer += dt;
        if (diffTimer >= GameConfig.DIFF_EVERY_SEC) {
            diffTimer -= GameConfig.DIFF_EVERY_SEC;

            currentSpeed = Math.min(currentSpeed + GameConfig.SPEED_STEP, GameConfig.SPEED_MAX);
            currentGap   = Math.max(currentGap   + GameConfig.GAP_STEP,  GameConfig.PIPE_GAP_MIN);

            for (PipePair p : pipes) {
                p.setSpeed(currentSpeed);
                p.setGap(currentGap);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (PipePair p : pipes) p.render(batch);
    }

    public Iterable<PipePair> getPipes() { return pipes; }

    private float nextSpawnX() {
        return pipes.isEmpty()
                ? GameConfig.WORLD_WIDTH + SAFE_START_OFFSET
                : pipes.peekLast().getX() + spacingPixels();
    }

    private float spacingPixels() {
        // más distancia inicial entre tubos
        return currentSpeed * GameConfig.PIPE_SPAWN_EVERY * 1.35f;
    }

    private PipePair createPair(float startX) {
        float minC = GameConfig.GAP_MARGIN_BOTTOM + currentGap * 0.5f;
        float maxC = GameConfig.WORLD_HEIGHT - (GameConfig.GAP_MARGIN_TOP + currentGap * 0.5f);
        float cy = MathUtils.random(minC, maxC);
        return new PipePair(startX, cy, currentGap, currentSpeed);
    }
    public void dispose() {
        pipes.clear();                 // por prolijidad
        PipePair.disposeTextures();    // libera las texturas compartidas
    }


}
