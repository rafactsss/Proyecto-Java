package com.proyecto.flappy.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.proyecto.flappy.game.Bird;

public class SpaceFactory implements GameFactory {

    @Override
    public Bird createBird() {
        return new Bird("bird.png"); // ahora será usado
    }

    @Override
    public Texture createBackground() {
        return new Texture("background.png");
    }
}
