package com.proyecto.flappy.game.strategy;  
  
import com.proyecto.flappy.game.PipePair;  
  
public class HorizontalPipeMovement implements PipeMovementStrategy {  
      
    @Override  
    public void update(PipePair pipe, float dt) {  
        // Movimiento horizontal estándar (izquierda)  
        pipe.setX(pipe.getX() - pipe.getSpeed() * dt);  
    }  
}