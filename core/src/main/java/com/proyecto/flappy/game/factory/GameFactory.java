package com.proyecto.flappy.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.proyecto.flappy.game.Bird;

public interface GameFactory {

    Bird createBird();
    Texture createBackground();
}
