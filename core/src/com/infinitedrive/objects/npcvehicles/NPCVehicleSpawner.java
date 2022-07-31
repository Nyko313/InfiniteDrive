package com.infinitedrive.objects.npcvehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.infinitedrive.helpers.Const;
import com.infinitedrive.helpers.DataHandler;
import com.infinitedrive.Gameobject;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.objects.rocket.CollectableRocket;
import com.infinitedrive.objects.Player;

import java.util.Random;

public class NPCVehicleSpawner extends Gameobject {
    public static NPCVehicleSpawner INSTANCE;
    private Random rand;

    private int vehicleCounter;
    private int vehicleCounterOnMissileSpawn;
    private float lastDistance;
    private double spawnDistance;
    private int lastSpawnPos;
    private int laneSpawnCounter = -1;
    private double spawnDistanceMultiplier = 2f;
    private World world;

    private Array<NPCVehicle> npcVehicles;
    private Array<SpawnedNPCVehicle> vehicles;
    public Array<SpawnedNPCVehicle> getVehicles() {
        return vehicles;
    }
    private int[] spawnPos = {-Const.LANES_DISTANCE,0,Const.LANES_DISTANCE};


    public NPCVehicleSpawner(World world) {
        super.renderPriority = 3;
        initialize(this);

        this.world = world;
        INSTANCE = this;
        rand = new Random();
        vehicles = new Array<>();
        spawnDistance = Const.NPCVEHICLES_SPAWN_DISTANCE;

        // Load all NPC vehicle
        npcVehicles = DataHandler.loadNPCVehicles(Const.NPCVEHICLES_QUANTITY);
    }
    @Override
    public void update(){



        // Spawn vehicle/collectable rocket
        if(Player.INSTANCE.getDistanceTraveled() - lastDistance > spawnDistance && vehicles.size < Const.MAX_NPCVEHICLES_IN_SCENE){
            // Spawn a rocket every 15 vehicle
            if(vehicleCounter - vehicleCounterOnMissileSpawn == 15){
                new CollectableRocket(spawnPos[new Random().nextInt(3)] + InfiniteDrive.INSTANCE.getScreenWidth() / 2f, InfiniteDrive.INSTANCE.getScreenHeight());
                vehicleCounterOnMissileSpawn = vehicleCounter;
            }else{
                spawnVehicle();
            }
        }

        // Update NPC vehicles
        for(int i = 0; i < vehicles.size; i++){
            vehicles.get(i).update();
        }
    }

    @Override
    public void render(){
        // Draw all vehicles
        for(int i = 0; i < vehicles.size; i++){
            batch.draw(vehicles.get(i).getTexture(),vehicles.get(i).getBody().getPosition().x - vehicles.get(i).getWidth() / 2 ,vehicles.get(i).getBody().getPosition().y - vehicles.get(i).getHeight()/ 2 , vehicles.get(i).getWidth(), vehicles.get(i).getHeight());
        }
    }

    public void destroyVehicle(SpawnedNPCVehicle vehicle){
        world.destroyBody(vehicle.getBody());
        vehicles.removeValue(vehicle, false);
    }

    @Override
    public void dispose() {
        for(SpawnedNPCVehicle vehicle : vehicles){
            vehicle.dispose();
        }
    }

    private void spawnVehicle(){
        // Set a random spawn position
        int spawnPosition = spawnPos[new Random().nextInt(3)];
        // Check how many times a vehicle spawn in a lane
        if(spawnPosition == lastSpawnPos && laneSpawnCounter >= Const.MAX_NPCVEHICLES_IN_LANE){
            do{
                spawnPosition = spawnPos[new Random().nextInt(3)];
            }while(spawnPosition == lastSpawnPos);
            laneSpawnCounter = 0;
        }
        lastSpawnPos = spawnPosition;
        laneSpawnCounter ++;

        spawnPosition += InfiniteDrive.INSTANCE.getScreenWidth() / 2f;

        // Instantiate the new vehicle
        SpawnedNPCVehicle vehicle = new SpawnedNPCVehicle(npcVehicles.get(rand.nextInt(Const.NPCVEHICLES_QUANTITY)),world, spawnPosition,InfiniteDrive.INSTANCE.getScreenHeight() + npcVehicles.get(rand.nextInt(Const.NPCVEHICLES_QUANTITY)).getHeight());

        vehicles.add(vehicle);
        lastDistance = Player.INSTANCE.getDistanceTraveled();
        vehicleCounter++;
    }


}
