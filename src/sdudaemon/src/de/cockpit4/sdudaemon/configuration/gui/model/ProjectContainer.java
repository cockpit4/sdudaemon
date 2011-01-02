/*
Copyright (c) 2010 cockpit4, Kevin Krüger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
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
    
    public final String[] TABLE_HEADER_NAMES = {"Active","Name","Configuration Path"};

    public ProjectContainer() {
    }

    public void addProject(Project newProject) {
	if (newProject != null) {
	    newProject.addListener(this);
	    projectList.add(newProject);
            fireChangeEvent();
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
        fireTableStructureChanged();
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
    public Class getColumnClass(int columnIndex){
	switch(columnIndex){
	    case 0:
		return Boolean.class;
	    case 1:
	    case 2:
		return String.class;
	    default:
		return null;
	}
    }

    @Override
    public boolean isCellEditable(int row,int col){
	return col==0;
    }

    @Override
    public String getColumnName(int col){
	return TABLE_HEADER_NAMES[col];
    }

    @Override
    public void setValueAt(Object o, int row, int col){
	if(isCellEditable(row, col)){
	    switch(col){
		case 0:
		    getProject(row).setActive((Boolean)o);
		break;
		case 1://not implemented
		break;
		case 2:
		break;
	    }
	}
    }

    public ArrayList<Project> getProjects(){
	return projectList;
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