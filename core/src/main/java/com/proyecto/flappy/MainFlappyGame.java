package com.proyecto.flappy;

import com.badlogic.gdx.Game;
import com.proyecto.flappy.screens.FlappyScreen;

public class MainFlappyGame extends Game {
    @Override
    public void create() {
        setScreen(new FlappyScreen(this));
    }
}
