/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskExecutingStrategy;

import TaskExceptions.WrongParameterFormatException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Andrzej
 */
public class OrderTask implements ExecutingStrategy{

    @Override
    public String execute(String parameters) throws WrongParameterFormatException{
        
        if(!parameters.matches("^[a-zA-Z\\s]*$"))throw new WrongParameterFormatException("Forbidden non-letter characters \n");
        
        String[] parametersTable = parameters.trim().split(" ");
        String result = new String();
        List<String> parameterList = new ArrayList<>();
        
        for(String parameter : parametersTable){
            parameterList.add(parameter);
        }
        Collections.sort(parameterList,String.CASE_INSENSITIVE_ORDER);
        
        for(String parameter : parameterList){
            result+=parameter+" ";
        }
        return result;
    }
 
}
