package com.proyecto.flappy.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionSystem {

    public boolean check(Bird bird, Iterable<PipePair> pipes) {
        Rectangle b = bird.getHitbox();
        for (PipePair p : pipes) {
            if (Intersector.overlaps(b, p.getTopBounds()) ||
                Intersector.overlaps(b, p.getBottomBounds())) {
                bird.onCollision(null);
                return true;
            }
        }
        return false;
    }
}
