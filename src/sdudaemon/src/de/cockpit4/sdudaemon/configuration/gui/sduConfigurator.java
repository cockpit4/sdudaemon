/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cockpit4.sdudaemon.configuration.gui;

import de.cockpit4.sdudaemon.configuration.gui.controller.ConfigurationController;
import de.cockpit4.sdudaemon.configuration.gui.model.ConfigurationModel;
import de.cockpit4.sdudaemon.configuration.gui.view.ConfigurationWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/** This is the sduDaemon starter class. If you call the main method,
 */
public class sduConfigurator {

    public static void main(String[] argV) {
	
	//GUI initialization
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception ex) {
	    Logger.getLogger(sduConfigurator.class.getName()).log(Level.SEVERE, null, ex);
	}

	String config;
	JFileChooser fc;

	if (argV.length > 0) {
	    config = argV[0];
	}else { //show file selection dialog
	    fc = new JFileChooser();

	    fc.setDialogType(JFileChooser.OPEN_DIALOG);



	    if (fc.showOpenDialog(fc) == JFileChooser.APPROVE_OPTION) {
		config = fc.getSelectedFile().getAbsolutePath();
	    } else {
		do {
		    config = JOptionPane.showInputDialog("Creating a new File Please Enter Desired Filename"); //create empty configuration if aborted
		} while (config == null);
	    }
	}

	ConfigurationModel m = new ConfigurationModel(config);
	ConfigurationController c = new ConfigurationController(m);

	final ConfigurationWindow mw = new ConfigurationWindow(c);


	java.awt.EventQueue.invokeLater(new Runnable() {

	    public void run() {
		mw.setVisible(true);
	    }
	});
    }
}
