/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import junit.framework.TestCase;

/**
 *
 * @author nicolasjimenez
 */
public class ParserTest extends TestCase {
    
    public ParserTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of parse method, of class Parser.
     */
    public void testParse() {
        System.out.println("parse");
        Parser instance = null;
        boolean expResult = false;
        boolean result = instance.parse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
