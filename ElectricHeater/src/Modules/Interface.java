/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modules;

import Main.ElectricHeater.Parameters;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrzej
 */
public class Interface {
    public enum Massages{
        ONOF, UNLOCK, BLOCKorBURNER, BURNERSTATUS, EXIT, WARNING
    }
    public Massages massageManager(){
        if(!Parameters.powerOnStatus)return Massages.ONOF;
        else if(Parameters.blockade)return Massages.UNLOCK;
        else if(Parameters.burnerFlag!=4) return Massages.BURNERSTATUS;
        else return Massages.BLOCKorBURNER;
    }
    public String massage(Massages masage){
        switch(masage){
            case ONOF: return "Type 'o' to tern ON/OFF";
            case UNLOCK: return "Burner locked. Type 'b' key.";
            case BLOCKorBURNER: return "Choose a burner (1-4) or type 'b' to lock";
            case BURNERSTATUS: return "Type power from 0 to 9";
            case EXIT: return "Press 'c' to close system";
            case WARNING: return "Unexpected key typed";
        }
        return "0";
    }

    public void operation(Parameters param, Heater[] heater){
        Displayer displayer = new Displayer();

        Scanner scanner = new Scanner(System.in);

        while(Parameters.applicationOn){
            String s = scanner.nextLine().trim();
            if(s.length()>1)
            {
                displayer.displayState(massage(Massages.WARNING), heater);
                break;
            }
            else{
                char key = s.charAt(0);

                switch(key){

                    case 'o':   if(!param.powerOnStatus) {
                                    param.powerOnStatus ^= true;
                                    param.burnerFlag = 4;
                                    displayer.displayState(massage(Massages.UNLOCK), heater);
                                }
                                else if(param.blockade==false){
                                    param.powerOnStatus ^= true;
                                    param.blockade=true;
                                    displayer.displayState(massage(Massages.ONOF), heater);
                                }
                                else displayer.displayState(massage(Massages.UNLOCK), heater);

                        break;
                    case 'c': System.exit(0);
                        break;
                    case 'b':   if(!(!param.powerOnStatus)){
                        param.blockade^=true;
                        param.burnerFlag=4;
                        displayer.displayState(massage(massageManager()), heater);
                    }
                    else displayer.displayState(massage(Massages.ONOF), heater);
                        break;
                    case '0':  case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
                        if(!param.blockade && param.powerOnStatus && param.burnerFlag==4 && (key-'0')<5 && key!='0'){
                            param.burnerFlag=key-1-'0';
                            displayer.displayState(massage(Massages.BURNERSTATUS), heater);
                            operation(param, heater);
                        }
                        else if(!param.blockade && param.powerOnStatus && param.burnerFlag<4){
                            heater[param.burnerFlag].setBurner(key-'0');//do sprawdzenia
                            param.burnerFlag=4;
                            displayer.displayState(massage(Massages.BLOCKorBURNER), heater);
                        }
                        else displayer.displayState(massage(massageManager()), heater);

                        break;
                    default:  displayer.displayState(massage(Massages.WARNING), heater);
                    {
                        try {
                            sleep(50);

                        } catch (InterruptedException ex) {
                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }


        }
    }}
