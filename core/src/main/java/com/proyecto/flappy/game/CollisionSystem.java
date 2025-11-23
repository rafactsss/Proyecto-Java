package com.proyecto.flappy.game;

import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.audio.SoundManager;

public class CollisionSystem {

    private final Rectangle tmpBird = new Rectangle();
    private final Rectangle tmpPipe = new Rectangle();

    private static final float BIRD_SHRINK_X = 0.30f;
    private static final float BIRD_SHRINK_Y = 0.30f;
    private static final float PIPE_SHRINK_X = 0.10f;

    public boolean check(Bird bird, Iterable<PipePair> pipes) {

        Rectangle b = bird.getHitbox();

        float shrinkX = b.width * BIRD_SHRINK_X;
        float shrinkY = b.height * BIRD_SHRINK_Y;

        tmpBird.set(
                b.x + shrinkX,
                b.y + shrinkY,
                b.width - 2f * shrinkX,
                b.height - 2f * shrinkY
        );

        if (tmpBird.width <= 0 || tmpBird.height <= 0)
            return false;

        for (PipePair pipe : pipes) {

            for (Rectangle pipeHb : pipe.getHitboxes()) {

                float psx = pipeHb.width * PIPE_SHRINK_X;

                tmpPipe.set(
                        pipeHb.x + psx,
                        pipeHb.y,
                        pipeHb.width - 2f * psx,
                        pipeHb.height
                );

                if (tmpPipe.width > 0 && tmpPipe.overlaps(tmpBird)) {
                    bird.onCollision(null);   // ✔ CORRECTO
                    SoundManager.playHit();
                    return true;
                }
            }
        }

        return false;
    }
}
