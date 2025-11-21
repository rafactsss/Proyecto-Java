package com.proyecto.flappy.game;

import com.badlogic.gdx.math.Rectangle;
import com.proyecto.flappy.audio.SoundManager;

/**
 * Sistema de colisiones entre el Bird y los PipePair.
 * Aquí afinamos las hitbox para que el margen de error sea menor.
 */
public class CollisionSystem {

    // Rectángulos temporales para no crear objetos en cada frame
    private final Rectangle tmpBird       = new Rectangle();
    private final Rectangle tmpPipeTop    = new Rectangle();
    private final Rectangle tmpPipeBottom = new Rectangle();

    // FACTORES DE REDUCCIÓN (ajusta estos si quieres más o menos tolerancia)
    // 0.3f significa que recortamos un 30% de cada lado (cohete MUCHO más permisivo)
    private static final float BIRD_SHRINK_FACTOR_X = 0.30f;
    private static final float BIRD_SHRINK_FACTOR_Y = 0.30f;

    // Para los tubos: recorte lateral suave
    private static final float PIPE_SHRINK_FACTOR_X = 0.10f;

    /**
     * Revisa colisiones entre el bird y todos los pipes.
     *
     * @param bird  la nave (Bird)
     * @param pipes colección iterable de PipePair (viene de PipeSystem.getPipes())
     * @return true si hay colisión, false en caso contrario
     */
    public boolean check(Bird bird, Iterable<PipePair> pipes) {

        // 1) Tomamos la hitbox original del bird (Rectangle)
        Rectangle b = bird.getHitbox();

        // 2) Calculamos cuánto encogerla proporcionalmente a su tamaño
        float shrinkX = b.width  * BIRD_SHRINK_FACTOR_X;
        float shrinkY = b.height * BIRD_SHRINK_FACTOR_Y;

        // 3) Creamos un rectángulo más pequeño centrado dentro de la hitbox original
        tmpBird.set(
                b.x + shrinkX,
                b.y + shrinkY,
                b.width  - 2f * shrinkX,
                b.height - 2f * shrinkY
        );

        if (tmpBird.width <= 0 || tmpBird.height <= 0) {
            // Si por algún motivo quedara degenerado, no hacemos nada
            return false;
        }

        // 4) Recorremos todos los pipes
        for (PipePair pipe : pipes) {

            // --- TOP PIPE ---
            Rectangle top = pipe.getTopBounds();
            float topShrinkX = top.width * PIPE_SHRINK_FACTOR_X;

            tmpPipeTop.set(
                    top.x + topShrinkX,
                    top.y,
                    top.width  - 2f * topShrinkX,
                    top.height
            );

            // --- BOTTOM PIPE ---
            Rectangle bottom = pipe.getBottomBounds();
            float bottomShrinkX = bottom.width * PIPE_SHRINK_FACTOR_X;

            tmpPipeBottom.set(
                    bottom.x + bottomShrinkX,
                    bottom.y,
                    bottom.width  - 2f * bottomShrinkX,
                    bottom.height
            );

            // Si el cohete reducido se solapa con alguno de los tubos reducidos:
            if (tmpPipeTop.width > 0 && tmpPipeTop.overlaps(tmpBird)) {
                bird.onCollision(null);   // no usamos el parámetro en Bird
                SoundManager.playHit();
                return true;
            }

            if (tmpPipeBottom.width > 0 && tmpPipeBottom.overlaps(tmpBird)) {
                bird.onCollision(null);
                SoundManager.playHit();
                return true;
            }
        }

        return false;
    }
}
