package com.proyecto.flappy.game;  
  
import com.badlogic.gdx.graphics.g2d.SpriteBatch;  
import com.badlogic.gdx.math.MathUtils;  
import com.proyecto.flappy.config.GameConfig;  
  
import java.util.ArrayDeque;  
import java.util.Deque;  
  
public class PipeSystem {  
  
    private final Deque<PipePair> pipes = new ArrayDeque<>();  
    private float spawnTimer = 0f, diffTimer = 0f;  
  
    private float currentSpeed = GameConfig.getInstance().PIPE_BASE_SPEED;  
    private float currentGap   = GameConfig.getInstance().PIPE_GAP_START;  
  
    // --- Márgenes de seguridad para un arranque sin "muerte instantánea" ---  
    /** cuán lejos del borde derecho nace el 1er tubo (en píxeles) */  
    private static final float SAFE_START_OFFSET = 280f;  
    /** separación mínima entre tubos, por si el cálculo dinámico da demasiado poco */  
    private static final float MIN_SPACING       = 240f;  
  
    public PipeSystem() { reset(); }  
  
    public void reset() {  
        GameConfig config = GameConfig.getInstance();  
        pipes.clear();  
        spawnTimer = diffTimer = 0f;  
        currentSpeed = config.PIPE_BASE_SPEED;  
        currentGap   = config.PIPE_GAP_START;  
  
        float x = config.WORLD_WIDTH + 160f;  
        for (int i = 0; i < 3; i++) {   
            pipes.add(createPair(x));   
            x += spacingPixels();   
        }  
    }  
  
    public void update(float dt) {  
        GameConfig config = GameConfig.getInstance();  
          
        for (PipePair p : pipes) p.update(dt);  
  
        spawnTimer += dt;  
        if (spawnTimer >= config.PIPE_SPAWN_EVERY) {  
            spawnTimer -= config.PIPE_SPAWN_EVERY;  
            pipes.add(createPair(nextSpawnX()));  
        }  
  
        // limpiar los que salieron completamente de pantalla  
        while (!pipes.isEmpty() && pipes.peekFirst().isOffScreen()) {  
            pipes.removeFirst();  
        }  
  
        // dificultad (más rápido / menos gap)  
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
        return new PipePair(startX, cy, currentGap, currentSpeed);  
    }  
      
    public void dispose() {  
        pipes.clear();  
        PipePair.disposeTextures();  
    }  
}
