/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kkevin.sdudaemon;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Attribute;
import org.jdom.xpath.XPath;
import org.kkevin.sdudaemon.configuration.Configuration;
import org.kkevin.sdudaemon.execute.ExecutionQueue;
import org.kkevin.xmlmanipulator.XMLManipulator;
import org.webharvest.definition.DefinitionResolver;

/**
 * This Class handles daemon initialization
 * @category WebScraper
 * @package org.kkevin.sdudaemon
 * @subpackage org.kkevin.sdudaemon.threads
 * @author Kevin Krüger
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
 */
public class Daemon {
	private ArrayList<Configuration> projects;
	private ArrayList<ExecutionQueue> threads;

	/**Initialize the Deamon and loades a configfile
	 * after a configfile is loaded find the project directory and load all valid projects
	 * Creates  an ExecutionQueue-Thread and prepares them for execution
	 * Use start() to execute the ExecutiontionQueue engaging the scraping recycling and updating processes
	 * @param configFile path to the main config XML file providing general application settings and paths to single projects.
	 */
	public Daemon(String configFile){
		try {
			//1 . load config file X
			//2 . configurate daemon X
			//3 . load project configurations X
			//4 . create Execution Pipes
			//5 . create Execution Pipe Executors
			//6 . execute pipes executors

			String outputPath;
			String statePath;
			threads = new ArrayList<ExecutionQueue>();
			XMLManipulator xm = new XMLManipulator(XMLManipulator.readFileAsString(configFile));

			projects = new ArrayList<Configuration>();

	 		DefinitionResolver.registerPlugin("org.kkevin.wsplugin.DelayPlugin");
			DefinitionResolver.registerPlugin("org.kkevin.xmlmanipulator.XMLManipulatorPlugin");

			if(Boolean.parseBoolean(xm.getXPathValue("/config/logging/@active"))){
				Logger.getLogger("SystemLogger").addHandler(new FileHandler(xm.getXPathValue("/config/logging/@path")+Calendar.getInstance().getTime().toString()));
			}

			outputPath = xm.getXPathValue("//dump/@path");
			statePath = xm.getXPathValue("//statefiles/@path");

			Logger.getLogger("SystemLogger").setFilter(null);
			Logger.getLogger("SystemLogger").setLevel(Level.ALL);
			Logger.getLogger("SystemLogger").config("logger successfully initialized!");

			List results = XPath.selectNodes(xm.getDocument(), "//projects/project/@path");
			Logger.getLogger("SystemLogger").log(Level.CONFIG, "Projects found : {0}", results.size());
			for(Object c : results){
				if(c instanceof Attribute){
					projects.add(new Configuration(((Attribute) c).getValue(),outputPath,statePath));
				}
			}

			
			for(Configuration p : projects){
				threads.add(new ExecutionQueue(p));
			}


			//register the additional plugins used by this project
		}
		catch (Exception ex) {
			System.err.println("Fatal ERROR check configuration!\n"+ex);
			ex.printStackTrace();
		}
		
	}
	/**This function starts the main execution of all decided and specified projects, set the active flag to false in the project configuration file.
	 */
	public void start(){
		for(ExecutionQueue q : threads){
			System.out.println("Thread "+q.toString());
			q.start();
		}
	}


	/**This function prints out the help message giving a brief explanation in how to use this program
	 */
	public static void showHint(){
		System.out.println("USAGE : "+Daemon.class.getCanonicalName()+" config background logging logfile");
		System.out.println(" config       path to config file (valid wellformed XML)");
	}

	/**This function starts this application
	 */
	public static void main(String[] argV){
		if(argV.length>0){
			Daemon d = new Daemon(argV[0]);
			d.start();
		}
		else{
			showHint();
		}
	}
}
