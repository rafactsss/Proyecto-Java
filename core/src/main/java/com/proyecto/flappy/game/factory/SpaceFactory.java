package com.proyecto.flappy.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.proyecto.flappy.game.Bird;

public class SpaceFactory implements GameFactory {

    @Override
    public Bird createBird() {
        // Usa el constructor Bird(String spritePath) que ya tiene tu Bird final
        return new Bird("bird.png"); // tu sprite de cohete espacial
    }

    @Override
    public Texture createBackground() {
        return new Texture("background.png"); // tu fondo espacial
    }
}
