package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.proyecto.flappy.config.GameConfig;

public class Bird {
    private Texture texture;
    public Circle hitbox;
    private float x, y;
    private float velocityY = 0;
    private final float gravity = -12;

    public Bird() {
        texture = new Texture("bird.png"); // luego pondrás esta imagen en assets
        x = GameConfig.BIRD_START_X;
        y = GameConfig.BIRD_START_Y;
        hitbox = new Circle(x, y, texture.getWidth() / 2f);
    }

    public void update(float delta) {
        velocityY += gravity * delta * 60;
        y += velocityY * delta * 60;
        if (y < 0) y = 0;
        hitbox.setPosition(x + texture.getWidth() / 2f, y + texture.getHeight() / 2f);
    }

    public void jump() {
        velocityY = 250;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getY() { return y; }
}
