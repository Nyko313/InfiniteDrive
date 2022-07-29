package com.infinitedrive.objects.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.infinitedrive.helpers.BodyCreator;
import com.infinitedrive.helpers.Const;
import com.infinitedrive.Gameobject;
import com.infinitedrive.objects.Player;

public class CollectableRocket extends Gameobject {

    private Texture texture;
    private float width = 21;
    private float height = 35;

    public CollectableRocket(float x, float y){
        renderPriority = 2;
        initialize(this);

        texture = new Texture("sprites/rocket.png");

        body = BodyCreator.createBoxBody(world, x, y, width, height, this, true, true);
    }

    @Override
    public void update() {
        body.setTransform(new Vector2(body.getPosition().x, body.getPosition().y - Player.INSTANCE.getCurrentVelocity() * Const.VEHICLES_VELOCITY_MULTIPLIER * Gdx.graphics.getDeltaTime()), 0);

        if(body.getPosition().y <= -height){
            destroy();
        }
    }

    @Override
    public void render() {
        batch.draw(texture, body.getPosition().x, body.getPosition().y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
