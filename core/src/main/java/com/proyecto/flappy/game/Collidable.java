package com.proyecto.flappy.game;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
    Rectangle getHitbox();
    void onCollision(Entity other);
}