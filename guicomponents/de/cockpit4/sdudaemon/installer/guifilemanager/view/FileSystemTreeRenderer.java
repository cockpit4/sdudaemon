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

package de.cockpit4.sdudaemon.installer.guifilemanager.view;

import de.cockpit4.sdudaemon.installer.guifilemanager.model.FileSystemTreeNode;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author kneo
 */
public class FileSystemTreeRenderer extends JComponent implements TreeCellRenderer{
    private FileSystemTreeRendererCell comp = new FileSystemTreeRendererCell();
    public FileSystemTreeRenderer(){
        
    }
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        FileSystemTreeNode t = (FileSystemTreeNode) value;
        if(leaf){
            comp.setType(FileSystemTreeRendererCell.TYPE_FILE);
            comp.setTitle(t.getFile().getName());
            comp.setInfo(t.getFile().length()+" bytes");
            comp.setSelected(selected);
        }
        else{
            comp.setType(FileSystemTreeRendererCell.TYPE_FOLDER);
            comp.setTitle(t.getFile().getName());
            comp.setInfo("Contains "+t.getChildCount()+" Objects");
            comp.setSelected(selected);
        }
        return comp;
    }

    public static void main(String[] argV){
        
        
    }
}
