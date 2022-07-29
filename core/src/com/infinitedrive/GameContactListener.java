package com.infinitedrive;

import com.badlogic.gdx.physics.box2d.*;
import com.infinitedrive.objects.*;
import com.infinitedrive.objects.npcvehicles.SpawnedNPCVehicle;
import com.infinitedrive.objects.rocket.CollectableRocket;
import com.infinitedrive.objects.rocket.Rocket;

public class GameContactListener implements ContactListener {

    private Fixture fixtureA;
    private Fixture fixtureB;
    private SpawnedNPCVehicle vehicleA;
    private SpawnedNPCVehicle vehicleB;
    public GameContactListener(){
    }

    @Override
    public void beginContact(Contact contact) {

        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();



        if(compareColliderClass(Rocket.class) != null && compareColliderClass(SpawnedNPCVehicle.class) != null){
            NPCMissileCollision();
        }else if(fixtureA.getBody().getUserData().getClass() == SpawnedNPCVehicle.class && fixtureB.getBody().getUserData().getClass() == SpawnedNPCVehicle.class) {
            NPCCollision();
        }else if(compareColliderClass(Player.class) != null && compareColliderClass(SpawnedNPCVehicle.class) != null){
            playerNPCCollision();
        }else if(compareColliderClass(Player.class) != null && compareColliderClass(CollectableRocket.class) != null){
            if(Player.INSTANCE.getRocketAmount() <3){
                playerPickableRocketCollison();
            }

        }

    }



    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    private void NPCMissileCollision() {
        vehicleA = (SpawnedNPCVehicle) compareColliderClass(SpawnedNPCVehicle.class).getBody().getUserData();

        Rocket rocket = (Rocket) compareColliderClass(Rocket.class).getBody().getUserData();
        rocket.explode();
        vehicleA.destroy();
    }
    private void playerNPCCollision(){
       vehicleA = (SpawnedNPCVehicle) compareColliderClass(SpawnedNPCVehicle.class).getBody().getUserData();

        if(vehicleA.getCurrentVelocity() > Player.INSTANCE.getCurrentVelocity()){
            vehicleA.setCurrentVelocity(0);
        }else{
            vehicleA.setCurrentVelocity(Player.INSTANCE.getCurrentVelocity());
        }
        Player.INSTANCE.crash();
    }

    private void NPCCollision(){
        // Get the colliding bodies
        vehicleA = (SpawnedNPCVehicle) compareColliderClass(SpawnedNPCVehicle.class).getBody().getUserData();
        if(vehicleA == fixtureA.getBody().getUserData()){
            vehicleB = (SpawnedNPCVehicle) fixtureB.getBody().getUserData();
        }else{
            vehicleA = (SpawnedNPCVehicle) fixtureB.getBody().getUserData();
        }

        // Change the bodies velocity
        if(vehicleA.getCurrentVelocity() >= vehicleB.getCurrentVelocity()) {
            vehicleA.setCurrentVelocity(vehicleB.getCurrentVelocity());
        }else{
            vehicleB.setCurrentVelocity(vehicleA.getCurrentVelocity());
        }

        /*      ALTERNATIVE COLLISION
        if(vehicleA.getCurrentVelocity() >= vehicleB.getCurrentVelocity()){
            vehicleA.setCurrentVelocity(vehicleB.getCurrentVelocity() - 2);
            vehicleB.setCurrentVelocity(vehicleB.getCurrentVelocity() + 7);
        }else{
            vehicleB.setCurrentVelocity(vehicleA.getCurrentVelocity() - 2);
            vehicleA.setCurrentVelocity(vehicleA.getCurrentVelocity() + 7);
        }
        */
    }

    private void playerPickableRocketCollison(){
        CollectableRocket pickableRocket = (CollectableRocket) compareColliderClass(CollectableRocket.class).getBody().getUserData();
        pickableRocket.destroy();

        Player.INSTANCE.setRocketAmount(Player.INSTANCE.getRocketAmount() + 1);
    }

    private Fixture compareColliderClass(Class<?> cls){
        if(fixtureA.getBody().getUserData().getClass().getName() == cls.getName()){
            return fixtureA;
        } else if (fixtureB.getBody().getUserData().getClass().getName() == cls.getName()) {
            return fixtureB;
        }
        return null;
    }
}
