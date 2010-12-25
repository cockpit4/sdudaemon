/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.guifilemanager.model;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.io.File;
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
public class FileSystemTreeModelTest {
    private File testDir = new File(System.getProperty("user.dir"));
    public FileSystemTreeModelTest() {
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

    /**
     * Test of getRoot method, of class FileSystemTreeModel.
     */
//    @Test
//    public void testGetRoot() {
//        System.out.println("getRoot");
//        FileSystemTreeModel instance = new FileSystemTreeModel();
//        Object expResult = null;
//        Object result = instance.getRoot();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getChild method, of class FileSystemTreeModel.
     */
    @Test
    public void testGetChild() {
        System.out.println("getChild");
        FileSystemTreeNode parent = new FileSystemTreeNode(testDir, null);
        FileSystemTreeModel testmodel = new FileSystemTreeModel();
        int count = parent.getChildCount();
        boolean noerror = true;

        for(int i = 0 ;i<count;i++){
            FileSystemTreeNode child = (FileSystemTreeNode) testmodel.getChild(parent, i);
            if(!((FileSystemTreeNode)parent.getChildAt(i)).getFile().equals(child.getFile())){
                noerror = false;
            }
        }
        assertTrue(noerror);
    }

    /**
     * Test of getChildCount method, of class FileSystemTreeModel.
     */
    @Test
    public void testGetChildCount() {
        System.out.println("getChildCount");
        FileSystemTreeNode parent = new FileSystemTreeNode(testDir, null);
        FileSystemTreeModel instance = new FileSystemTreeModel();
        int expResult = parent.getChildCount();
        int result = instance.getChildCount(parent);
        assertEquals(expResult, result);
    }

    /**
     * Test of isLeaf method, of class FileSystemTreeModel.
     */
    @Test
    public void testIsLeaf() {
        System.out.println("isLeaf");
        FileSystemTreeNode node = new FileSystemTreeNode(testDir, null);
        FileSystemTreeModel instance = new FileSystemTreeModel();
        boolean expResult = node.isLeaf();
        boolean result = instance.isLeaf(node);
        assertEquals(expResult, result);
    }

    /**
     * Test of valueForPathChanged method, of class FileSystemTreeModel.
     */
//    @Test
//    public void testValueForPathChanged() {
//        System.out.println("valueForPathChanged");
//        TreePath path = null;
//        Object newValue = null;
//        FileSystemTreeModel instance = new FileSystemTreeModel();
//        instance.valueForPathChanged(path, newValue);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getIndexOfChild method, of class FileSystemTreeModel.
     */
    @Test
    public void testGetIndexOfChild() {
        System.out.println("getIndexOfChild");
        FileSystemTreeNode parent = new FileSystemTreeNode(testDir, null);
        FileSystemTreeModel model = new FileSystemTreeModel();
        Enumeration children = parent.children();
        boolean noerror = true;
        for(int i = 0; children.hasMoreElements(); i++){
            TreeNode child = (TreeNode)children.nextElement();
            int index = model.getIndexOfChild(parent, child);
            int expIndex = parent.getIndex(child);
            if(index != expIndex){
                noerror = false;
            }
        }

        assertTrue(noerror);
        
    }

    /**
     * Test of addTreeModelListener method, of class FileSystemTreeModel.
     */
//    @Test
//    public void testAddTreeModelListener() {
//        System.out.println("addTreeModelListener");
//        TreeModelListener l = null;
//        FileSystemTreeModel instance = new FileSystemTreeModel();
//        instance.addTreeModelListener(l);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of removeTreeModelListener method, of class FileSystemTreeModel.
     */
//    @Test
//    public void testRemoveTreeModelListener() {
//        System.out.println("removeTreeModelListener");
//        TreeModelListener l = null;
//        FileSystemTreeModel instance = new FileSystemTreeModel();
//        instance.removeTreeModelListener(l);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}