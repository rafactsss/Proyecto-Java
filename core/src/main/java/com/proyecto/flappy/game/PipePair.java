package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.config.GameConfig;

public class PipePair {
    public Rectangle top, bottom;
    private Texture topTex, bottomTex;
    private float x;

    public PipePair(float startX) {
        x = startX;
        float gapY = 250 + (float) Math.random() * 200;
        topTex = new Texture("pipe_top.png");
        bottomTex = new Texture("pipe_bottom.png");

        top = new Rectangle(x, gapY + GameConfig.PIPE_GAP / 2, GameConfig.PIPE_WIDTH, 500);
        bottom = new Rectangle(x, gapY - 500 - GameConfig.PIPE_GAP / 2, GameConfig.PIPE_WIDTH, 500);
    }

    public void update(float delta) {
        x -= GameConfig.PIPE_SPEED * delta;
        top.setX(x);
        bottom.setX(x);
    }

    public void render(SpriteBatch batch) {
        batch.draw(topTex, top.x, top.y);
        batch.draw(bottomTex, bottom.x, bottom.y);
    }

    public void dispose() {
        topTex.dispose();
        bottomTex.dispose();
    }

    public float getX() { return x; }
}
