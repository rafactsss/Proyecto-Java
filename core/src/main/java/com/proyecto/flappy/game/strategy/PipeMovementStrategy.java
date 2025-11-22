package com.proyecto.flappy.game.strategy;  
  
import com.proyecto.flappy.game.PipePair;  
  
public interface PipeMovementStrategy {  
    void update(PipePair pipe, float dt);  
}