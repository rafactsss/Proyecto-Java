package com.proyecto.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.config.GameConfig;

public class Bird extends Entity implements Collidable {

    private final Texture texture;
    private float vy = 0f;
    private final float gravity = -900f;     // px/s^2
    private final float jumpImpulse = 300f;  // px/s

    public Bird() {
        texture = new Texture(Gdx.files.internal("bird.png"));

        // posición inicial
        position.set(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y);

        // hitbox del tamaño del sprite (ajústalo si quieres “perdón” en bordes)
        bounds.set(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update(float dt) {
        vy += gravity * dt;
        position.y += vy * dt;

        // piso
        if (position.y < 0) {
            position.y = 0;
            vy = 0;
        }
        // techo
        if (position.y + bounds.height > GameConfig.WORLD_HEIGHT) {
            position.y = GameConfig.WORLD_HEIGHT - bounds.height;
            vy = 0;
        }

        bounds.setPosition(position.x, position.y);
    }

    public void jump() { vy = jumpImpulse; }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    // === Collidable ===
    @Override
    public Rectangle getHitbox() { 
        // devolvemos el mismo rectángulo de Entity
        return bounds; 
    }

    @Override
    public void onCollision(Entity other) { active = false; }

    public void dispose() { texture.dispose(); }
}
