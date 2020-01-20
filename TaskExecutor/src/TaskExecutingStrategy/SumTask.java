/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskExecutingStrategy;

import TaskExceptions.WrongParameterFormatException;

/**
 *
 * @author Andrzej
 */
public class SumTask implements ExecutingStrategy{

    @Override
    public String execute(String parameters) throws WrongParameterFormatException{
        if(!parameters.matches("[0-9\\s]+"))throw new WrongParameterFormatException("Sum takes only numbers!\n");
        String[] parametersTable = parameters.trim().split(" ");
        int sum = 0;
        for(String parameter : parametersTable){
            sum+= Integer.parseInt(parameter);
        }
        return Integer.toString(sum);
    }
    
}
