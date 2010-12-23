/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.eventhandling;

import java.util.ArrayList;

/**
 *
 * @author kneo
 */
public class ChangeAdapter implements Observable {
    private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();
    private ArrayList<FinishedListener> flisteners = new ArrayList<FinishedListener>();
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    public void fireChangeEvent(ChangeEvent evt) {
        for(ChangeListener l : listeners){
            l.onChange(evt);
        }
    }

    public void addFinishListener(FinishedListener listener) {
        flisteners.add(listener);
    }

    public void removeFinishListener(FinishedListener listener) {
        flisteners.remove(listener);
    }

    public void fireFinishEvent() {
        for(FinishedListener l : flisteners){
            l.onFinish();
        }
    }
}
