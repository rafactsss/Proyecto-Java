package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.proyecto.flappy.config.GameConfig;
import com.proyecto.flappy.audio.SoundManager;
import com.proyecto.flappy.game.strategy.MovementStrategy;
import com.proyecto.flappy.game.strategy.NormalMovement;

public class Bird extends Entity implements Collidable {

    private Texture texture;
    private TextureRegion region;

    // HITBOX RECTANGULAR SUAVE
    private float hitboxWidth;
    private float hitboxHeight;
    private float hitboxOffsetX;
    private float hitboxOffsetY;

    // Hitbox circular opcional (no usado)
    private final Circle circleHitbox;

    // Estrategia de movimiento
    private MovementStrategy movementStrategy = new NormalMovement();

    public final Vector2 velocity = new Vector2();

    public Bird() {
        texture = new Texture("bird.png");
        region = new TextureRegion(texture);

        GameConfig config = GameConfig.getInstance();
        position.set(config.BIRD_START_X, config.BIRD_START_Y);

        // Ajuste suave basado en el sprite (60x60 px)
        hitboxWidth  = config.BIRD_WIDTH * 0.55f;   // ~55% del ancho real
        hitboxHeight = config.BIRD_HEIGHT * 0.45f;  // ~45% del alto
        hitboxOffsetX = (config.BIRD_WIDTH  - hitboxWidth)  / 2f;
        hitboxOffsetY = (config.BIRD_HEIGHT - hitboxHeight) / 2f;

        bounds.set(
                position.x + hitboxOffsetX,
                position.y + hitboxOffsetY,
                hitboxWidth,
                hitboxHeight
        );

        // Hitbox circular (solo por compatibilidad)
        float r = Math.min(hitboxWidth, hitboxHeight) * 0.6f;
        circleHitbox = new Circle(
                position.x + config.BIRD_WIDTH / 2f,
                position.y + config.BIRD_HEIGHT / 2f,
                r
        );
    }

    public Bird(String spritePath) {
        this();
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    @Override
    protected void customUpdate(float dt) {
        if (movementStrategy != null) {
            movementStrategy.update(this, dt);
        }

        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }

        bounds.set(
                position.x + hitboxOffsetX,
                position.y + hitboxOffsetY,
                hitboxWidth,
                hitboxHeight
        );

        GameConfig config = GameConfig.getInstance();
        circleHitbox.setPosition(
                position.x + config.BIRD_WIDTH / 2f,
                position.y + config.BIRD_HEIGHT / 2f
        );
    }

    @Override
    public void render(SpriteBatch batch) {
        GameConfig config = GameConfig.getInstance();
        batch.draw(region, position.x, position.y,
                config.BIRD_WIDTH, config.BIRD_HEIGHT);
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
        return circleHitbox; // no usado en colisiones
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
