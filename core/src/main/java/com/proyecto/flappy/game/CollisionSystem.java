package com.proyecto.flappy.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionSystem {

    public boolean check(Bird bird, Iterable<PipePair> pipes) {
        final Rectangle b = bird.getHitbox();

        for (PipePair p : pipes) {
            // ---- filtro rápido por eje X: si no hay solape horizontal, seguimos
            if (b.x + b.width <= p.getX())      continue; // el pájaro está a la izquierda del tubo
            if (b.x >= p.getRight())            continue; // el pájaro ya pasó ese tubo

            // ahora sí probamos rectángulos reales
            if (Intersector.overlaps(b, p.getTopBounds()) ||
                Intersector.overlaps(b, p.getBottomBounds())) {
                bird.onCollision(null);
                return true;
            }
        }
        return false;
    }
}
