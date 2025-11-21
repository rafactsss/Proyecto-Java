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
    private Circle circleHitbox;   // opcional: uso interno si quieres

    // --- MÁRGENES PARA EL HITBOX (aquí afinamos el “margen de error”) ---
    // Súbelos si quieres que sea aún más “permisivo”
    private static final float HITBOX_INSET_X = 6f;  // margen horizontal
    private static final float HITBOX_INSET_Y = 4f;  // margen vertical

    private final float gravity = -12f; // gravedad “hacia abajo”

    public Bird() {
        // Carga del sprite (cohete)
        texture = new Texture("bird.png");
        region = new TextureRegion(texture);

        // Posición inicial
        position.set(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y);

        // --- HITBOX RECTANGULAR MÁS PEQUEÑO QUE EL SPRITE ---
        bounds.set(
                position.x + HITBOX_INSET_X,
                position.y + HITBOX_INSET_Y,
                GameConfig.BIRD_WIDTH  - 2f * HITBOX_INSET_X,
                GameConfig.BIRD_HEIGHT - 2f * HITBOX_INSET_Y
        );

        // Hitbox circular opcional, centrado en el pájaro
        circleHitbox = new Circle(
                position.x + GameConfig.BIRD_WIDTH / 2f,
                position.y + GameConfig.BIRD_HEIGHT / 2f,
                (GameConfig.BIRD_WIDTH - 2f * HITBOX_INSET_X) / 2f
        );
    }
    
    // Constructor usado por la Abstract Factory (SpaceFactory)
    public Bird(String spritePath) {
        // Por ahora reutilizamos el mismo comportamiento del constructor por defecto.
        // Si más adelante quieres cambiar el sprite según la factory, aquí puedes usar spritePath.
        this();
    }


 // MARGEN para reducir la hitbox
    private static final float HITBOX_MARGIN = 6f;  // prueba entre 4 y 10

    @Override
    protected void customUpdate(float dt) {

        velocity.y += gravity * dt * 60f;

        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }

        // HITBOX REDUCIDA
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

