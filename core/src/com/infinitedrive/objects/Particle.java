package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.infinitedrive.helpers.Const;
import com.infinitedrive.Gameobject;

public class Particle extends Gameobject {
    private ParticleEffect particle;
    private float x;
    private float y;
    private boolean attached;

    public Particle(float x , float y, boolean attached, String name){
        initialize(this);

        this.attached = attached;
        this.x = x;
        this.y = y;

        particle = new ParticleEffect();

        // Load and start the particle effect
        particle.load(Gdx.files.local("effects/" + name), Gdx.files.local("effects/"));
        particle.setPosition(x,y);
        particle.start();
    }

    @Override
    public void update() {
        // update the effect
        particle.update(Gdx.graphics.getDeltaTime());

        // move the effect
        if(!attached){
            y -= Player.INSTANCE.getCurrentVelocity() * Const.EXPLOSION_VELOCITY_MULTIPLIER * Gdx.graphics.getDeltaTime();
            particle.setPosition(x,y);
        }
        if(particle.isComplete()){
            destroy();
        }

    }

    @Override
    public void render(){
        particle.draw(batch);
    }

    @Override
    public void dispose() {
        particle.dispose();

    }
}
