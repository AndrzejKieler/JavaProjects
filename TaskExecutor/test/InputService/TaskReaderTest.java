/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputService;

import Main.Task;
import java.io.FileNotFoundException;
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
public class TaskReaderTest {
    
    public TaskReaderTest() {
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
     * Test of contructor TaskReader() with empty path parameter
     */
    @Test (expected = FileNotFoundException.class)
    public void testReadTask() throws Exception {
        System.out.println("readTask");
        TaskReader instance = new TaskReader("");
       
    }

    /**
     * Test of contructor TaskReader() wih null path parameter
     */
    @Test (expected = FileNotFoundException.class)
    public void testReadTask2() throws Exception {
        System.out.println("readTask");
        TaskReader instance = new TaskReader(null);
       
    }
   
    
}
