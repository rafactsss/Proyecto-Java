package com.proyecto.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.config.GameConfig;

public class Bird extends Entity implements Collidable {

    private final Texture texture;
    private float vy = 0f;
    private final float gravity = -900f;
    private final float jumpImpulse = 300f;

    // Dibujo y colisión “amables”
    private static final float DRAW_W = 36f; // pinta más chico que el sprite real
    private static final float DRAW_H = 26f;
    private static final float PAD = 4f;     // recorta hitbox por cada lado

    public Bird() {
        // OJO con el nombre: pon el archivo como "bird.png" (todo minúsculas) para evitar líos en otros SO
        texture = new Texture(Gdx.files.internal("bird.png"));

        position.set(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y);

        // hitbox: un poco más chica que el dibujo
        bounds.set(position.x + PAD, position.y + PAD, DRAW_W - PAD * 2f, DRAW_H - PAD * 2f);
    }

    @Override
    public void update(float dt) {
        vy += gravity * dt;
        position.y += vy * dt;

        if (position.y < 0) { position.y = 0; vy = 0; }
        if (position.y + DRAW_H > GameConfig.WORLD_HEIGHT) {
            position.y = GameConfig.WORLD_HEIGHT - DRAW_H;
            vy = 0;
        }
        bounds.setPosition(position.x + PAD, position.y + PAD);
    }

    public void jump() { vy = jumpImpulse; }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, DRAW_W, DRAW_H);
    }

    @Override public Rectangle getHitbox() { return bounds; }
    @Override public void onCollision(Entity other) { active = false; }

    public void dispose() { texture.dispose(); }
}
