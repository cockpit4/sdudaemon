/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.configuration.gui;

import de.cockpit4.sdudaemon.configuration.gui.controller.ConfigurationController;
import de.cockpit4.sdudaemon.configuration.gui.model.ConfigurationModel;
import de.cockpit4.sdudaemon.configuration.gui.view.ConfigurationWindow;
import javax.swing.JFileChooser;

/**
 *
 * @author kneo
 */
public class sduConfigurator {
	public static void main(String[] argV){

		String config;
		JFileChooser fc;
		if(argV.length > 0){
			config = argV[0];
		}
		else{ //show file selection dialog
			fc = new JFileChooser();
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			

			if(fc.showOpenDialog(fc) == JFileChooser.APPROVE_OPTION){
				config = fc.getSelectedFile().getAbsolutePath();
			}
			else{
				config = null; //create empty configuration if aborted
			}
		}
		ConfigurationModel      m = new ConfigurationModel(config);
		ConfigurationController c = new ConfigurationController(m);

		final ConfigurationWindow mw = new ConfigurationWindow(c);


		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
			mw.setVisible(true);
		    }
		});
	}
}
