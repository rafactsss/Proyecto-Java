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

    // --- Strategy ---
    private MovementStrategy movementStrategy;

    public Bird() {
        loadSprite("bird.png");
    }

    // Constructor para Abstract Factory
    public Bird(String spritePath) {
        loadSprite(spritePath);
    }

    private void loadSprite(String spritePath) {
        texture = new Texture(spritePath);
        region = new TextureRegion(texture);

        // Posición inicial
        position.set(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y);

        // Hitbox Rectangle (principal para colisiones)
        bounds.set(position.x, position.y,
                   GameConfig.BIRD_WIDTH, GameConfig.BIRD_HEIGHT);

        // Hitbox Circle (opcional)
        circleHitbox = new Circle(
                position.x + GameConfig.BIRD_WIDTH / 2f,
                position.y + GameConfig.BIRD_HEIGHT / 2f,
                GameConfig.BIRD_WIDTH / 2f
        );

        // Strategy por defecto
        movementStrategy = new NormalMovement();
    }

    @Override
    protected void customUpdate(float dt) {

        // Aplicar la Strategy en vez de gravedad fija
        movementStrategy.update(this, dt);

        // Suelo
        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }

        // Actualizar Rectangle hitbox
        bounds.setPosition(position.x, position.y);

        // Actualizar Circle hitbox
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

    // --- Bird actions ---

    public void jump() {
        velocity.y = 250f;
    }

    // --- Strategy ---

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    // --- Collidable ---

    @Override
    public Rectangle getHitbox() {
        return bounds;
    }

    @Override
    public void onCollision(Entity other) {
        active = false;
        SoundManager.getInstance().playHit();
    }

    // Opcional
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


