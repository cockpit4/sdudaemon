/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.tool;

import java.io.File;

/**
 *
 * @author kneo
 */
public class SortableFile implements Sortable{
    private File data;
    public static int sortMode = 0;
    public SortableFile(File f){
        data = f;
    }

    public File getFile(){
        return this.data;
    }

    public void setFile(File f){
        this.data = f;
    }

    public boolean equalTo(Sortable t) {
        File com = ((SortableFile)t).getFile();

        switch(sortMode){
            case 0:
                return data.getName().equals(com.getName());

            case 1:
                return data.lastModified() == com.lastModified();

            case 2:
                return data.length() == com.length();
            default:
                return false;
        }
    }

    public boolean inequalTo(Sortable t) {
         File com = ((SortableFile)t).getFile();

        switch(sortMode){
            case 0:
                return !data.getName().equals(com.getName());

            case 1:
                return data.lastModified() != com.lastModified();

            case 2:
                return data.length() != com.length();
            default:
                return false;
        }
    }

    public boolean lesserThan(Sortable t) {
        File com = ((SortableFile)t).getFile();

        switch(sortMode){
            case 0:
                return data.getName().compareTo(com.getName())<0;

            case 1:
                return data.lastModified() < com.lastModified();

            case 2:
                return data.length() < com.length();
            default:
                return false;
        }
    }

    public boolean greaterThan(Sortable t) {
        File com = ((SortableFile)t).getFile();

        switch(sortMode){
            case 0:
                return data.getName().compareTo(com.getName())>0;

            case 1:
                return data.lastModified() > com.lastModified();

            case 2:
                return data.length() > com.length();
            default:
                return false;
        }
    }

    public boolean lesserOrEqualTo(Sortable t) {
        File com = ((SortableFile)t).getFile();

        switch(sortMode){
            case 0:
                return lesserThan(t)||equalTo(t);

            case 1:
                return data.lastModified() <= com.lastModified();

            case 2:
                return data.length() <= com.length();
            default:
                return false;
        }
    }

    public boolean greaterOrEqualTo(Sortable t) {
        File com = ((SortableFile)t).getFile();

        switch(sortMode){
            case 0:
                return greaterThan(t)||equalTo(t);

            case 1:
                return data.lastModified() >= com.lastModified();

            case 2:
                return data.length() >= com.length();
            default:
                return false;
        }
    }

    public void swap(Sortable t) {
        SortableFile com = ((SortableFile) t);
        File tmp = com.getFile();
        com.setFile(data);
        data=tmp;
    }
}

