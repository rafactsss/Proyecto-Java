package com.proyecto.flappy.game.strategy;  
  
import com.proyecto.flappy.game.Bird;  
  
public class NormalMovement implements MovementStrategy {  
    private final float gravity = -900f;  
      
    @Override  
    public void update(Bird bird, float dt) {  
        // Aplicar gravedad  
        bird.velocity.y += gravity * dt;  
        bird.position.y += bird.velocity.y * dt;  
    }  
}