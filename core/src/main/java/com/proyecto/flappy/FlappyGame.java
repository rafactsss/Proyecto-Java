package com.proyecto.flappy;

import com.badlogic.gdx.Game;
import com.proyecto.flappy.screens.FlappyScreen;
import com.proyecto.flappy.game.PipePair;

public class FlappyGame extends Game {
    @Override public void create() { setScreen(new FlappyScreen(this)); }
    @Override public void dispose() {
        if (getScreen() != null) getScreen().dispose();
        PipePair.disposeTextures();
    }
}

