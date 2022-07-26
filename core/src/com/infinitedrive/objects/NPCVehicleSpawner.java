package com.infinitedrive.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.infinitedrive.Const;
import com.infinitedrive.DataHandler;
import com.infinitedrive.Gameobject;
import com.infinitedrive.InfiniteDrive;

import java.util.Random;

public class NPCVehicleSpawner extends Gameobject {
    public static NPCVehicleSpawner INSTANCE;
    private Random rand;

    private Array<NPCVehicle> npcVehicles;
    private Array<SpawnedNPCVehicle> vehicles;
    private int[] spawnPos = {-Const.LANES_DISTANCE,0,Const.LANES_DISTANCE};

    private int vehicleCounter;
    private float lastDistance;
    private float spawnDistance;
    private int lastSpawnPos;
    private int laneSpawnCounter = -1;
    private SpriteBatch batch;
    private World world;


    public Array<SpawnedNPCVehicle> getVehicles() {
        return vehicles;
    }

    public NPCVehicleSpawner(World world) {
        super.renderPriority = 2;
        initialize(this);

        this.world = world;
        INSTANCE = this;
        rand = new Random();
        batch = InfiniteDrive.INSTANCE.getBatch();

        // Load all NPC vehicle
        npcVehicles = DataHandler.loadNPCVehicles(Const.NPCVEHICLES_QUANTITY);

        vehicles = new Array<>();
        spawnDistance = Const.NPCVEHICLES_SPAWN_DISTANCE;

        // Spawn the first random vehicle
        spawnVehicle();
    }

    public void update(){
        if(Player.INSTANCE.getDistanceTraveled() - lastDistance > spawnDistance && vehicles.size < Const.MAX_NPCVEHICLES_IN_SCENE){
            spawnVehicle();
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
        vehicleCounter--;
    }

    private void spawnVehicle(){

        int spawnPosition = spawnPos[new Random().nextInt(3)];
        if(spawnPosition == lastSpawnPos && laneSpawnCounter >= Const.MAX_NPCVEHICLES_IN_LANE){
            do{
                spawnPosition = spawnPos[new Random().nextInt(3)];
            }while(spawnPosition == lastSpawnPos);
            laneSpawnCounter = 0;
        }
        lastSpawnPos = spawnPosition;
        laneSpawnCounter ++;

        spawnPosition += InfiniteDrive.INSTANCE.getScreenWidth() / 2f;
        SpawnedNPCVehicle vehicle = new SpawnedNPCVehicle(npcVehicles.get(rand.nextInt(Const.NPCVEHICLES_QUANTITY)),world, spawnPosition,InfiniteDrive.INSTANCE.getScreenHeight() + npcVehicles.get(rand.nextInt(Const.NPCVEHICLES_QUANTITY)).getHeight());

        vehicles.add(vehicle);
        vehicle.getBody().setUserData(Integer.toString(vehicleCounter));
        vehicleCounter++;
        lastDistance = Player.INSTANCE.getDistanceTraveled();
    }


}
