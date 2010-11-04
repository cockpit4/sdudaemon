/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.model.eventhandling;

/**To watch part of the observer actor model
 */
public interface Observable {

	/**This method is the callback to notify all registered observers about a change made to an model object, what ever ...
	 */
	public void fireChangeEvent(); //add a event class to resolve changes
	/**Adds a listener to the notify list
	 * @param listener
	 */
	public void addListener(ModelChangeListener listener);
	/** Removes a listener from the notify list
	 * @param listener
	 */
	public void removeListener(ModelChangeListener listener);
}
