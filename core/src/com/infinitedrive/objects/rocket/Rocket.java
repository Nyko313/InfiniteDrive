package com.infinitedrive.objects.rocket;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.infinitedrive.helpers.BodyCreator;
import com.infinitedrive.Gameobject;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.objects.Particle;

public class Rocket extends Gameobject {

    private Texture texture;
    private float speed = 20;
    private float width = 19;
    private float height = 30;

    public Rocket(float x, float y){
        initialize(this);

        texture = new Texture("sprites/rocket.png");

        body = BodyCreator.createBoxBody(world, x, y, width, height, this, true, true);
    }

    public void explode(){
        if(!isFlaggedForDelete){
            new Particle(body.getPosition().x, body.getPosition().y + 50, false, "explosion.party");
            destroy();
        }

    }

    @Override
    public void update(){
        body.setTransform(new Vector2(body.getPosition().x, body.getPosition().y + speed), 0);

        if(body.getPosition().y > InfiniteDrive.INSTANCE.getScreenHeight() + 10){
            destroy();
        }
    }

    @Override
    public void render(){
        if(!isFlaggedForDelete) batch.draw(texture, body.getPosition().x - width / 2, body.getPosition().y - height / 2, width, height);
    }
}
