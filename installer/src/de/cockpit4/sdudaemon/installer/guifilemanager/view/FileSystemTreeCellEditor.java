/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.guifilemanager.view;

import de.cockpit4.sdudaemon.installer.guifilemanager.model.FileSystemTreeNode;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.TreeCellEditor;

/**
 *
 * @author kneo
 */
public class FileSystemTreeCellEditor implements TreeCellEditor{
    private FileSystemTreeRendererCell comp = new FileSystemTreeRendererCell();
    private ArrayList<CellEditorListener> cellEditorListeners = new ArrayList<CellEditorListener>();
    private FileSystemTreeNode value;

    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        if(leaf){
            comp.setType(0);
            comp.setInfo(this.value.getFile().length()+" bytes");
        }
        else{
            comp.setType(1);
            comp.setInfo("contains "+this.value.getChildCount()+" Objects");
        }

        this.value = (FileSystemTreeNode) value;
        comp.setTitle(this.value.getFile().getName());

        return comp;
    }

    public Object getCellEditorValue() {
        return null;
    }

    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    public boolean stopCellEditing() {
        return true;
    }

    public void cancelCellEditing() {}

    public void addCellEditorListener(CellEditorListener l) {
        cellEditorListeners.add(l);
    }

    public void removeCellEditorListener(CellEditorListener l) {
        cellEditorListeners.remove(l);
    }

}
