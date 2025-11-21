package com.proyecto.flappy.game.strategy;

import com.proyecto.flappy.game.Bird;

/**
 * Movimiento normal del pájaro: gravedad estándar.
 */
public class NormalMovement implements MovementStrategy {

    @Override
    public void update(Bird bird, float dt) {
        // Accedemos a la velocidad a través del getter
        bird.getVelocity().y += -12f * dt * 60f;
    }
}
