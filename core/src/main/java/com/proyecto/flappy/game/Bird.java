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
    private static final float HITBOX_MARGIN = 7f;  
  
    public Bird() {  
        texture = new Texture("bird.png");  
        region = new TextureRegion(texture);  
  
        GameConfig config = GameConfig.getInstance();  
          
        position.set(config.BIRD_START_X, config.BIRD_START_Y);  
  
        bounds.set(  
                position.x + HITBOX_MARGIN,  
                position.y + HITBOX_MARGIN,  
                config.BIRD_WIDTH - 2f * HITBOX_MARGIN,  
                config.BIRD_HEIGHT - 2f * HITBOX_MARGIN  
        );  
  
        circleHitbox = new Circle(  
                position.x + config.BIRD_WIDTH / 2f,  
                position.y + config.BIRD_HEIGHT / 2f,  
                (config.BIRD_WIDTH - 2f * HITBOX_MARGIN) / 2f  
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
  
        GameConfig config = GameConfig.getInstance();  
          
        // Actualizar hitbox  
        bounds.set(  
                position.x + HITBOX_MARGIN,  
                position.y + HITBOX_MARGIN,  
                config.BIRD_WIDTH - HITBOX_MARGIN * 2,  
                config.BIRD_HEIGHT - HITBOX_MARGIN * 2  
        );  
  
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
        return circleHitbox;  
    }  
  
    public void dispose() {  
        if (texture != null) texture.dispose();  
    }  
}

