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
  
    private final Rectangle top = new Rectangle();  
    private final Rectangle bottom = new Rectangle();  
      
    // NUEVO: Estrategia de movimiento (Strategy Pattern)  
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
        // MODIFICADO: Delegar movimiento a la estrategia  
        if (movementStrategy != null) {  
            movementStrategy.update(this, dt);  
        }  
          
        // Actualizar posiciones de los rectángulos  
        top.setX(x);  
        bottom.setX(x);  
    }  
  
    public void render(SpriteBatch batch) {  
        batch.draw(TEX_TOP, top.x, top.y + top.height, top.width, -top.height);  
        batch.draw(TEX_BOTTOM, bottom.x, bottom.y, bottom.width, bottom.height);  
    }  
  
    private void recomputeRects() {  
        GameConfig config = GameConfig.getInstance();  
        float half = gapSize * 0.5f;  
        top.set(x, centerY + half, width, config.PIPE_HEIGHT);  
        bottom.set(x, centerY - half - config.PIPE_HEIGHT, width, config.PIPE_HEIGHT);  
    }  
  
    // Getters existentes  
    public Rectangle getTopBounds()    { return top; }  
    public Rectangle getBottomBounds() { return bottom; }  
    public float getX()                { return x; }  
    public float getRight()            { return x + width; }  
    public boolean isOffScreen()       { return getRight() < 0; }  
    public float getCenterX()          { return x + width * 0.5f; }  
      
    // NUEVO: Getter para centerY  
    public float getCenterY()          { return centerY; }  
      
    // NUEVO: Getter para speed  
    public float getSpeed()            { return speed; }  
      
    // Setters existentes  
    public void setSpeed(float speed)  { this.speed = speed; }  
    public void setGap(float gap)      { this.gapSize = gap; recomputeRects(); }  
      
    // NUEVO: Setter para x (usado por estrategias)  
    public void setX(float x) {  
        this.x = x;  
    }  
      
    // NUEVO: Setter para centerY (usado por estrategias)  
    public void setCenterY(float centerY) {  
        this.centerY = centerY;  
        recomputeRects(); // Recalcular rectángulos cuando cambia centerY  
    }  
      
    // NUEVO: Método para cambiar estrategia en runtime  
    public void setMovementStrategy(PipeMovementStrategy strategy) {  
        this.movementStrategy = strategy;  
    }  
}