package com.proyecto.flappy.game;

public class ScoreSystem {
    private int score = 0;
    public void update(Bird bird, Iterable<PipePair> pipes) {
        float bx = bird.getHitbox().x;
        for (PipePair p : pipes) {
            if (!p.scored && p.getCenterX() < bx) { p.scored = true; score++; }
        }
    }
    public int getScore() { return score; }
    public void reset() { score = 0; }
}
