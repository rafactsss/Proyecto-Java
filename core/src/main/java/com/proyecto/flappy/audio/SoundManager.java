package com.proyecto.flappy.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/** Gestor simple de SFX implementado como Singleton pero con API estática compatible. */
public final class SoundManager {

    // --- SINGLETON REAL ---
    private static final SoundManager INSTANCE = new SoundManager();

    /** Acceso global al Singleton (por si quieres usarlo en contexto). */
    public static SoundManager getInstance() {
        return INSTANCE;
    }

    // --- ESTADO DE LA INSTANCIA ---
    private boolean loaded = false;
    private Sound sFlap, sPoint, sHit, sDie;

    /** Constructor privado (regla del Singleton). */
    private SoundManager() { }

    // --------- MÉTODOS INTERNOS DE INSTANCIA ---------

    private Sound loadInternal(String file) {
        FileHandle fh = Gdx.files.internal(file);
        if (!fh.exists()) {
            if (Gdx.app != null) {
                Gdx.app.log("SFX", "NO existe en assets/: " + file);
            } else {
                System.out.println("[SFX] NO existe en assets/: " + file);
            }
            return null;
        }
        return Gdx.audio.newSound(fh);
    }

    /** Carga los sonidos solo una vez (instancia). */
    private void doLoad() {
        if (loaded) return;
        sFlap  = loadInternal("flap.mp3");   // cambia a .ogg/.wav si corresponde
        sPoint = loadInternal("point.mp3");
        sHit   = loadInternal("hit.mp3");
        sDie   = loadInternal("die.mp3");
        loaded = true;
    }

    private void playInternal(Sound s, float vol) {
        if (s != null) {
            long id = s.play();
            s.setVolume(id, vol);
        }
    }

    private void doPlayFlap() {
        if (!loaded) doLoad();
        playInternal(sFlap, 1f);
    }

    private void doPlayPoint() {
        if (!loaded) doLoad();
        playInternal(sPoint, 1f);
    }

    private void doPlayHit() {
        if (!loaded) doLoad();
        if (sHit != null) playInternal(sHit, 1f);
        else playInternal(sDie, 0.8f);
    }

    private void doPlayDie() {
        if (!loaded) doLoad();
        playInternal(sDie, 1f);
    }

    private void doDispose() {
        if (sFlap  != null) { sFlap.dispose();  sFlap  = null; }
        if (sPoint != null) { sPoint.dispose(); sPoint = null; }
        if (sHit   != null) { sHit.dispose();   sHit   = null; }
        if (sDie   != null) { sDie.dispose();   sDie   = null; }
        loaded = false;
    }

    // --------- API ESTÁTICA COMPATIBLE (LO QUE YA USAS) ---------

    /** Llama una vez (o se auto-llama desde playXxx()). */
    public static void load() {
        INSTANCE.doLoad();
    }

    private static void play(Sound s, float vol) {
        INSTANCE.playInternal(s, vol);
    }

    public static void playFlap()  {
        INSTANCE.doPlayFlap();
    }

    public static void playPoint() {
        INSTANCE.doPlayPoint();
    }

    /** Siempre existe la función: si no hay hit.wav, cae a die.wav. */
    public static void playHit() {
        INSTANCE.doPlayHit();
    }

    public static void playDie() {
        INSTANCE.doPlayDie();
    }

    public static void dispose() {
        INSTANCE.doDispose();
    }
}
