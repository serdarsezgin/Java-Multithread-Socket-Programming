/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientType1;

import ClientType2.Planners;
import Job.Jobs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ss
 */
public class Machines {

    public String name;
    public int id;
    public String type;
    public int speed;
    public String status;

    public static ArrayList<Machines> MachinesList = new ArrayList<Machines>();
    
    public static Map<Integer, Jobs> jobMap = new ConcurrentHashMap<Integer, Jobs>();
  

    public Machines(String name, int id, String type, int speed) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.speed = speed;
        this.status = "EMPTY";
        MachinesList.add(this);
    }

    public Machines() {
        this.status = "EMPTY";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return (" MachineName: " + name + " Machineid: " + id + " MachineType: " + type + " MachineSpeed: " + speed + " MachineStatus: " + status);
    }

}
