package Main;

import TaskExecutingStrategy.CamelCaseTask;
import TaskExecutingStrategy.OrderTask;
import TaskExecutingStrategy.SumTask;
import TaskExecutingStrategy.ReverseTask;
import TaskExecutingStrategy.ExecutingStrategy;
import OutputService.ResultOutput;
import TaskExceptions.*;

/**
 * Object <code> Task </code> represents one task imported from imput
 * @author Andrzej
 */
public class Task implements ResultOutput {
    /**
     * Type of task to be executed
     */
    private String taskType;
    /**
     * Task parameters from record
     */
    private String taskParameters;
    /**
     * Result of the task
    */
    private String result;
    
    /**
     * Factory witch checks task type and produces object of proper executing strategy implementing class
     * @return ExecutionStrategy is a object of proper task type executing class
     * @throws UnknownTaskTypeException 
     */
    ExecutingStrategy TaskTypeFactory() throws UnknownTaskTypeException{
        ExecutingStrategy strategy = null;
        switch(this.taskType){
            case "SUM":strategy = new SumTask();
                break;
            case "CC": strategy = new CamelCaseTask();
                break;
            case "REVERSE": strategy = new ReverseTask();
                break;
            case "ORDER": strategy = new OrderTask();
                break;
            default : throw new UnknownTaskTypeException("Unknown type of task!");
        }
        return strategy;
    }   
    
    String getTaskType(){
        return this.taskType;
    }
    String getTaskParameters(){
        return this.taskParameters;
    }
    public void setTaskType(String taskType)throws UnknownTaskTypeException {
        
        if(taskType.isEmpty())throw new UnknownTaskTypeException("Empty task type");
        this.taskType=taskType.trim();
    }
    public void setTaskParameters(String taskParameters) throws WrongParameterFormatException{
        if(taskParameters.isEmpty())throw new WrongParameterFormatException("Empty parameter line");
        this.taskParameters=taskParameters;
    }
    void processResult()throws UnknownTaskTypeException, WrongParameterFormatException{
        result = this.TaskTypeFactory().execute(this.getTaskParameters());
    }
    
    @Override
    public void printResult() {
        System.out.println(result+"\n");
    }
    
}
