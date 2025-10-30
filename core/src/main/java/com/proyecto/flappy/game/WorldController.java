package com.proyecto.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.proyecto.flappy.config.GameConfig;
import java.util.ArrayList;
import java.util.List;

public class WorldController {
    private Bird bird;
    private List<PipePair> pipes;

    public WorldController() {
        bird = new Bird();
        pipes = new ArrayList<>();
        pipes.add(new PipePair(GameConfig.WORLD_WIDTH));
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) bird.jump();

        bird.update(delta);
        for (PipePair p : pipes) p.update(delta);

        checkCollisions();
        if (pipes.get(pipes.size() - 1).getX() < GameConfig.WORLD_WIDTH - 250) {
            pipes.add(new PipePair(GameConfig.WORLD_WIDTH + 200));
        }
    }

    private void checkCollisions() {
        for (PipePair p : pipes) {
            if (Intersector.overlaps(bird.hitbox, p.top) ||
                Intersector.overlaps(bird.hitbox, p.bottom)) {
                System.out.println("💥 Colisión detectada");
            }
        }
    }

    public void render(SpriteBatch batch) {
        bird.render(batch);
        for (PipePair p : pipes) p.render(batch);
    }

    public void dispose() {
        bird.dispose();
        for (PipePair p : pipes) p.dispose();
    }
}
