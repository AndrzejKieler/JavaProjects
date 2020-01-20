/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskExceptions;

/**
 *
 * @author Andrzej
 */
public class UnknownTaskTypeException extends Exception{
    public UnknownTaskTypeException(String string){
        super(string);
    }
}
