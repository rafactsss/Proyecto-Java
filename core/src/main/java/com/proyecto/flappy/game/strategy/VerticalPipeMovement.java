package com.proyecto.flappy.game.strategy;  
  
import com.badlogic.gdx.math.MathUtils;  
import com.proyecto.flappy.config.GameConfig;  
import com.proyecto.flappy.game.PipePair;  
  
public class VerticalPipeMovement implements PipeMovementStrategy {  
      
    private float verticalSpeed = 30f; // Velocidad vertical suave (px/s)  
    private float direction = 1f; // 1 = abajo, -1 = arriba  
    private float minY;  
    private float maxY;  
      
    public VerticalPipeMovement() {  
        GameConfig config = GameConfig.getInstance();  
        // Calcular límites para el movimiento vertical  
        minY = config.GAP_MARGIN_BOTTOM + config.PIPE_GAP_MIN * 0.5f;  
        maxY = config.WORLD_HEIGHT - (config.GAP_MARGIN_TOP + config.PIPE_GAP_MIN * 0.5f);  
    }  
      
    @Override  
    public void update(PipePair pipe, float dt) {  
        // Movimiento horizontal (sigue moviéndose a la izquierda)  
        pipe.setX(pipe.getX() - pipe.getSpeed() * dt);  
          
        // Movimiento vertical suave  
        float newCenterY = pipe.getCenterY() + (verticalSpeed * direction * dt);  
          
        // Cambiar dirección si alcanza los límites  
        if (newCenterY >= maxY) {  
            newCenterY = maxY;  
            direction = -1f; // Cambiar a subir  
        } else if (newCenterY <= minY) {  
            newCenterY = minY;  
            direction = 1f; // Cambiar a bajar  
        }  
          
        pipe.setCenterY(newCenterY);  
    }  
}