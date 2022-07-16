// This script is used ONLY to create new json vehicles

package com.infinitedrive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.infinitedrive.objects.NPCVehicle;
import com.infinitedrive.objects.Vehicle;


public class DataHandler {
    public static Vehicle loadPlayerVehicle(String name){
        Json json = new Json();
        FileHandle file = Gdx.files.local("Data\\PlayerVehicle\\" + name + ".json");
        String s = file.readString();
        return json.fromJson(Vehicle.class, s);
    }

    public static void createPlayerVehicle(String name, float startingVelocity, float maxVelocity, float accelleration, float brakeDurability, int price, int width, int height, float sizeMultiplier, String texture){
        Vehicle vehicle = new Vehicle(name, startingVelocity,maxVelocity, accelleration, brakeDurability, price, width, height, sizeMultiplier, texture);
        Json json = new Json();
        String s = json.toJson(vehicle);

        FileHandle jsonFile = Gdx.files.local("Data\\PlayerVehicle\\" + vehicle.getName() + ".json");
        jsonFile.writeString(json.prettyPrint(s), true);

    }

    public static Array<NPCVehicle> loadNPCVehicles(int nOfVehicle){
        Array<NPCVehicle> vehicles = new Array<>();
        for(int i = 0; i< nOfVehicle; i++){
            Json json = new Json();
            FileHandle file = Gdx.files.local("Data\\NPCVehicle\\NPCVehicle" + (i +1) + ".json");
            String s = file.readString();
            vehicles.add(json.fromJson(NPCVehicle.class, s));
        }

        return vehicles;
    }

    public static void createNPCVehicle(String name, int maxVelocity, int minVelocity, int width, int height, float sizeMultiplier, String texture){
        NPCVehicle vehicle = new NPCVehicle(name,maxVelocity, minVelocity, width, height, sizeMultiplier, texture);
        Json json = new Json();
        String s = json.toJson(vehicle);

        FileHandle jsonFile = Gdx.files.local("Data\\NPCVehicle\\" + vehicle.getName() + ".json");
        jsonFile.writeString(json.prettyPrint(s), true);

    }
}
