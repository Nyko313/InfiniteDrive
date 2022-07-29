package com.infinitedrive.helpers;

import com.badlogic.gdx.physics.box2d.*;

public class BodyCreator {

    public static Body createBoxBody(World world, float x, float y, float width, float height, Object userData, boolean dynamic, boolean sensor){
        Body body;

        // Set up the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);

        bodyDef.type = dynamic ? BodyDef.BodyType.DynamicBody: BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        // Create the fixture and the box shape
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width* 0.50f, height * 0.50f);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        // Active body physic
        body.setSleepingAllowed(false);
        // Dispose unused shape
        shape.dispose();

        //Set as sensor
        if(sensor) {
            for (Fixture fix : body.getFixtureList()) {
                fix.setSensor(true);
            }
        }

        // Set a name for identification
        body.setUserData(userData);
        return body;
    }
}
