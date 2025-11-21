package com.proyecto.flappy.game.strategy;

import com.proyecto.flappy.game.Bird;

/**
 * Strategy para controlar cómo se mueve el Bird.
 */
public interface MovementStrategy {
    void update(Bird bird, float dt);
}
