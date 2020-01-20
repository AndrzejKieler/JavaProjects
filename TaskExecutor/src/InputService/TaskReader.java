/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Main.Task;
import TaskExceptions.UnknownTaskTypeException;
import TaskExceptions.WrongParameterFormatException;
import java.util.NoSuchElementException;

/**
 *
 * @author Andrzej
 */
public class TaskReader {
    
    private final Scanner scanner;
  
    public  TaskReader(String path) throws FileNotFoundException, NullPointerException{
        scanner = new Scanner(new File(path));
                
        
    }
  
  
    public  boolean hasNextTask(){
       return scanner.hasNextLine();
    }
  
   
    public Task readTask()throws Exception{
       Task task = new Task();
            task.setTaskType(scanner.nextLine());
            task.setTaskParameters(scanner.nextLine());
        
        return task;
    }
  
  
    public   void readerClose(){
        scanner.close();
    }
}
