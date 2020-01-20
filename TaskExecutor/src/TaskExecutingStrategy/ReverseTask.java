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
public class ReverseTask implements ExecutingStrategy{

    @Override
    public String execute(String parameters) throws WrongParameterFormatException {
        if(parameters.trim().split(" ").length==1 && parameters.matches("^[a-zA-Z]*$")){
            parameters = new StringBuilder(parameters).reverse().toString();
        }
        else throw new WrongParameterFormatException("Wrong word to reversion\n");
        return parameters.toString();
    }
    
}
