package com.proyecto.flappy.game;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {

    // ✔ para entidades con un solo hitbox (Bird)
    Rectangle getHitbox();

    // ✔ entidades con múltiples hitboxes (PipePair)
    default Iterable<Rectangle> getHitboxes() {
        return java.util.Collections.singleton(getHitbox());
    }

    void onCollision(Entity other);
}
