/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.model.eventhandling;

import java.util.ArrayList;

/**Abstract class implementing the Observable interface to avoid some work adding, removing and event firing is already defined.
 * Extend your model classes from this class to implement the actor observer pattern in no time
 */
public abstract class AbstractObservableModel implements Observable{
	ArrayList<ModelChangeListener> listeners =  new ArrayList<ModelChangeListener>();


	/**This method is the callback to notify all registered observers about a change made to an model object, what ever ...
	 */
	public void fireChangeEvent() {
		for(ModelChangeListener i : listeners){
			i.onChange();
		}
	}
	/**Adds a listener to the notify list
	 * @param listener
	 */
	public void addListener(ModelChangeListener listener) {
		listeners.add(listener);
	}
	/** Removes a listener from the notify list
	 * @param listener
	 */
	public void removeListener(ModelChangeListener listener) {
		listeners.remove(listener);
	}
}
