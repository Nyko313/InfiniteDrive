package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.infinitedrive.Gameobject;
import com.infinitedrive.InfiniteDrive;
import org.jetbrains.annotations.NotNull;

public class Rocket extends Gameobject {

    private Texture texture;
    private float speed = 20;
    private float width = 15;
    private float height = 50;

    public Rocket(float x, float y){
        initialize(this);

        texture = new Texture("Missile.png");


        // Set up the body definition
        BodyDef bDef = new BodyDef();
        bDef.position.set(x - width/2, y);
        bDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bDef);

        // Create the fixture and the box shape
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, width);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);

        // Active body physic
        body.setSleepingAllowed(false);
        // Dispose unused shape
        shape.dispose();
        // Set a name for identification
        body.setUserData("Rocket");
    }

    public void explode(){
        new Explosion(body.getPosition().x, body.getPosition().y);
        destroy();
    }

    public void destroy(){
        super.destroy();
        Player.INSTANCE.setRocket(null);
    }

    public void update(){
        body.setTransform(new Vector2(body.getPosition().x, body.getPosition().y + speed), 0);

        if(body.getPosition().y > InfiniteDrive.INSTANCE.getScreenHeight() + 10){
            destroy();
        }
    }

    public void render(){
        if(!isFlaggedForDelete) batch.draw(texture, body.getPosition().x, body.getPosition().y, width, height);
    }
}
