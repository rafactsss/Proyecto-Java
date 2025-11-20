package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected final Vector2 position = new Vector2();
    protected final Vector2 velocity = new Vector2();
    protected final Rectangle bounds = new Rectangle();
    protected boolean active = true;

    // TEMPLATE METHOD
    public final void update(float dt) {
        applyPhysics(dt);      // parte fija
        customUpdate(dt);      // parte variable
    }

    protected void applyPhysics(float dt) {
        position.mulAdd(velocity, dt);
        bounds.setPosition(position.x, position.y);
    }

    protected abstract void customUpdate(float dt);
    public abstract void render(SpriteBatch batch);

    public Rectangle getBounds() { return bounds; }
    public boolean isActive()    { return active; }
}
