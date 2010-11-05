/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.model;

import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.AbstractObservableModel;

/**This class describes the link to a scraping project. In order to implement the actor observer model it implements the AbstractObservableModel to notify listeners about data changes.
 */
public class Project extends AbstractObservableModel{
	private String name;
	private String path;
	private boolean active;
	/**Creates a new project node.
	 * @param act set the state of a project (en- or disabled)
	 * @param name set the name of the project required for debug and information purposes
	 * @param path set the path to the scraping project files are stored.
	 */
	public Project(boolean act, String name, String path){
	    this.active =act;
	    this.name = name;
	    this.path = path;
	}
	/**Returns the name of the project
	 * @return name of the Project
	 */
	public String getName() {
		return name;
	}
	/** Sets the name of the project
	 * @param name of the project
	 */
	public void setName(String name) {
		this.name = name;
		fireChangeEvent();
	}
	/**Returns the path where the project related files are stored
	 * @return path to project files
	 */
	public String getPath() {
		return path;
	}
	/**Set the Path to the project related configuration files
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
		fireChangeEvent();
	}
	/**Tells if a project is active
	 * @return true if the project is active, false otherwise
	 */
	public boolean isActive() {
		return active;
	}
	/**Set the state of a project. Fires onChangeEvent on change.
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
		fireChangeEvent();
	}
}
