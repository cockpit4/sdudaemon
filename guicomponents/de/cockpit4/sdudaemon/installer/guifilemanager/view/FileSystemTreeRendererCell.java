/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.guifilemanager.view;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author kneo
 */
    public class FileSystemTreeRendererCell extends JPanel implements MouseListener{
        public static final int TYPE_FILE = 0;
        public static final int TYPE_FOLDER = 1;

        private JLabel icon;
        private JLabel title;
        private JLabel info;
        private JButton apply;
        private JButton mkdir;

        private Icon file;
        private Icon folder;
        private Icon accept;
        private Icon newFolder;


        public FileSystemTreeRendererCell(){
            this.setOpaque(false);
            this.addMouseListener(this);

            //load resources
            accept = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/res/images/apply.png")));
            folder = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/res/images/folder.png")));
            file = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/res/images/file.png")));
            newFolder =  new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/res/images/add.png")));
            icon = new JLabel(file);
            title = new JLabel("Text");
            info  = new JLabel("Extra info");
            info.setVisible(false);
            mkdir = new JButton(newFolder);
            mkdir.setToolTipText("create a new folder");
            mkdir.setVisible(false);
            apply = new JButton(accept);
            apply.setToolTipText("Apply this element as your choice.");
            apply.setVisible(false);



            this.add(icon);
            this.add(title);
            this.add(info);
            this.add(mkdir);
            this.add(apply);
        }

        public void setTitle(String title){
            this.title.setText(title);
        }

        public void setInfo(String info){
            this.info.setText(info);
        }

        public void setType(int type){
            switch(type){
                case 0:
                    icon.setIcon(file);
                    break;
                case 1:
                    icon.setIcon(folder);
                    break;
            }
        }

        public void setSelected(boolean sel){
            info.setVisible(sel);
            apply.setVisible(sel);
            mkdir.setVisible(sel);
        }

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {
            //TODO : show extra infomation and apply button on mouse over
            info.setVisible(true);
            apply.setVisible(true);
            update(this.getGraphics());
        }

        public void mouseExited(MouseEvent e) {
           //TODO : hide extra information and apply button on mouse exit
            info.setVisible(false);
            apply.setVisible(false);
            update(this.getGraphics());
        }

        public static void main(String[] argV){
            FileSystemTreeRendererCell t = new FileSystemTreeRendererCell();
        }

    }