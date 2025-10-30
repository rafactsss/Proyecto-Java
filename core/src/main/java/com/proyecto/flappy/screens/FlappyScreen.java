package com.proyecto.flappy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyecto.flappy.MainFlappyGame;
import com.proyecto.flappy.game.WorldController;

public class FlappyScreen extends ScreenAdapter {
    private final MainFlappyGame game;
    private SpriteBatch batch;
    private WorldController world;

    public FlappyScreen(MainFlappyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        world = new WorldController();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.7f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        world.render(batch);
        batch.end();

        world.update(delta);
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
}
