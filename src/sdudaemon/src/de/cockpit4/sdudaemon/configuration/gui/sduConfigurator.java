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
package de.cockpit4.sdudaemon.configuration.gui;

import de.cockpit4.sdudaemon.configuration.gui.controller.ConfigurationController;
import de.cockpit4.sdudaemon.configuration.gui.model.ConfigurationModel;
import de.cockpit4.sdudaemon.configuration.gui.view.ConfigurationWindow;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/** This is the sduDaemon starter class. If you call the main method, a file chooser is created which asks the user for a proper xml configuration file.
 * If the user hits cancel the application tries to create a new empty file at specified folder asking the user how to name it.
 * The application is terminated with -1 if the user cancels this option too as well as if he has no permission to read or write files.
 * Also you can add a startup parameter containing a valid path to the file to load.
 */
public class sduConfigurator {

    public static void main(String[] argV) {

	//GUI initialization
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception ex) {
	    Logger.getLogger(sduConfigurator.class.getName()).log(Level.SEVERE, null, ex);
	}

	String config = null; //configuration file
	JFileChooser fc = null; //filechooser object

	if (argV.length > 0) {
	    config = argV[0];
	} else { //show file selection dialog
	    fc = new JFileChooser();

	    fc.setDialogType(JFileChooser.OPEN_DIALOG); //generate a new file chooser

	    if (fc.showOpenDialog(fc) == JFileChooser.APPROVE_OPTION) { // what did the user do
		config = fc.getSelectedFile().getAbsolutePath();
	    } else {
		do {
		    config = fc.getCurrentDirectory().getAbsolutePath() + File.separator;
		    String check = JOptionPane.showInputDialog("Creating a new File Please Enter Desired Filename"); //create empty configuration if aborted
                    System.err.println("Check : "+check);
		    if (check != null && check.length() > 0 && !check.equals("")) {
			config += check;
		    } else {
			System.err.println("no file name entered! exiting...");
			System.exit(-1);
		    }

		} while (config == null);
	    }
	}

	File configFile = new File(config);//create a new file if not existent
        if(!configFile.exists()){
            try {
                FileWriter fw = new FileWriter(configFile);
                fw.write("");
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(sduConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

	if (!configFile.canRead() || !configFile.canWrite()) { // kill application if user has no right to read or write files to specified location
	    System.err.println("no permission to read or write specified file! exiting...");
	    System.exit(-1);
	}


	ConfigurationModel m = new ConfigurationModel(config); //create new model
	ConfigurationController c = new ConfigurationController(m); // create new controller

	final ConfigurationWindow mw = new ConfigurationWindow(c); // create GUI Window and assign the controller


	java.awt.EventQueue.invokeLater(new Runnable() {

	    public void run() {
		mw.setVisible(true);
	    }
	});
    }
}
