/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.model.eventhandling;

import java.util.ArrayList;

/**
 *
 * @author kneo
 */
public abstract class AbstractObservableModel implements Observable{
	ArrayList<ModelChangeListener> listeners =  new ArrayList<ModelChangeListener>();
	public void fireChangeEvent() {
		for(ModelChangeListener i : listeners){
			i.onChange();
		}
	}

	public void addListener(ModelChangeListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ModelChangeListener listener) {
		listeners.remove(listener);
	}
}
