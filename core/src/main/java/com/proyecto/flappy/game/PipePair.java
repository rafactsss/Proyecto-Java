package com.proyecto.flappy.game;  
  
import com.badlogic.gdx.graphics.Texture;  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  
import com.badlogic.gdx.math.MathUtils;  
import com.badlogic.gdx.math.Rectangle;  
import com.proyecto.flappy.config.GameConfig;  
  
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
        x -= speed * dt;  
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
  
    public Rectangle getTopBounds()    { return top; }  
    public Rectangle getBottomBounds() { return bottom; }  
    public float getX()                { return x; }  
    public float getRight()            { return x + width; }  
    public boolean isOffScreen()       { return getRight() < 0; }  
    public float getCenterX()          { return x + width * 0.5f; }  
  
    public void setSpeed(float speed)  { this.speed = speed; }  
    public void setGap(float gap)      { this.gapSize = gap; recomputeRects(); }  
}
