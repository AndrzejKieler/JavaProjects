/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import TaskExceptions.UnknownTaskTypeException;
import TaskExceptions.WrongParameterFormatException;
import TaskExecutingStrategy.ExecutingStrategy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andrzej
 */
public class TaskTest {
    
    public TaskTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {

    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.flush();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        
        System.out.flush();
    }

    /**
     * Test of TaskTypeFactory method, then Task type is wrong typed.
     */
    @Test(expected = UnknownTaskTypeException.class)
    public void testTaskTypeFactory() throws Exception {
        System.out.println("TaskTypeFactory");
        Task instance = new Task();
        instance.setTaskType("ADDD");
        instance.TaskTypeFactory();
       
    }

    
    /**
     * Test of setTaskType method, of class Task.
     */
    @Test
    public void testSetTaskType() throws Exception {
        System.out.println("setTaskType");
        String taskType = " REVERSE ";
        Task instance = new Task();
        instance.setTaskType(taskType);
        assertEquals("REVERSE", instance.getTaskType());

    }

    /**
     * Test of setTaskParameters method, of class Task.
     */
    @Test
    public void testSetTaskParameters() throws Exception {
        System.out.println("setTaskParameters");
        String taskParameters = "";
        Task instance = new Task();
        try{
            instance.setTaskParameters(taskParameters);
        }catch(WrongParameterFormatException e){
            assertEquals("Empty parameter line", e.getMessage());
        }
        
    }


}
