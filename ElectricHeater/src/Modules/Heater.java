/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modules;

import Main.ElectricHeater;

/**
 *
 * @author Andrzej
 */
public class Heater{
    private final int id;
    private int burnerStatus;
    private long time;
    private long start;

    public Heater(int id){
        this.id = id;
        this.burnerStatus = 0;
    }
    public Heater(int id, int burnerStatus){
        this.id = id;
        this.burnerStatus = burnerStatus;
    }

    String getBurnerStatus(){
        if(System.currentTimeMillis()-start<time && burnerStatus==0 && ElectricHeater.Parameters.burnerFlag!=this.id-1) return ": h";
        else if(ElectricHeater.Parameters.burnerFlag==this.id-1)return ": _";
        else return ": " + burnerStatus;
    }

    void setBurner(int burnerStatus){
        if(this.burnerStatus > 0 && burnerStatus == 0)
        {
            time = this.burnerStatus * 1000L * 10;
            start = System.currentTimeMillis();
        }
        this.burnerStatus = burnerStatus;
    }
}

