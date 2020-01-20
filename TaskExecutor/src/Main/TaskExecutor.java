package Main;


import InputService.TaskReader;
import java.util.NoSuchElementException;
import TaskExceptions.*;
import java.io.FileNotFoundException;

/**
 * Recrutation task whitch executes from flat file imported tasks
 * @author Andrzej
 */
public class TaskExecutor {
    
        private static int records  = 0;
        private static int executed = 0;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        TaskReader taskReader = null;
        
        /*
         * Type path to input flat file
         */
        String path = "/Users/Andrzej/Documents/input.txt";
        try{
            taskReader = new TaskReader(path);
        }catch (FileNotFoundException | NullPointerException e) {
            System.out.println(e.getMessage()+"! Retype input path");
            System.exit(0);
        }  
       
        while(taskReader.hasNextTask()){
           
            records++;
           
            Task task;
       
            try{
                task = taskReader.readTask();
            }
            catch(NoSuchElementException | NullPointerException e){
                System.out.println(e.getMessage()+" Task without parameters");
                continue;
            }
            catch(UnknownTaskTypeException | WrongParameterFormatException e){
                System.out.println(e.getMessage()+"\n");
                continue;
            }
            try{
                    task.processResult();
            }
            catch(UnknownTaskTypeException|WrongParameterFormatException e){
                System.out.println(e.getMessage()); 
                continue;
            }
            task.printResult();
            executed++;
       }
       taskReader.readerClose();
       
       report();
       
    }
    
    
    static void report(){
        System.out.println("\nTaskExecutor process finished.");
        System.out.println("Records with tasks in input: "+records);
        System.out.println("Task executed: "+executed);
        System.out.println("Records rejected: "+(records-executed));
    }
}
