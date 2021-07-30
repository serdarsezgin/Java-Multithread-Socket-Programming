/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Job;

import ClientType1.Machines;
import java.util.ArrayList;

/**
 *
 * @author ss
 */
public class Jobs {
    
    public int id;
    public String type;
    public int uzunluk;
    
    public static ArrayList<Jobs> JobsList = new ArrayList<Jobs>();
    
    
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

    public int getUzunluk() {
        return uzunluk;
    }

    public void setUzunluk(int uzunluk) {
        this.uzunluk = uzunluk;
    }

    public static ArrayList<Jobs> getJobsList() {
        return JobsList;
    }

    public static void setJobsList(ArrayList<Jobs> JobsList) {
        Jobs.JobsList = JobsList;
    }
     @Override
    public String toString() {
        return (" JobId: " + id + " JobType: " + type + " JobTime: " + uzunluk);
    }
    
}
