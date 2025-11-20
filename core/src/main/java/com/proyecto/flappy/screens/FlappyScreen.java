package com.proyecto.flappy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.proyecto.flappy.FlappyGame;
import com.proyecto.flappy.config.GameConfig;
import com.proyecto.flappy.game.Bird;
import com.proyecto.flappy.game.CollisionSystem;
import com.proyecto.flappy.game.PipeSystem;
import com.proyecto.flappy.game.ScoreSystem;
import com.proyecto.flappy.game.PipePair;
import com.proyecto.flappy.audio.SoundManager;
import com.proyecto.flappy.game.factory.GameFactory;
import com.proyecto.flappy.game.factory.SpaceFactory;


public class FlappyScreen extends ScreenAdapter {

    public enum State { READY, RUNNING, GAME_OVER }

    private final FlappyGame game;

    private SpriteBatch batch;
    
    private GameFactory factory;
    
    private BitmapFont font;
    private Texture background;

    private Bird bird;
    private PipeSystem pipes;
    private CollisionSystem collisions;
    private ScoreSystem score;

    private State state = State.READY;

    // gracia al arrancar RUNNING
    private float runTime;
    private static final float START_GRACE = 1.0f;

    // Para detectar “punto” sin tocar tu ScoreSystem
    private int prevScore = 0;
    private boolean playedGameOverSfx = false;

    public FlappyScreen(FlappyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Elegimos qué "tema" usar: FACTORÍA ESPACIAL
        factory = new SpaceFactory();

        // El fondo ahora lo crea la Abstract Factory
        background = factory.createBackground();

        SoundManager.load();  // cargar sonidos
        reset();              // crea bird, pipes, etc.
    }


    private void reset() {
        // AHORA el Bird viene de la Abstract Factory
        bird = factory.createBird();

        pipes = new PipeSystem();
        collisions = new CollisionSystem();
        score = new ScoreSystem();

        runTime = 0f;
        state = State.READY;

        prevScore = 0;
        playedGameOverSfx = false;
    }


    @Override
    public void render(float dt) {
        update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        pipes.render(batch);
        bird.render(batch);
        drawHUD();
        batch.end();
    }

    private void update(float dt) {
        switch (state) {
            case READY:
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    state = State.RUNNING;
                    runTime = 0f;
                    SoundManager.playFlap(); // feedback al iniciar
                }
                break;

            case RUNNING:
                runTime += dt;

                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    bird.jump();
                    SoundManager.playFlap(); // salto
                }

                bird.update(dt);
                pipes.update(dt);

                // Colisión (después del "grace time")
                if (runTime > START_GRACE && collisions.check(bird, pipes.getPipes())) {
                    state = State.GAME_OVER;
                    if (!playedGameOverSfx) {
                        SoundManager.playHit();
                        SoundManager.playDie();
                        playedGameOverSfx = true;
                    }
                } else {
                    // Detectar si subió el score
                    score.update(bird, pipes.getPipes());
                    if (score.getScore() > prevScore) {
                        SoundManager.playPoint();
                        prevScore = score.getScore();
                    }
                }
                break;

            case GAME_OVER:
                if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                    reset();
                }
                break;
        }
    }

    private void drawHUD() {
        font.draw(batch, "Score: " + score.getScore(), 16, 460);
        if (state == State.READY) {
            font.draw(batch, "SPACE para comenzar", 350, 240);
        } else if (state == State.GAME_OVER) {
            font.draw(batch, "GAME OVER - R para reiniciar", 300, 240);
        }
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null)  font.dispose();
        if (bird != null)  bird.dispose();
        PipePair.disposeTextures();
        if (background != null) background.dispose();

        SoundManager.dispose(); // <-- liberar sonidos
    }
}
