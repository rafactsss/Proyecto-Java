package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.config.GameConfig;
import com.proyecto.flappy.game.strategy.PipeMovementStrategy;
import com.proyecto.flappy.game.strategy.HorizontalPipeMovement;

import java.util.Arrays;

public class PipePair implements Collidable {

    // ----------- TEXTURAS COMPARTIDAS -----------
    private static Texture TEX_TOP;
    private static Texture TEX_BOTTOM;

    public static void loadTextures() {
        if (TEX_TOP == null)    TEX_TOP    = new Texture("pipe_top.png");
        if (TEX_BOTTOM == null) TEX_BOTTOM = new Texture("pipe_bottom.png");
    }

    public static void disposeTextures() {
        if (TEX_TOP != null) { TEX_TOP.dispose(); TEX_TOP = null; }
        if (TEX_BOTTOM != null) { TEX_BOTTOM.dispose(); TEX_BOTTOM = null; }
    }


    // ----------- ATRIBUTOS DEL PIPE -----------
    private float x;
    private float width;
    private float centerY;
    private float gapSize;
    private float speed;

    public boolean scored = false;

    private final Rectangle top = new Rectangle();
    private final Rectangle bottom = new Rectangle();

    private PipeMovementStrategy movementStrategy = new HorizontalPipeMovement();


    // ----------- CONSTRUCTORES -----------
    public PipePair(float startX, float centerY, float gapSize, float speed) {
        loadTextures();
        this.x = startX;
        this.centerY = centerY;
        this.gapSize = gapSize;
        this.speed = speed;
        this.width = GameConfig.getInstance().PIPE_WIDTH;
        recomputeRects();
    }

    public PipePair(float startX) {
        this(
            startX,
            MathUtils.clamp(
                GameConfig.getInstance().WORLD_HEIGHT * 0.5f,
                GameConfig.getInstance().GAP_MARGIN_BOTTOM + GameConfig.getInstance().PIPE_GAP_START * 0.5f,
                GameConfig.getInstance().WORLD_HEIGHT - (GameConfig.getInstance().GAP_MARGIN_TOP + GameConfig.getInstance().PIPE_GAP_START * 0.5f)
            ),
            GameConfig.getInstance().PIPE_GAP_START,
            GameConfig.getInstance().PIPE_BASE_SPEED
        );
    }


    // ----------- UPDATE & RENDER -----------
    public void update(float dt) {

        if (movementStrategy != null)
            movementStrategy.update(this, dt);

        top.setX(x);
        bottom.setX(x);
    }

    public void render(SpriteBatch batch) {
        batch.draw(TEX_TOP, top.x, top.y + top.height, top.width, -top.height);
        batch.draw(TEX_BOTTOM, bottom.x, bottom.y, bottom.width, bottom.height);
    }


    // ----------- HITBOXES -----------
    private void recomputeRects() {
        GameConfig config = GameConfig.getInstance();
        float half = gapSize * 0.5f;

        top.set(x, centerY + half, width, config.PIPE_HEIGHT);
        bottom.set(x, centerY - half - config.PIPE_HEIGHT, width, config.PIPE_HEIGHT);
    }

    public Rectangle getTopBounds() { return top; }
    public Rectangle getBottomBounds() { return bottom; }


    // ----------- GETTERS / SETTERS -----------

    public float getX() { return x; }

    public float getRight() { return x + width; }

    public boolean isOffScreen() { return getRight() < 0; }

    public float getSpeed() { return speed; }      // ← NECESARIO PARA STRATEGY

    public float getCenterY() { return centerY; }  // ← NECESARIO PARA STRATEGY
    
    public float getCenterX() { return x + width * 0.5f; }

    public void setSpeed(float speed) { this.speed = speed; }

    public void setGap(float gap) { 
        this.gapSize = gap; 
        recomputeRects(); 
    }

    public void setX(float x) { 
        this.x = x; 
        top.setX(x);
        bottom.setX(x);
    }

    public void setCenterY(float cy) {
        this.centerY = cy;
        recomputeRects();
    }

    public void setMovementStrategy(PipeMovementStrategy strategy) {
        this.movementStrategy = strategy;
    }


    // ----------- COLLIDABLE IMPLEMENTACIÓN -----------

    @Override
    public Rectangle getHitbox() {
        // Se requiere por la interfaz pero NO lo usamos (usamos varios hitboxes)
        return top;
    }

    @Override
    public Iterable<Rectangle> getHitboxes() {
        return Arrays.asList(top, bottom);
    }

    @Override
    public void onCollision(Entity other) {
        // Los tubos no reaccionan ante colisión
    }
}
