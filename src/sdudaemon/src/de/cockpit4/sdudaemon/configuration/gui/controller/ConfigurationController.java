/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cockpit4.sdudaemon.configuration.gui.controller;

import de.cockpit4.sdudaemon.configuration.gui.model.ConfigurationModel;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import de.cockpit4.xmlmanipulator.XMLManipulator;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kneo
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

    public ConfigurationModel getModel() {
	return configModel;
    }

    private void load() {
	try {
	    if (xm == null) //initialize a new XMLManipulator
		xm = new XMLManipulator(XMLManipulator.readFileAsString(configModel.getConfigPath()));

	    try {
		xm.getXPathValue("/config");//probe if node root exists
	    } catch (IllegalStateException e) {
		xm.setXMLContent("<config/>"); //create a new empty configuration
		xm.addXPathNode("/config/logging[@active=\'false\']/@path","");
		xm.addXPathNode("/config/libraries/@path","");
		xm.addXPathNode("/config/statefiles/@path","");
		xm.addXPathNode("/config/dump/@path","");
		//System.err.println(xm);
	    } finally {
		configModel.setLoggerEnabled(Boolean.parseBoolean(xm.getXPathValue("/config/logging/@active")));
		configModel.setLoggerPath(xm.getXPathValue("/config/logging/@path"));
		configModel.setLibraryPath(xm.getXPathValue("/config/libraries/@path"));
		configModel.setStatePath(xm.getXPathValue("/config/statefiles/@path"));
		configModel.setTempPath(xm.getXPathValue("/config/dump/@path"));

		File configDir = new File(configModel.getLibraryPath());

		File[] list = configDir.listFiles();
		
		for(File f : list){
			byte foundlib = configModel.getFoundLibraries();
			System.err.print("filename : "+f.getName());
			if(f.getName().split("[-]")[0].matches("jaxen[.]*")){
				foundlib |= 1;
				System.err.println(" matched!");
			}
			if(f.getName().split("[-]")[0].matches("bsh[.]*")){
				foundlib |= 2;
				System.err.println(" matched!");
			}
			if(f.getName().split("[.-]")[0].matches("jdom[.]*")){
				foundlib |= 4;
				System.err.println(" matched!");
			}
			if(f.getName().split("[-]")[0].matches("postgresql[.]*")){
				foundlib |= 8;
				System.err.println(" matched!");
			}
			if(f.getName().split("[_-]")[0].matches("webharvest[.]*")){
				foundlib |= 16;
				System.err.println(" matched!");
			}
			configModel.setFoundLibraries(foundlib);
		}

		System.err.println("Libs :"+configModel.getFoundLibraries());

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
	    //write the config file
	    FileWriter fw = new FileWriter(configModel.getConfigPath());
	    fw.write(xm.toString());
	    fw.close();

	} catch (Exception ex) {
	    Logger.getLogger(ConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public void onChange() {
	//the model changed some values act here
	save();
    }
}
