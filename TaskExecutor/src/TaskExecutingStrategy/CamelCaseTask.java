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
public class CamelCaseTask implements ExecutingStrategy {
    
    @Override
    public String execute(String parameters) throws WrongParameterFormatException{
        
        if(!parameters.matches("^[a-zA-Z\\s]*$"))throw new WrongParameterFormatException("Forbidden non-letter characters \n");
        
        String[] parametersTable = parameters.trim().split(" ");
        String result="";
        
        for(String parameter : parametersTable){
           parameter.toLowerCase();
           parameter = parameter.substring(0, 1).toUpperCase()+parameter.substring(1);
           result+=parameter;
        }
        
        result = result.substring(0, 1).toLowerCase()+result.substring(1); //if first char in lower case
        
        return result;
    }
    
}
