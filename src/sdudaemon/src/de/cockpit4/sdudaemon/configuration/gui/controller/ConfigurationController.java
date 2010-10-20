/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui.controller;

import de.cockpit4.sdudaemon.configuration.gui.model.ConfigurationModel;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import de.cockpit4.xmlmanipulator.XMLManipulator;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kneo
 */
public class ConfigurationController implements ModelChangeListener{
	private ConfigurationModel configModel;
	XMLManipulator xm;
	public ConfigurationController(ConfigurationModel m){
		if(m != null){
			configModel = m;
			load();
			configModel.addListener(this);
			
		}
	}

	public ConfigurationModel getModel(){
		return configModel;
	}

	private void load(){
		try{
			if(xm==null); //initialize a new XMLManipulator
				xm = new XMLManipulator(XMLManipulator.readFileAsString(configModel.getConfigPath()));

			configModel.setLoggerEnabled(Boolean.parseBoolean(xm.getXPathValue("/config/logging/@active")));
			configModel.setLoggerPath(xm.getXPathValue("/config/logging/@path"));
			configModel.setLibraryPath(xm.getXPathValue("/config/libraries/@path"));
			configModel.setStatePath(xm.getXPathValue("/config/statefiles/@path"));
			configModel.setTempPath(xm.getXPathValue("/config/dump/@path"));


			
		} catch (Exception ex) {
			Logger.getLogger(ConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void save(){
		try{
			if(xm==null); //initialize a new XMLManipulator if not existend
				xm = new XMLManipulator(XMLManipulator.readFileAsString(configModel.getConfigPath()));

				
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
