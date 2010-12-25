/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.guifilemanager.model;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kneo
 */
public class FileSystemTreeNodeTest {
    private final File testDir = new File(System.getProperty("user.dir"));
    public FileSystemTreeNodeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**Test of getChildAt method, of class FileSystemTreeNode.
     */
    @Test
    public void testGetChildAt() {
        System.out.println("getChildAt");
        int childIndex = 0;
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, null);
        boolean noerror = true;
        File[] testSet = testDir.listFiles();

        for(int i = 0 ; i < testSet.length;i++){
            File result = ((FileSystemTreeNode) instance.getChildAt(i)).getFile();
            File expResult = testSet[i];

            if(!result.equals(expResult)){ //some files are not listed
                noerror = false;
            }

        }
        assertEquals(true, noerror);
    }

    /**Test of getChildCount method, of class FileSystemTreeNode.
     */
    @Test
    public void testGetChildCount() {
        System.out.println("getChildCount");
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, null);
        int expResult = testDir.listFiles().length;
        int result = instance.getChildCount();
        assertEquals(expResult, result);
    }

    /**Test of getParent method, of class FileSystemTreeNode.
     */
    @Test
    public void testGetParent() {
        System.out.println("getParent");
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, new FileSystemTreeNode(testDir.getParentFile(), null));
        File expResult = testDir.getParentFile();
        File result = ((FileSystemTreeNode)instance.getParent()).getFile();
        assertEquals(expResult, result);
    }

    /**Test of getIndex method, of class FileSystemTreeNode.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex:");
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, null);
        boolean noerror = true;
        File[] testSet = testDir.listFiles();

        for(int i = 0; i<testSet.length;i++){
            int index = i;
            int node = instance.getIndex(new FileSystemTreeNode(testSet[i],instance));
            System.out.println("\tindex "+index+" node "+node+ " name "+testSet[i].toString());
            if(node != i)
                noerror = false;
        }

        assertTrue(noerror);
    }

    /**Test of getAllowsChildren method, of class FileSystemTreeNode.
     */
    @Test
    public void testGetAllowsChildren() {
        System.out.println("getAllowsChildren");
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, null);
        boolean expResult = testDir.isDirectory();
        boolean result = instance.getAllowsChildren();
        assertEquals(expResult, result);
    }

    /**Test of isLeaf method, of class FileSystemTreeNode.
     */
    @Test
    public void testIsLeaf() {
        System.out.println("isLeaf");
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, null);
        boolean expResult = testDir.isFile();
        boolean result = instance.isLeaf();
        assertEquals(expResult, result);
    }

    /**Test of children method, of class FileSystemTreeNode.
     */
    @Test
    public void testChildren() {
        System.out.println("children");
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, null);
        File[] expResult = testDir.listFiles();
        Enumeration result = instance.children();
        boolean noerror = true;

        for(int i = 0; result.hasMoreElements(); i++){
            if(!expResult[i].equals(((FileSystemTreeNode)result.nextElement()).getFile()))
                noerror = false;
        }

        assertTrue(noerror);
    }

    /**Test of getFile method, of class FileSystemTreeNode.
     */
    @Test
    public void testGetFile() {
        System.out.println("getFile");
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, null);
        File expResult = testDir;
        File result = instance.getFile();
        assertEquals(expResult, result);
    }

    /**Test of toString method, of class FileSystemTreeNode.
     */
    @Test
    public void testToString() throws IOException {
        System.out.println("toString");
        FileSystemTreeNode instance = new FileSystemTreeNode(testDir, null);
        String expResult = testDir.getName();
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}