/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.guifilemanager.view;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author kneo
 */
public class FileSystemTreeRenderer extends JComponent implements TreeCellRenderer{

    public FileSystemTreeRenderer(){
        
    }


    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        return this;
    }

}
