package com.proyecto.flappy.game.strategy;  
  
import com.proyecto.flappy.game.Bird;  
  
public class AcceleratedMovement implements MovementStrategy {  
    private float gravity = -900f;  
    private float accelerationFactor = 1.0f;  
    private final float maxGravity = -1500f;  
      
    @Override  
    public void update(Bird bird, float dt) {  
        // Gravedad que aumenta con el tiempo  
        accelerationFactor += dt * 0.1f;  
        float currentGravity = Math.max(gravity * accelerationFactor, maxGravity);  
          
        bird.velocity.y += currentGravity * dt;  
        bird.position.y += bird.velocity.y * dt;  
    }  
}