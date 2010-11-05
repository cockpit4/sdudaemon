/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.model.project;

import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.AbstractObservableModel;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import java.util.ArrayList;

/**
 *
 * @author kneo
 */
public class ProjectContainer extends AbstractObservableModel implements ModelChangeListener{
	ArrayList<Project> projectList = new ArrayList<Project>();
	
	public ProjectContainer(){
		
	}
	
	public void addProject(Project newProject){
		if(newProject!= null){
			newProject.addListener(this);
			projectList.add(newProject);
		}
		else
			throw new NullPointerException();
	}
	
	public Project getProject(int index){
		return null;
	}

	public void onChange() {
		fireChangeEvent();
	}

}
