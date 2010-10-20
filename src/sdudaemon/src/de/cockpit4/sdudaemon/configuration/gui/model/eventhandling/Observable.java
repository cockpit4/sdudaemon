/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.model.eventhandling;

/**
 *
 * @author kneo
 */
public interface Observable {
	public void fireChangeEvent(); //add a event class to resolve changes
	public void addListener(ModelChangeListener listener);
	public void removeListener(ModelChangeListener listener);
}
