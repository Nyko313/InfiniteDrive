package com.infinitedrive;

import com.badlogic.gdx.physics.box2d.*;
import com.infinitedrive.objects.NPCVehicleSpawner;
import com.infinitedrive.objects.Player;
import com.infinitedrive.objects.SpawnedNPCVehicle;
import com.infinitedrive.objects.Vehicle;

public class GameContactListener implements ContactListener {

    private Fixture fixtureA;
    private Fixture fixtureB;
    private SpawnedNPCVehicle vehicleA;
    private SpawnedNPCVehicle vehicleB;
    public GameContactListener(){
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");

        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        if(fixtureA.getBody().getUserData() != "Player" && fixtureB.getBody().getUserData() != "Player") {
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

    private void playerNPCCollision(){
        Player.INSTANCE.crash();
    }

    private void NPCCollision(){
        // Get the colliding bodies
        for (SpawnedNPCVehicle vehicle :NPCVehicleSpawner.INSTANCE.getVehicles()) {
            if(vehicle.getBody().getUserData() == fixtureA.getBody().getUserData()){
                vehicleA = vehicle;
            }
        }
        for (SpawnedNPCVehicle vehicle :NPCVehicleSpawner.INSTANCE.getVehicles()) {
            if(vehicle.getBody().getUserData() == fixtureB.getBody().getUserData()){
                vehicleB = vehicle;
            }
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
}
