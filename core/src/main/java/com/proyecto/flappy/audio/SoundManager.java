package com.proyecto.flappy.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/** Gestor simple de SFX. Todos los play() existen aunque falte un archivo. */
public final class SoundManager {

    private static boolean loaded = false;
    private static Sound sFlap, sPoint, sHit, sDie;

    private SoundManager() {}

    private static Sound load(String file) {
        FileHandle fh = Gdx.files.internal(file);
        if (!fh.exists()) {
            Gdx.app.log("SFX", "NO existe en assets/: " + file);
            return null;
        }
        return Gdx.audio.newSound(fh);
    }

    /** Llama una vez (o se auto-llama desde playXxx()). Ajusta extensiones si usas .ogg/.mp3 */
    public static void load() {
        if (loaded) return;
        sFlap  = load("flap.mp3");   // cambia a flap.ogg/.mp3 si corresponde
        sPoint = load("point.mp3");
        sHit   = load("hit.mp3");    // si no existe, playHit() hará fallback
        sDie   = load("die.mp3");
        loaded = true;
    }

    private static void play(Sound s, float vol) {
        if (s != null) {
            long id = s.play();
            s.setVolume(id, vol);
        }
    }

    public static void playFlap()  { if (!loaded) load(); play(sFlap,  1f); }
    public static void playPoint() { if (!loaded) load(); play(sPoint, 1f); }

    /** Siempre existe la función: si no hay hit.wav, cae a die.wav. */
    public static void playHit()   { 
        if (!loaded) load(); 
        if (sHit != null) play(sHit, 1f); else play(sDie, 0.8f);
    }

    public static void playDie()   { if (!loaded) load(); play(sDie,   1f); }

    public static void dispose() {
        if (sFlap  != null) { sFlap.dispose();  sFlap  = null; }
        if (sPoint != null) { sPoint.dispose(); sPoint = null; }
        if (sHit   != null) { sHit.dispose();   sHit   = null; }
        if (sDie   != null) { sDie.dispose();   sDie   = null; }
        loaded = false;
    }
}
