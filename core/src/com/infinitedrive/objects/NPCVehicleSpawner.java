package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.infinitedrive.Const;
import com.infinitedrive.DataHandler;
import com.infinitedrive.InfiniteDrive;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class NPCVehicleSpawner {
    private Array<NPCVehicle> npcVehicles;
    private Array<SpawnedNPCVehicle> vehicles;
    private Random rand;
    private float lastSpawnTime;
    private float timeSpawn;
    private int[] spawnPos = {-Const.LANES_DISTANCE,0,Const.LANES_DISTANCE};

    private SpriteBatch batch;
    public NPCVehicleSpawner() {
        rand = new Random();
        npcVehicles = DataHandler.LoadNPCVehicles(Const.NPCVEHICLES_QUANTITY);

        batch = InfiniteDrive.INSTANCE.getBatch();
        vehicles = new Array<>();
        spawnVehicle(rand.nextInt(Const.NPCVEHICLES_QUANTITY));
        timeSpawn = 800000000;
    }

    public void update(){
        if(TimeUtils.nanoTime() - lastSpawnTime > timeSpawn){
            spawnVehicle(rand.nextInt(Const.NPCVEHICLES_QUANTITY));
        }
        //System.out.println(TimeUtils.millis() - lastSpawnTime);
        //System.out.println(vehicles.get(0).getRectangle().x + "   " + vehicles.get(0).getRectangle().y);

        for(int i = 0; i < vehicles.size; i++){
            // Move vehicles
            vehicles.get(i).getRectangle().y -= (Player.INSTANCE.getVehicle().getStartingVelocity() - vehicles.get(i).getMinVelocity() ) * Const.VEHICLES_VELOCITY_MULTIPLIER* Gdx.graphics.getDeltaTime();

            // Delete vehicles
            if(vehicles.get(i).getRectangle().y < 0 - vehicles.get(i).getRectangle().y){
                vehicles.removeIndex(i);
            }

            // Collision
            if(vehicles.get(i).getRectangle().overlaps(Player.INSTANCE.getRectangle())){
                Gdx.app.exit();
                Vector2 vector2 = new Vector2();
                System.out.println(vehicles.get(i).getRectangle().getSize(vector2));
                System.out.println(vector2);
            }
            timeSpawn -= 10;

        }
    }

    public void render(){
        for(int i = 0; i < vehicles.size; i++){
            batch.draw(vehicles.get(i).getTexture(),vehicles.get(i).getRectangle().x - (vehicles.get(i).getWidth() * vehicles.get(i).getSizeMultiplier()) / 2 ,vehicles.get(i).getRectangle().y - (vehicles.get(i).getHeight() * vehicles.get(i).getSizeMultiplier()) / 2 , vehicles.get(i).getWidth() * vehicles.get(i).getSizeMultiplier(), vehicles.get(i).getHeight() * vehicles.get(i).getSizeMultiplier());
        }
    }

    private void spawnVehicle(int index){
        SpawnedNPCVehicle vehicle = new SpawnedNPCVehicle(npcVehicles.get(index), new Rectangle());

        vehicle.getRectangle().x = spawnPos[new Random().nextInt(3)] + InfiniteDrive.INSTANCE.getScreenWidth() / 2;

        vehicle.getRectangle().y = InfiniteDrive.INSTANCE.getScreenHeight() + npcVehicles.get(index).getHeight();
        vehicle.getRectangle().setWidth(npcVehicles.get(index).getWidth());
        vehicle.getRectangle().setHeight(npcVehicles.get(index).getHeight());
        vehicles.add(vehicle);
        lastSpawnTime = TimeUtils.nanoTime();
    }
}
