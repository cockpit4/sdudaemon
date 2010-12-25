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
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

/**Class representing a single file or directory for the tree view
 * @author Kevin Krüger
 */
    public class FileSystemTreeNode implements TreeNode{
        private File node;
        private FileSystemTreeNode parent;
        private FileSystemTreeNode[] children;

        /** Create a new FileSystemTreeNode
         * @param node File Object to assign to this FileSystemNode
         * @param parent parent of this node;
         */
        public FileSystemTreeNode(File node,FileSystemTreeNode parent){
            this.node = node;
            this.parent = parent;
            //children = node.listFiles();
        }
        /**reads the children of a Directory
         * Necessary to avoid load of the whole Filesystem tree
         */
        private void readChildren(){
            if(children == null && node.isDirectory()){
                File[] files = node.listFiles();
                children = new FileSystemTreeNode[files.length];

                for(int i = 0; i<files.length;i++){
                    children[i] = new FileSystemTreeNode(files[i],this);
                }
            }
        }
        /**Returns the nth child of a directory which is either a file or a directory
         * @param childIndex index of the child
         * @return child on position childindex null if there is no child because the selected node is a file
         */
        public TreeNode getChildAt(int childIndex) {
            readChildren(); // initialize children
            if(node.isDirectory())
                if(childIndex < children.length && childIndex > -1)
                    return children[childIndex];
                else
                    throw new ArrayIndexOutOfBoundsException("no such child!");
            else
                return null;
        }
        /**Returns the count of available children
         * @return count of childen nodes
         */
        public int getChildCount() {
            readChildren(); // initialize children
            return children.length;
        }
        /**returns the parent of this node
         * @return parent of this node
         */
        public TreeNode getParent() {
            return parent;
        }
        /**Returns the index of a node
         * @param node to return its index
         * @return The index of the node. -1 if node is not in the children list
         */
        public int getIndex(TreeNode node) {
            readChildren();

            FileSystemTreeNode n = (FileSystemTreeNode) node;
            for(int i = 0; i<children.length;i++){
                if(children[i].getFile().equals(n.getFile())){
                    return i;
                }
            }
            return -1;
        }
        /**Determines if node is a directory.
         * @return true if assigned File is a directory
         */
        public boolean getAllowsChildren() {
            return node.isDirectory();
        }
        /**Determines if node is a file
         * @return true if node is a file
         */
        public boolean isLeaf() {
            return node.isFile();
        }
        /**
         * @return FileSystemTreeNode Enumeration
         */
        public Enumeration children() {
            readChildren(); // initialize children
            return (new Enumeration(){
                private int counter = 0;

                public boolean hasMoreElements() {
                    return counter < children.length;
                }

                public Object nextElement() {
                    return children[counter++];
                }
            });
        }
        /**Returns the file describing this tree node
         * @return file mapped to this node
         */
        public File getFile(){
            return node;
        }
        /**(no doc)
         * @see java.lang.Object
         */
        @Override
        public String toString(){
            return node.getName();
        }
    }
