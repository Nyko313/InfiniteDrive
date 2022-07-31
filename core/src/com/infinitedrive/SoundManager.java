package com.infinitedrive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.infinitedrive.objects.Player;

public class SoundManager {
    public static SoundManager INSTANCE;

    private AppPreferences appPreferences;

    private long id;

    private Sound engine;
    private Sound explosion;
    private Sound rocketCollected;
    private Sound lose;
    private Sound collision;
    private Sound shoot;
    private Sound song;


    public SoundManager(AppPreferences appPreferences){
        INSTANCE = this;
        this.appPreferences = appPreferences;

        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        rocketCollected = Gdx.audio.newSound(Gdx.files.internal("sounds/rocketCollected.wav"));
        lose = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.wav"));
        collision = Gdx.audio.newSound(Gdx.files.internal("sounds/collision.wav"));
        shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
        engine= Gdx.audio.newSound(Gdx.files.internal("sounds/engine.wav"));
        song= Gdx.audio.newSound(Gdx.files.internal("sounds/megalovania.wav"));

        id = engine.play();
        engine.setLooping(id, true);
    }

    // Play sounds and songs
    public void UpdatePlayerSound(){
        engine.setVolume(id, appPreferences.getSoundVolume() * 0.2f);
        engine.setPitch(id, Player.INSTANCE.getCurrentVelocity() * 0.016f);
    }

    public void PlayExplosion(){
        explosion.play(appPreferences.getSoundVolume() * 2);
    }

    public void PlayRocketCollected(){
        rocketCollected.play(appPreferences.getSoundVolume());
    }

    public void PlayLose(){
        lose.play(appPreferences.getSoundVolume());
    }

    public void PlayCollision(){
        collision.play(appPreferences.getSoundVolume());
    }

    public void PlayShoot(){
        shoot.play(appPreferences.getSoundVolume() * 0.5f);
    }

    public void PlaySong(){
        song.play(0.4f * appPreferences.getMusicVolume());
    }

    public void StopSong(){
        song.stop();
    }

    public void dispose(){
        song.stop();
        explosion.stop();
        rocketCollected.stop();
        lose.stop();
        collision.stop();
        shoot.stop();
        engine.stop();

        song.dispose();
        explosion.dispose();
        rocketCollected.dispose();
        lose.dispose();
        collision.dispose();
        shoot.dispose();
        engine.dispose();
    }
}
