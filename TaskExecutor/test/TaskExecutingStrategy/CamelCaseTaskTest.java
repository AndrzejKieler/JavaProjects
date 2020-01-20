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
public class CamelCaseTaskTest {
    
    public CamelCaseTaskTest() {
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
     * Test of execute method, of class CamelCaseTask with non-alphabetical input
     */
    @Test(expected = WrongParameterFormatException.class)
    public void testExecute() throws Exception {
        System.out.println("execute");
        String parameters = "java variable2";
        CamelCaseTask instance = new CamelCaseTask();
        String result = instance.execute(parameters);
    }
    
}
