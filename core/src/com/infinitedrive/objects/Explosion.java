package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.infinitedrive.Const;
import com.infinitedrive.Gameobject;

public class Explosion extends Gameobject {
    private ParticleEffect explosionEffect;
    private float x;
    private float y;
    public Explosion(float x , float y){
        initialize(this);
        this.x = x;
        this.y = y;

        explosionEffect = new ParticleEffect();
        explosionEffect.load(Gdx.files.internal("Effects/explosion.party"), Gdx.files.internal("Effects/"));
        explosionEffect.getEmitters().first().setPosition(x,y);
        explosionEffect.start();
    }

    public void update() {
        explosionEffect.update(Gdx.graphics.getDeltaTime());
    }

    public void render(){

        explosionEffect.draw(batch);
    }
}
