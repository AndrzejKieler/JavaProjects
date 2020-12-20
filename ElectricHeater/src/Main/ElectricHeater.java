package Main;

import Modules.*;
import Main.ElectricHeater.Parameters;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrzej
 */
public class ElectricHeater {

    private static Parameters Parameters;

    public static class Parameters{
        public static boolean powerOnStatus = false;
        public static boolean applicationOn = true;
        public static boolean blockade = true;
        public static int burnerFlag = 4;
    }

    public static void main(String[] args) {

        Interface userInterface = new Interface();
        Displayer displayer =  new Displayer();
        Heater[] heater = new Heater[4];
        heater[0] = new Heater(1);
        heater[1] = new Heater(2);
        heater[2] = new Heater(3);
        heater[3] = new Heater(4);

        displayer.displayState(userInterface.massage(Interface.Massages.ONOF), heater);


        userInterface.operation(Parameters, heater);




    }
}


