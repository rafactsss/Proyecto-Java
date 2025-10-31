package com.proyecto.flappy.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected final Vector2 position = new Vector2();
    protected final Rectangle bounds = new Rectangle();
    protected boolean active = true;

    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);

    public Rectangle getBounds() { 
        bounds.setPosition(position.x, position.y);
        return bounds; 
    }
    public boolean isActive() { return active; }
}
