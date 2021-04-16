/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adamoutler.cats_data_extracter;

import java.awt.GraphicsEnvironment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 *
 * @author adamo
 */
public class TablizerTest {
    
    public TablizerTest() {
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
     * Test of tablize method, of class Tablizer.
     */
    @Test
    public void testTablize() throws Exception {
        System.out.println("main");
        DataManipulator.fileLocation="src/msgCache.bin";
        DataManipulator.processMsgCache();
                
    }

}
