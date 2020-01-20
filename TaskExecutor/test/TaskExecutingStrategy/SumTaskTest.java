/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskExecutingStrategy;

import TaskExceptions.WrongParameterFormatException;
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
public class SumTaskTest {
    
    public SumTaskTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of execute method, of class SumTask witch not only digital input
     */
    @Test(expected = WrongParameterFormatException.class)
    public void testExecute1() throws Exception {
        System.out.println("execute1");
        String parameters = "1 2 5 O";
        SumTask instance = new SumTask();
        String result = instance.execute(parameters);
        
    }
    /**
     * Test of rejecting float parameters
     * @throws Exception 
     */
    @Test(expected = WrongParameterFormatException.class)
    public void testExecute2() throws Exception {
        System.out.println("execute1");
        String parameters = "1 7 5 3.15";
        SumTask instance = new SumTask();
        String result = instance.execute(parameters);
        
    }
    
}
