package com.proyecto.flappy.game.strategy;  
  
import com.proyecto.flappy.game.Bird;  
  
public class SpaceMovement implements MovementStrategy {  
    private final float deceleration = 200f;  
      
    @Override  
    public void update(Bird bird, float dt) {  
        // Sin gravedad, pero con desaceleración gradual  
        if (bird.velocity.y > 0) {  
            bird.velocity.y -= deceleration * dt;  
            if (bird.velocity.y < 0) bird.velocity.y = 0;  
        } else if (bird.velocity.y < 0) {  
            bird.velocity.y += deceleration * dt;  
            if (bird.velocity.y > 0) bird.velocity.y = 0;  
        }  
          
        bird.position.y += bird.velocity.y * dt;  
    }  
}