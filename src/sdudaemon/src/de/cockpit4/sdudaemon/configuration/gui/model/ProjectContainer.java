package de.cockpit4.sdudaemon.configuration.gui.model;

import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.Observable;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 */
public class ProjectContainer extends AbstractTableModel implements ModelChangeListener, Observable {
    ArrayList<Project> projectList = new ArrayList<Project>();
    ArrayList<ModelChangeListener> observers = new ArrayList<ModelChangeListener>();
    
    public final String[] TABLE_HEADER_NAMES = {"active","name","configuration path"};

    public ProjectContainer() {
    }

    public void addProject(Project newProject) {
	if (newProject != null) {
	    newProject.addListener(this);
	    projectList.add(newProject);
	} else {
	    throw new NullPointerException();
	}
    }

    public Project getProject(int index) {
	return projectList.get(index);
    }

    public int getSize() {
	return projectList.size();
    }

    public void onChange() {
	fireChangeEvent();
    }

    public void fireChangeEvent() {
	for (ModelChangeListener o : observers) {
	    o.onChange();
	}
    }

    public void addListener(ModelChangeListener listener) {
	observers.add(listener);
    }

    public void removeListener(ModelChangeListener listener) {
	observers.remove(listener);
    }

    public int getRowCount() {
	return this.getSize();
    }

    public int getColumnCount() {
	return 3;
    }

    @Override
    public boolean isCellEditable(int row,int col){
	return col==0;
    }

    @Override
    public String getColumnName(int col){
	return TABLE_HEADER_NAMES[col];
    }

    public Object getValueAt(int row, int col) {
	switch(col){
	    case 0:
		return Boolean.valueOf(getProject(row).isActive());
	    case 1:
		return getProject(row).getName();
	    case 2:
		return getProject(row).getPath();
	}
	return null;
    }
}