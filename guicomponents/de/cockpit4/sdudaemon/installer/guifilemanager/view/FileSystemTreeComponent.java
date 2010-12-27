/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.guifilemanager.view;

import de.cockpit4.sdudaemon.installer.guifilemanager.model.FileSystemTreeModel;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;

/**
 * @author kneo
 */
public class FileSystemTreeComponent extends JComponent{
    private FileSystemTreeModel model;
    private JPanel contentPane;
    private JTree folderTree;

    private void initComponents(){
        contentPane = new JPanel();
        folderTree = new JTree(model);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(BorderLayout.CENTER, folderTree);
        this.add(contentPane);
    }

    public FileSystemTreeComponent(){
        model = new FileSystemTreeModel();
        initComponents();
    }

    public FileSystemTreeComponent(FileSystemTreeModel model){
        this.model = model;
        initComponents();
    }
}
