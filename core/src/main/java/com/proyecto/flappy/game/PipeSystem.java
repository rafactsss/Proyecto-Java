package com.proyecto.flappy.game;  
  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  
import com.badlogic.gdx.math.MathUtils;  
import com.proyecto.flappy.config.GameConfig;  
import com.proyecto.flappy.game.strategy.HorizontalPipeMovement;  
import com.proyecto.flappy.game.strategy.VerticalPipeMovement;  
  
import java.util.ArrayDeque;  
import java.util.Deque;  
  
public class PipeSystem {  
  
    private final Deque<PipePair> pipes = new ArrayDeque<>();  
    private float spawnTimer = 0f, diffTimer = 0f;  
  
    private float currentSpeed = GameConfig.getInstance().PIPE_BASE_SPEED;  
    private float currentGap   = GameConfig.getInstance().PIPE_GAP_START;  
      
    // Flag para saber si ya cambiamos a movimiento vertical  
    private boolean verticalModeActivated = false;  
  
    private static final float SAFE_START_OFFSET = 280f;  
    private static final float MIN_SPACING       = 240f;  
  
    public PipeSystem() { reset(); }  
  
    public void reset() {  
        GameConfig config = GameConfig.getInstance();  
        pipes.clear();  
        spawnTimer = diffTimer = 0f;  
        currentSpeed = config.PIPE_BASE_SPEED;  
        currentGap   = config.PIPE_GAP_START;  
        verticalModeActivated = false;  
  
        float x = config.WORLD_WIDTH + 160f;  
        for (int i = 0; i < 3; i++) {   
            pipes.add(createPair(x));   
            x += spacingPixels();   
        }  
    }  
  
    public void update(float dt, int currentScore) {  
        GameConfig config = GameConfig.getInstance();  
          
        // Activar movimiento vertical después del nivel 50  
        if (currentScore >= 30 && !verticalModeActivated) {  
            activateVerticalMovement();  
            verticalModeActivated = true;  
        }  
          
        for (PipePair p : pipes) p.update(dt);  
  
        spawnTimer += dt;  
        if (spawnTimer >= config.PIPE_SPAWN_EVERY) {  
            spawnTimer -= config.PIPE_SPAWN_EVERY;  
            pipes.add(createPair(nextSpawnX()));  
        }  
  
        while (!pipes.isEmpty() && pipes.peekFirst().isOffScreen()) {  
            pipes.removeFirst();  
        }  
  
        diffTimer += dt;  
        if (diffTimer >= config.DIFF_EVERY_SEC) {  
            diffTimer -= config.DIFF_EVERY_SEC;  
  
            currentSpeed = Math.min(currentSpeed + config.SPEED_STEP, config.SPEED_MAX);  
            currentGap   = Math.max(currentGap   + config.GAP_STEP,  config.PIPE_GAP_MIN);  
  
            for (PipePair p : pipes) {  
                p.setSpeed(currentSpeed);  
                p.setGap(currentGap);  
            }  
        }  
    }  
      
    private void activateVerticalMovement() {  
        // Cambiar todos los pilares existentes a movimiento vertical  
        VerticalPipeMovement verticalStrategy = new VerticalPipeMovement();  
        for (PipePair p : pipes) {  
            p.setMovementStrategy(verticalStrategy);  
        }  
    }  
  
    public void render(SpriteBatch batch) {   
        for (PipePair p : pipes) p.render(batch);   
    }  
  
    public Iterable<PipePair> getPipes() { return pipes; }  
  
    private float nextSpawnX() {  
        GameConfig config = GameConfig.getInstance();  
        return pipes.isEmpty()  
                ? config.WORLD_WIDTH + SAFE_START_OFFSET  
                : pipes.peekLast().getX() + spacingPixels();  
    }  
      
    private float spacingPixels() {   
        GameConfig config = GameConfig.getInstance();  
        return currentSpeed * config.PIPE_SPAWN_EVERY * 1.35f;   
    }  
  
    private PipePair createPair(float startX) {  
        GameConfig config = GameConfig.getInstance();  
        float minC = config.GAP_MARGIN_BOTTOM + currentGap * 0.5f;  
        float maxC = config.WORLD_HEIGHT - (config.GAP_MARGIN_TOP + currentGap * 0.5f);  
        float cy = MathUtils.random(minC, maxC);  
          
        PipePair pair = new PipePair(startX, cy, currentGap, currentSpeed);  
          
        // Si ya estamos en modo vertical, aplicar la estrategia vertical  
        if (verticalModeActivated) {  
            pair.setMovementStrategy(new VerticalPipeMovement());  
        }  
          
        return pair;  
    }  
      
    public void dispose() {  
        pipes.clear();  
        PipePair.disposeTextures();  
    }  
}
