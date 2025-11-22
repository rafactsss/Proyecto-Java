package com.proyecto.flappy.game.strategy;  
  
import com.proyecto.flappy.game.Bird;  
  
public class FloatingMovement implements MovementStrategy {  
    private final float gravity = -600f;  
    private final float buoyancy = 100f;  
      
    @Override  
    public void update(Bird bird, float dt) {  
        // Gravedad reducida + flotación  
        bird.velocity.y += (gravity + buoyancy) * dt;  
        bird.position.y += bird.velocity.y * dt;  
    }  
}