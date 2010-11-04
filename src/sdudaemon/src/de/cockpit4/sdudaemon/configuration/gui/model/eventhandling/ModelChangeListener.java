/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.model.eventhandling;

/** This is a simple Interface to implement the Actor Observer Model
 */
public interface ModelChangeListener {
	/**Override this method to code you want to execute if your observer gets notified about a change in a some data structure.
	 */
	public void onChange();
}
