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
public interface ExecutingStrategy {
    String execute(String parameters) throws WrongParameterFormatException;
}
