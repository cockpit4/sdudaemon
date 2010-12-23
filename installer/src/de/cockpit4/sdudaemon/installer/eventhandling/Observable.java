/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer.eventhandling;

/**
 *
 * @author kneo
 */
public interface Observable {
    public void addFinishListener(FinishedListener listener);
    public void removeFinishListener(FinishedListener listener);
    public void addChangeListener(ChangeListener listener);
    public void removeChangeListener(ChangeListener listener);
    public void fireChangeEvent(ChangeEvent evt);
    public void fireFinishEvent();
}
