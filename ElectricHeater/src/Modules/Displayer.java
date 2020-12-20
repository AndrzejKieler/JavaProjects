/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modules;

import Modules.Heater;
import Main.ElectricHeater;
import static java.sql.DriverManager.println;


/**
 *
 * @author Andrzej
 */
public class Displayer {

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
    String getBlockade(){
        if(ElectricHeater.Parameters.blockade==true)return "BLOCKED";
        else return " ";
    }
    public void clearScreen(){

        for(int i=0;i<20;i++)
        {
            System.out.println("\n");
        }

    }

    String powerOnStatus(){
        if(ElectricHeater.Parameters.powerOnStatus!=true)return "OFF";
        else return "ON";
    }

    void burnerDesplayer(int burnerId, String burnerStatus){
        System.out.println("Burner " + burnerId + burnerStatus);
    }

    public void displayState(String massage, Heater[] burners){

        clearScreen();
        System.out.println(massage);
        System.out.println("\n" + powerOnStatus());
        System.out.println(getBlockade());

        burnerDesplayer(1, burners[0].getBurnerStatus());
        burnerDesplayer(2, burners[1].getBurnerStatus());
        burnerDesplayer(3, burners[2].getBurnerStatus());
        burnerDesplayer(4, burners[3].getBurnerStatus());

    }


}
