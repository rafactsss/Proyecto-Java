package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.config.GameConfig;
import com.proyecto.flappy.audio.SoundManager;

public class Bird extends Entity implements Collidable {

    private Texture texture;
    private TextureRegion region;
    private Circle circleHitbox;   // opcional: para uso interno si quieres

    private final float gravity = -12f; // gravedad “hacia abajo”

    public Bird() {
        // Carga del sprite
        texture = new Texture("bird.png");
        region = new TextureRegion(texture);

        // Posición inicial
        position.set(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y);

        // Hitbox rectangular, alineado al tamaño que quieres dibujar
        bounds.set(position.x, position.y,
                   GameConfig.BIRD_WIDTH, GameConfig.BIRD_HEIGHT);

        // Hitbox circular opcional, centrado en el pájaro
        circleHitbox = new Circle(
                position.x + GameConfig.BIRD_WIDTH / 2f,
                position.y + GameConfig.BIRD_HEIGHT / 2f,
                GameConfig.BIRD_WIDTH / 2f
        );
    }

    @Override
    protected void customUpdate(float dt) {
        // Aplicar gravedad
        velocity.y += gravity * dt * 60f;

        // Evitar que se vaya bajo el suelo
        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }

        // bounds se actualiza en applyPhysics(), pero reforzamos por claridad
        bounds.setPosition(position.x, position.y);

        // Actualizar círculo opcional
        circleHitbox.setPosition(
                position.x + GameConfig.BIRD_WIDTH / 2f,
                position.y + GameConfig.BIRD_HEIGHT / 2f
        );
    }

    @Override
    public void render(SpriteBatch batch) {
        // Dibujar el cohete reescalado al tamaño definido en GameConfig
        batch.draw(region, position.x, position.y,
                   GameConfig.BIRD_WIDTH, GameConfig.BIRD_HEIGHT);
    }

    // --- Comportamiento específico del pájaro ---

    public void jump() {
        velocity.y = 250f;
    }

    // --- Collidable ---

    @Override
    public Rectangle getHitbox() {
        // El sistema de colisión trabaja con Rectangle
        return bounds;
    }

    @Override
    public void onCollision(Entity other) {
        // Qué pasa cuando el pájaro choca
        active = false;
        SoundManager.playHit();
        // Aquí podrías avisar a FlappyScreen que es game over, etc.
    }

    // Getter opcional si necesitas el hitbox circular en otro lado
    public Circle getCircleHitbox() {
        return circleHitbox;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }
}

