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

    public PipeSystem() { reset(); }

    public void reset() {
        pipes.clear();
        spawnTimer = diffTimer = 0f;
        currentSpeed = GameConfig.PIPE_BASE_SPEED;
        currentGap   = GameConfig.PIPE_GAP_START;

        float x = GameConfig.WORLD_WIDTH + 60f;
        for (int i = 0; i < 3; i++) { pipes.add(createPair(x)); x += spacingPixels(); }
    }

    public void update(float dt) {
        for (PipePair p : pipes) p.update(dt);

        spawnTimer += dt;
        if (spawnTimer >= GameConfig.PIPE_SPAWN_EVERY) {
            spawnTimer -= GameConfig.PIPE_SPAWN_EVERY;
            pipes.add(createPair(nextSpawnX()));
        }

        while (!pipes.isEmpty() && pipes.peekFirst().isOffScreen()) pipes.removeFirst();

        diffTimer += dt;
        if (diffTimer >= GameConfig.DIFF_EVERY_SEC) {
            diffTimer -= GameConfig.DIFF_EVERY_SEC;
            currentSpeed = Math.min(currentSpeed + GameConfig.SPEED_STEP, GameConfig.SPEED_MAX);
            currentGap   = Math.max(currentGap   + GameConfig.GAP_STEP,  GameConfig.PIPE_GAP_MIN);
            for (PipePair p : pipes) { p.setSpeed(currentSpeed); p.setGap(currentGap); }
        }
    }

    public void render(SpriteBatch batch) { for (PipePair p : pipes) p.render(batch); }

    // ← ESTO usa tu FlappyScreen: pipes.getPipes()
    public Iterable<PipePair> getPipes() { return pipes; }

    private float nextSpawnX() {
        return pipes.isEmpty() ? GameConfig.WORLD_WIDTH + 40f
                               : pipes.peekLast().getX() + spacingPixels();
    }
    private float spacingPixels() { return currentSpeed * GameConfig.PIPE_SPAWN_EVERY * 1.1f; }

    private PipePair createPair(float startX) {
        float minC = GameConfig.GAP_MARGIN_BOTTOM + currentGap * 0.5f;
        float maxC = GameConfig.WORLD_HEIGHT - (GameConfig.GAP_MARGIN_TOP + currentGap * 0.5f);
        float cy = MathUtils.random(minC, maxC);
        return new PipePair(startX, cy, currentGap, currentSpeed);
    }
}


