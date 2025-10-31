package com.proyecto.flappy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyecto.flappy.FlappyGame;
import com.proyecto.flappy.game.Bird;
import com.proyecto.flappy.game.CollisionSystem;
import com.proyecto.flappy.game.PipeSystem;
import com.proyecto.flappy.game.ScoreSystem;

public class FlappyScreen extends ScreenAdapter {

    public enum State { READY, RUNNING, GAME_OVER }

    private final FlappyGame game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Bird bird;
    private PipeSystem pipes;
    private CollisionSystem collisions;
    private ScoreSystem score;

    private State state = State.READY;

    public FlappyScreen(FlappyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        reset();
    }

    private void reset() {
        bird = new Bird();
        pipes = new PipeSystem();
        collisions = new CollisionSystem();
        score = new ScoreSystem();
        state = State.READY;
    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClearColor(0.4f, 0.7f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        pipes.render(batch);
        bird.render(batch);
        drawHUD();
        batch.end();
    }

    private void update(float dt) {
        switch (state) {
            case READY:
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) state = State.RUNNING;
                break;
            case RUNNING:
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) bird.jump();
                bird.update(dt);
                pipes.update(dt);

                if (collisions.check(bird, pipes.getPipes())) {
                    state = State.GAME_OVER;
                } else {
                    score.update(bird, pipes.getPipes());
                }
                break;
            case GAME_OVER:
                if (Gdx.input.isKeyJustPressed(Input.Keys.R)) reset();
                break;
        }
    }

    private void drawHUD() {
        font.draw(batch, "Score: " + score.getScore(), 16, 460);
        if (state == State.READY)
            font.draw(batch, "SPACE para comenzar", 280, 240);
        else if (state == State.GAME_OVER)
            font.draw(batch, "GAME OVER - R para reiniciar", 230, 240);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        bird.dispose();
    }
}

