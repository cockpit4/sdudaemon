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
public class FileSorter {

    public static final int MODE_NAME = 0;
    public static final int MODE_TIME = 1;
    public static final int MODE_SIZE = 2;
    private SortableFile[] files;
    private int sortMode = 0;

    public FileSorter(File[] files) {
        this.files = new SortableFile[files.length];
        for (int i = 0; i < files.length; i++) {
            this.files[i] = new SortableFile(files[i]);
        }
    }

    private File[] getFilesArray(){
        File[] res = new File[files.length];

        for(int i = 0;i<files.length;i++){
            res[i] = files[i].getFile();
        }
        return res;
    }

    private static void quicksort(Sortable[] numbers, int low, int high) {
        int i = low, j = high;
        Sortable pivot = numbers[(low + high) / 2];

        while (i <= j) {

            while (numbers[i].lesserThan(pivot)) {
                i++;
            }

            while (numbers[j].greaterThan(pivot)) {
                j--;
            }

            if (i <= j) {
                numbers[i].swap(numbers[j]);
                i++;
                j--;
            }
        }

        if (low < j) {
            quicksort(numbers, low, j);
        }
        if (i < high) {
            quicksort(numbers, i, high);
        }
    }

    public File[] getFiles() {
        return getFilesArray();
    }

    public File[] byName() {
        SortableFile.sortMode = 0;
        quicksort(files, 0, files.length-1);
        return getFiles();
    }

    public File[] byDate() {
        SortableFile.sortMode = 1;
        quicksort(files, 0, files.length-1);
        return getFiles();
    }

    public File[] bySize(){
        SortableFile.sortMode = 2;
        quicksort(files, 0, files.length-1);
        return getFiles();
    }

//    public static void main(String[] argV){
//        FileSorter f = new FileSorter(new File("/home/kneo/System/sdudaemon/tmp/library-wildau-new-publications").listFiles());
//
//        File[] sorted = f.byDate();
//
//        for(File o : sorted){
//            System.out.println("File : "+o.getName()+ " " + o.lastModified());
//        }
//    }
}
