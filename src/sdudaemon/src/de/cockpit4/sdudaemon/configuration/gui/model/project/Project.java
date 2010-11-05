/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.model.project;

import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.AbstractObservableModel;

/**
 */
public class Project extends AbstractObservableModel{
	private String name;
	private String path;
	private boolean active;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		fireChangeEvent();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		fireChangeEvent();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		fireChangeEvent();
	}
}
