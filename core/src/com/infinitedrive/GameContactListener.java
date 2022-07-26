package com.infinitedrive;

import com.badlogic.gdx.physics.box2d.*;
import com.infinitedrive.objects.*;

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

        if(fixtureA.getBody().getUserData() == "Rocket" || fixtureB.getBody().getUserData() == "Rocket"){
            NPCMissileCollision();
        }else if(fixtureA.getBody().getUserData() != "Player" && fixtureB.getBody().getUserData() != "Player") {
            NPCCollision();
        }else if(fixtureA.getBody().getUserData() == "Player" || fixtureB.getBody().getUserData() == "Player"){
            playerNPCCollision();
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
        Rocket rocket = Player.INSTANCE.getRocket();
        if(fixtureA.getBody().getUserData() != "Rocket"){
            vehicleA = FindNPCVehicle(fixtureA);
        }else {
            vehicleA = FindNPCVehicle(fixtureB);
        }

        rocket.explode();
        vehicleA.destroy();
    }
    private void playerNPCCollision(){
        Player.INSTANCE.crash();
        if(fixtureA.getBody().getUserData() != "Player"){
            vehicleA = FindNPCVehicle(fixtureA);
        }else {
            vehicleA = FindNPCVehicle(fixtureB);
        }

        if(vehicleA.getCurrentVelocity() > Player.INSTANCE.getCurrentVelocity()){
            vehicleA.setCurrentVelocity(0);
        }
    }

    private void NPCCollision(){
        // Get the colliding bodies
        vehicleA = FindNPCVehicle(fixtureA);
        vehicleB = FindNPCVehicle(fixtureB);

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

    private SpawnedNPCVehicle FindNPCVehicle(Fixture fixture){
        for (SpawnedNPCVehicle vehicle :NPCVehicleSpawner.INSTANCE.getVehicles()) {
            if(vehicle.getBody().getUserData() == fixture.getBody().getUserData()){
                return vehicle;
            }
        }
        return null;
    }
}
