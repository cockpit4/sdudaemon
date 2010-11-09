/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cockpit4.sdudaemon.configuration.gui.controller;

import de.cockpit4.sdudaemon.configuration.gui.model.ConfigurationModel;
import de.cockpit4.sdudaemon.configuration.gui.model.Project;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import de.cockpit4.xmlmanipulator.XMLManipulator;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.xpath.XPath;

/** This class handles the general project configuration. The ConfigurationController updates the configuration file
 * if the user changes a value in one of the several input fields
 */
public class ConfigurationController implements ModelChangeListener {

    private ConfigurationModel configModel;
    XMLManipulator xm;

    public ConfigurationController(ConfigurationModel m) {
	if (m != null) {
	    configModel = m;
	    load();
	    configModel.addListener(this);
	}
    }
    /**This method returns the the configuration model assigned to this controller. @see ConfigurationModel
     * @return ConfigurationModel of this controller
     */
    public ConfigurationModel getModel() {
	return configModel;
    }

    private void load() {
	try {
	    if (xm == null) //initialize a new XMLManipulator
		xm = new XMLManipulator(XMLManipulator.readFileAsString(configModel.getConfigPath())); //load file if exists

	    try { // try to retrieve the root node of the configfile if its not found, the file is ussualy empty so generate a new file
		xm.getXPathValue("/config");//probe if node root exists
	    } catch (IllegalStateException e) {
		xm.setXMLContent("<config/>"); //create a new empty configuration
		xm.addXPathNode("/config/logging[@active=\'false\']/@path","");
		xm.addXPathNode("/config/libraries/@path","");
		xm.addXPathNode("/config/statefiles/@path","");
		xm.addXPathNode("/config/dump/@path","");
		//System.err.println(xm);
	    } finally { //after the file is loaded or created load all values into the model
		configModel.setLoggerEnabled(Boolean.parseBoolean(xm.getXPathValue("/config/logging/@active")));
		configModel.setLoggerPath(xm.getXPathValue("/config/logging/@path"));
		configModel.setLibraryPath(xm.getXPathValue("/config/libraries/@path"));
		configModel.setStatePath(xm.getXPathValue("/config/statefiles/@path"));
		configModel.setTempPath(xm.getXPathValue("/config/dump/@path"));

		File configDir = new File(configModel.getLibraryPath()); //get the library path to the required jar-files

		File[] list = configDir.listFiles();
		//TODO : verify if the files are actual jar libraries instead of matching file name patterns
		for(File f : list){ // this block retrives all files listed in the lib directory and matches its name patterns to perdefined regexps
			byte foundlib = configModel.getFoundLibraries();
			//System.err.print("filename : "+f.getName());
			if(f.getName().split("[-]")[0].matches("jaxen[.]*")){
				foundlib |= 1;
				//System.err.println(" matched!");
			}
			if(f.getName().split("[-]")[0].matches("bsh[.]*")){
				foundlib |= 2;
				//System.err.println(" matched!");
			}
			if(f.getName().split("[.-]")[0].matches("jdom[.]*")){
				foundlib |= 4;
				//System.err.println(" matched!");
			}
			if(f.getName().split("[-]")[0].matches("postgresql[.]*")){
				foundlib |= 8;
				//System.err.println(" matched!");
			}
			if(f.getName().split("[_-]")[0].matches("webharvest[.]*")){
				foundlib |= 16;
				//System.err.println(" matched!");
			}
			configModel.setFoundLibraries(foundlib);
		}

		List project = XPath.selectNodes(xm.getDocument(), "//project");


		for(Object node : project){
		    String name = ((Element)node).getAttributeValue("name");
		    boolean active = Boolean.parseBoolean(((Element)node).getAttributeValue("active"));
		    String path = ((Element)node).getAttributeValue("path");
		    
		    Project newPr = new Project(active,name,path);
		    configModel.getProjects().addProject(newPr);
		}
		//System.err.println("Libs :"+configModel.getFoundLibraries());

	    }

	} catch (Exception ex) {
	    Logger.getLogger(ConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private void save() {
	try {
	    if (xm == null) //initialize a new XMLManipulator if not existend
		 xm = new XMLManipulator(XMLManipulator.readFileAsString(configModel.getConfigPath()));
	    //System.err.println(xm);

	    xm.setXPathValue("/config/logging/@active", Boolean.toString(configModel.isLoggerEnabled()));
	    xm.setXPathValue("/config/logging/@path", configModel.getLoggerPath());
	    xm.setXPathValue("/config/libraries/@path", configModel.getLibraryPath());
	    xm.setXPathValue("/config/statefiles/@path", configModel.getStatePath());
	    xm.setXPathValue("/config/dump/@path", configModel.getTempPath());

	    for(Project p : getModel().getProjects().getProjects()){
		//TODO: save general project data as well
	    }


	    //write the config file
	    FileWriter fw = new FileWriter(configModel.getConfigPath());
	    fw.write(xm.toString());
	    fw.close();

	} catch (Exception ex) {
	    Logger.getLogger(ConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    /** This method is the implementation of the @see ModelChangeListener. On change the model is stored in the assigned xml file.
     */
    public void onChange() {
	//the model changed some values act here
	save();
    }
}
