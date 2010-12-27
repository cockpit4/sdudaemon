/*
Copyright (c) 2010 cockpit4, Kevin Krüger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package de.cockpit4.sdudaemon.installer.guifilemanager.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author kneo
 */
public class FileSystemTreeModel implements TreeModel{
    private TreeNode root;
    private ArrayList<TreeModelListener> treeModelListeners = new ArrayList<TreeModelListener>();

    private void readRoots(){
        final File[] fsroot = File.listRoots();

        if(fsroot.length>1){ //more than 1 filesystem root on windows OS
            root = new TreeNode() {
                FileSystemTreeNode[] roots = new FileSystemTreeNode[fsroot.length];

                public TreeNode getChildAt(int childIndex) {
                    return roots[childIndex];
                }

                public int getChildCount() {
                    return roots.length;
                }

                public TreeNode getParent() {
                    return null;
                }

                public int getIndex(TreeNode node) {
                    FileSystemTreeNode n = (FileSystemTreeNode) node;
                    for(int i = 0; i<roots.length;i++){
                        if(roots[i].getFile().equals(n.getFile())){
                            return i;
                        }
                    }
                    return -1;
                }

                public boolean getAllowsChildren() {
                    return true;
                }

                public boolean isLeaf() {
                    return false;
                }

                public Enumeration children() {
                    return new Enumeration() {
                        int counter = 0;
                        public boolean hasMoreElements() {
                            return roots.length > counter;
                        }

                        public Object nextElement() {
                            return roots[counter++];
                        }
                    };
                }

                @Override
                public String toString(){
                    return "Computer";
                }
            };
        }
        else{
            root = new FileSystemTreeNode(fsroot[0], null);
        }
    }
    /**Generates a new Tree Model listing all roots without selecting a
     */
    public FileSystemTreeModel(){
        readRoots();
    }
    /**Creates a new FileSystemModel with path as root node
     * @param path path to the root node
     */
    public FileSystemTreeModel(String path){
        root = new FileSystemTreeNode(new File(path), null);
    }
    /**Creates a new FileSystemModel with file as root node
     * @param file pointing to root node
     */
    public FileSystemTreeModel(File file){
        root = new FileSystemTreeNode(file, null);
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        return ((TreeNode) parent).getChildAt(index);
    }

    public int getChildCount(Object parent) {
        return ((TreeNode) parent).getChildCount();
    }

    public boolean isLeaf(Object node) {
        return ((TreeNode) node).isLeaf();
    }

    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    public int getIndexOfChild(Object parent, Object child) {
        return ((TreeNode) parent).getIndex((TreeNode) child);
    }

    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.remove(l);
    }
}
