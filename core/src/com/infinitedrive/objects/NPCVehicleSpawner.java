package com.infinitedrive.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.infinitedrive.Const;
import com.infinitedrive.DataHandler;
import com.infinitedrive.InfiniteDrive;

import java.util.Random;

public class NPCVehicleSpawner{
    public static NPCVehicleSpawner INSTANCE;
    private Random rand;

    private Array<NPCVehicle> npcVehicles;
    private Array<SpawnedNPCVehicle> vehicles;
    private int[] spawnPos = {-Const.LANES_DISTANCE,0,Const.LANES_DISTANCE};

    private int vehicleCounter;
    private float lastDistance;
    private float spawnDistance;
    private SpriteBatch batch;
    private World world;


    public Array<SpawnedNPCVehicle> getVehicles() {
        return vehicles;
    }

    public NPCVehicleSpawner(World world) {
        this.world = world;
        INSTANCE = this;
        rand = new Random();
        batch = InfiniteDrive.INSTANCE.getBatch();

        // Load all NPC vehicle
        npcVehicles = DataHandler.loadNPCVehicles(Const.NPCVEHICLES_QUANTITY);

        vehicles = new Array<>();
        spawnDistance = Const.NPCVEHICLES_SPAWN_DISTANCE;

        // Spawn first random vehicle
        spawnVehicle(rand.nextInt(Const.NPCVEHICLES_QUANTITY));
    }

    public void update(){
        if(Player.INSTANCE.getDistanceTraveled() - lastDistance > spawnDistance){
            spawnVehicle(rand.nextInt(Const.NPCVEHICLES_QUANTITY));
        }

        for(int i = 0; i < vehicles.size; i++){

            vehicles.get(i).update();

        }
    }

    public void render(){
        // Draw all vehicles
        for(int i = 0; i < vehicles.size; i++){
            batch.draw(vehicles.get(i).getTexture(),vehicles.get(i).getBody().getPosition().x - (vehicles.get(i).getWidth() * vehicles.get(i).getSizeMultiplier()) / 2 ,vehicles.get(i).getBody().getPosition().y - (vehicles.get(i).getHeight() * vehicles.get(i).getSizeMultiplier()) / 2 , vehicles.get(i).getWidth() * vehicles.get(i).getSizeMultiplier(), vehicles.get(i).getHeight() * vehicles.get(i).getSizeMultiplier());
        }
    }

    public void destroyVehicle(SpawnedNPCVehicle vehicle){
        world.destroyBody(vehicle.getBody());
        vehicles.removeValue(vehicle, false);
    }

    private void spawnVehicle(int index){
        SpawnedNPCVehicle vehicle = new SpawnedNPCVehicle(npcVehicles.get(index),world, spawnPos[new Random().nextInt(3)] + InfiniteDrive.INSTANCE.getScreenWidth() / 2f,InfiniteDrive.INSTANCE.getScreenHeight() + npcVehicles.get(index).getHeight());

        vehicles.add(vehicle);
        vehicle.getBody().setUserData(Integer.toString(vehicleCounter));
        vehicleCounter++;
        lastDistance = Player.INSTANCE.getDistanceTraveled();
    }


}
