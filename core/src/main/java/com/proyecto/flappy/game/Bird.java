package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.config.GameConfig;
import com.proyecto.flappy.audio.SoundManager;
import com.proyecto.flappy.game.strategy.MovementStrategy;
import com.proyecto.flappy.game.strategy.NormalMovement;

public class Bird extends Entity implements Collidable {

    private Texture texture;
    private TextureRegion region;
    private Circle circleHitbox;

    // Estrategia (STRATEGY PATTERN)
    private MovementStrategy movementStrategy = new NormalMovement();

    // MÁRGENES HITBOX
    private static final float HITBOX_MARGIN = 6f;

    public Bird() {
        texture = new Texture("bird.png");
        region = new TextureRegion(texture);

        position.set(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y);

        bounds.set(
                position.x + HITBOX_MARGIN,
                position.y + HITBOX_MARGIN,
                GameConfig.BIRD_WIDTH - 2f * HITBOX_MARGIN,
                GameConfig.BIRD_HEIGHT - 2f * HITBOX_MARGIN
        );

        circleHitbox = new Circle(
                position.x + GameConfig.BIRD_WIDTH / 2f,
                position.y + GameConfig.BIRD_HEIGHT / 2f,
                (GameConfig.BIRD_WIDTH - 2f * HITBOX_MARGIN) / 2f
        );
    }

    // Constructor usado por SpaceFactory
    public Bird(String spritePath) {
        this();
    }

    // ----- STRATEGY: permitir cambiar en runtime -----
    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    @Override
    protected void customUpdate(float dt) {

        // Strategy controla la física del movimiento
        if (movementStrategy != null) {
            movementStrategy.update(this, dt);
        }

        // límites del piso
        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }

        // Actualizar hitbox
        bounds.set(
                position.x + HITBOX_MARGIN,
                position.y + HITBOX_MARGIN,
                GameConfig.BIRD_WIDTH - HITBOX_MARGIN * 2,
                GameConfig.BIRD_HEIGHT - HITBOX_MARGIN * 2
        );

        circleHitbox.setPosition(
                position.x + GameConfig.BIRD_WIDTH / 2f,
                position.y + GameConfig.BIRD_HEIGHT / 2f
        );
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(region, position.x, position.y,
                GameConfig.BIRD_WIDTH, GameConfig.BIRD_HEIGHT);
    }

    public void jump() {
        velocity.y = 250f;
    }

    @Override
    public Rectangle getHitbox() {
        return bounds;
    }

    @Override
    public void onCollision(Entity other) {
        active = false;
        SoundManager.playHit();
    }

    public Circle getCircleHitbox() {
        return circleHitbox;
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}

