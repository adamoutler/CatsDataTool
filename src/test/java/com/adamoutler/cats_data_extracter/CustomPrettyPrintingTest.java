/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adamoutler.cats_data_extracter;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assume.assumeTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adamo
 */
public class CustomPrettyPrintingTest {
    
    public CustomPrettyPrintingTest() {
        assumeTrue(!GraphicsEnvironment.isHeadless());
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        assumeTrue(!GraphicsEnvironment.isHeadless());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class DataManipulator.
     */
    @Test
    public void testMain() throws IOException {
        System.out.println("main");
        DataManipulator.fileLocation="src/msgCache.bin";
        DataManipulator.processMsgCache();
                
                
    }
    
}
