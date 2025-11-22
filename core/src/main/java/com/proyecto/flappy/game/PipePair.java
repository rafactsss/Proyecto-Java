package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.config.GameConfig;
import com.proyecto.flappy.game.strategy.PipeMovementStrategy;
import com.proyecto.flappy.game.strategy.HorizontalPipeMovement;

public class PipePair {

    private static Texture TEX_TOP;
    private static Texture TEX_BOTTOM;

    public static void loadTextures() {
        if (TEX_TOP == null)    TEX_TOP    = new Texture("pipe_top.png");
        if (TEX_BOTTOM == null) TEX_BOTTOM = new Texture("pipe_bottom.png");
    }
    public static void disposeTextures() {
        if (TEX_TOP != null)    { TEX_TOP.dispose();    TEX_TOP = null; }
        if (TEX_BOTTOM != null) { TEX_BOTTOM.dispose(); TEX_BOTTOM = null; }
    }

    private float x;
    private float width;
    private float centerY;
    private float gapSize;
    private float speed;

    public boolean scored = false;

    // Rectángulos para DIBUJAR (tamaño completo del sprite)
    private final Rectangle top    = new Rectangle();
    private final Rectangle bottom = new Rectangle();

    // Rectángulos para COLISIÓN (más pequeños = hitbox suave)
    private final Rectangle topHitbox    = new Rectangle();
    private final Rectangle bottomHitbox = new Rectangle();

    // Margen usado para el hitbox suave
    private float hitboxMarginX;
    private float hitboxMarginY;

    // Estrategia de movimiento (Strategy Pattern)
    private PipeMovementStrategy movementStrategy = new HorizontalPipeMovement();

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
        this(startX,
            MathUtils.clamp(
                GameConfig.getInstance().WORLD_HEIGHT * 0.5f,
                GameConfig.getInstance().GAP_MARGIN_BOTTOM + GameConfig.getInstance().PIPE_GAP_START * 0.5f,
                GameConfig.getInstance().WORLD_HEIGHT - (GameConfig.getInstance().GAP_MARGIN_TOP + GameConfig.getInstance().PIPE_GAP_START * 0.5f)
            ),
            GameConfig.getInstance().PIPE_GAP_START,
            GameConfig.getInstance().PIPE_BASE_SPEED);
    }

    public void update(float dt) {
        // Delegar movimiento a la estrategia
        if (movementStrategy != null) {
            movementStrategy.update(this, dt);
        }

        // Actualizar posiciones en X de los rectángulos de dibujo
        top.setX(x);
        bottom.setX(x);

        // Actualizar posiciones en X de los hitboxes suaves
        topHitbox.setX(x + hitboxMarginX);
        bottomHitbox.setX(x + hitboxMarginX);
    }

    public void render(SpriteBatch batch) {
        // Usamos los rectángulos grandes para dibujar, NO los suaves
        batch.draw(TEX_TOP,    top.x,    top.y    + top.height,    top.width,    -top.height);
        batch.draw(TEX_BOTTOM, bottom.x, bottom.y,                 bottom.width, bottom.height);
    }

    private void recomputeRects() {
        GameConfig config = GameConfig.getInstance();
        float halfGap = gapSize * 0.5f;
        float fullH   = config.PIPE_HEIGHT;

        // ----- Rectángulos completos (para dibujar) -----
        top.set(
                x,
                centerY + halfGap,
                width,
                fullH
        );
        bottom.set(
                x,
                centerY - halfGap - fullH,
                width,
                fullH
        );

        // ----- Hitbox SUAVE -----
        // Contraemos un poco en X e Y para dar margen al jugador
        hitboxMarginX = width * 0.12f;      // 12% por cada lado
        hitboxMarginY = fullH * 0.06f;      // 6% arriba/abajo

        // Top: recortamos un poco por todos los lados
        topHitbox.set(
                top.x + hitboxMarginX,
                top.y + hitboxMarginY,
                top.width  - 2f * hitboxMarginX,
                top.height - 2f * hitboxMarginY
        );

        // Bottom: recortamos arriba y a los lados, pero dejamos base en Y
        bottomHitbox.set(
                bottom.x + hitboxMarginX,
                bottom.y,
                bottom.width  - 2f * hitboxMarginX,
                bottom.height - hitboxMarginY
        );
    }

    // Getters para colisión (ahora devuelven los hitboxes suaves)
    public Rectangle getTopBounds()    { return topHitbox; }
    public Rectangle getBottomBounds() { return bottomHitbox; }

    // Getters de posición / tamaño (no cambian)
    public float getX()          { return x; }
    public float getRight()      { return x + width; }
    public boolean isOffScreen() { return getRight() < 0; }
    public float getCenterX()    { return x + width * 0.5f; }

    // NUEVO: Getter para centerY
    public float getCenterY()    { return centerY; }

    // NUEVO: Getter para speed
    public float getSpeed()      { return speed; }

    // Setters existentes
    public void setSpeed(float speed)  { this.speed = speed; }
    public void setGap(float gap)      { this.gapSize = gap; recomputeRects(); }

    // Setter para x (usado por estrategias)
    public void setX(float x) {
        this.x = x;
        // No llamamos a recomputeRects() aquí para no recalcular Y/alturas.
        // Solo se actualizan las posiciones en update().
    }

    // Setter para centerY (usado por estrategias)
    public void setCenterY(float centerY) {
        this.centerY = centerY;
        recomputeRects(); // Recalcular rectángulos cuando cambia centerY
    }

    // Cambiar estrategia en runtime
    public void setMovementStrategy(PipeMovementStrategy strategy) {
        this.movementStrategy = strategy;
    }
}
