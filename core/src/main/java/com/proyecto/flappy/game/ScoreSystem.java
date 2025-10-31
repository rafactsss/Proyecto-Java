package com.proyecto.flappy.game;

public class ScoreSystem {

    private int score = 0;

    /** 
     * @return true si acabas de sumar al menos 1 punto en este frame 
     */
    public boolean update(Bird bird, Iterable<PipePair> pipes) {
        float birdX = bird.getHitbox().x;
        boolean scoredNow = false;

        for (PipePair p : pipes) {
            // Marca “p.scored = true” cuando el centro del par pasa detrás del pájaro
            if (!p.scored && p.getCenterX() < birdX) {
                p.scored = true;
                score++;
                scoredNow = true;
            }
        }
        return scoredNow;
    }

    public int getScore() { return score; }

    public void reset() { score = 0; }
}
