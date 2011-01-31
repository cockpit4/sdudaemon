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
package de.cockpit4.sdudaemon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Attribute;
import org.jdom.xpath.XPath;
import de.cockpit4.sdudaemon.configuration.Configuration;
import de.cockpit4.sdudaemon.execute.ExecutionQueue;
import de.cockpit4.xmlmanipulator.XMLManipulator;
import org.webharvest.definition.DefinitionResolver;

/**
 * This Class handles daemon initialization
 *
 * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
 * @version 1.0
 *
 *  Copyright (c) 2010 cockpit4 GmbH
 *  sdudeamon is released under the MIT license
 */
public class Daemon {

    private ArrayList<Configuration> projects;
    private ArrayList<ExecutionQueue> threads;
    public static Logger systemLogger = Logger.getLogger("SystemLogger");

    /**Initialize the Deamon and loades a configfile
     * after a configfile is loaded find the project directory and load all valid projects
     * Creates  an ExecutionQueue-Thread and prepares them for execution
     * Use start() to execute the ExecutiontionQueue engaging the scraping recycling and updating processes
     * @param configFile path to the main config XML file providing general application settings and paths to single projects.
     */
    public Daemon(String configFile) {
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

            DefinitionResolver.registerPlugin("de.cockpit4.wsplugin.DelayPlugin");
            DefinitionResolver.registerPlugin("de.cockpit4.xmlmanipulator.webharvest.XMLManipulatorPlugin");

            if (Boolean.parseBoolean(xm.getXPathValue("/config/logging/@active"))) {
                Logger.getLogger("SystemLogger").addHandler(new FileHandler(xm.getXPathValue("/config/logging/@path") + Calendar.getInstance().getTime().toString()));
            }

            outputPath = xm.getXPathValue("//dump/@path");
            statePath = xm.getXPathValue("//statefiles/@path");

            Logger.getLogger("SystemLogger").setFilter(null);
            Logger.getLogger("SystemLogger").setLevel(Level.ALL);
            Logger.getLogger("SystemLogger").config("logger successfully initialized!");
            List results = XPath.selectNodes(xm.getDocument(), "//projects/project/@path");
            Logger.getLogger("SystemLogger").log(Level.FINEST, "Projects found : {0}", results.size());
            for (Object c : results) {
                if (c instanceof Attribute) {
                    Logger.getLogger("SystemLogger").log(Level.INFO, "adding {0}", ((Attribute) c).getValue());
                    projects.add(new Configuration(((Attribute) c).getValue(), outputPath, statePath));
                }
            }
            Logger.getLogger("SystemLogger").log(Level.INFO, "adding project threads");
            for (Configuration p : projects) {
                threads.add(new ExecutionQueue(p));
            }


            //register the additional plugins used by this project
        } catch (Exception ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, "Fatal ERROR check configuration!\n {0}", ex);
        }

    }

    /**This function starts the main execution of all decided and specified projects, set the active flag to false in the project configuration file.
     */
    public void start() {
        Logger.getLogger("SystemLogger").log(Level.INFO, "starting thread queues...");
        for (ExecutionQueue q : threads) {
//            try {
                //q.start();
                //q.join(); //run just on project at a time
                q.run();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Daemon.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }

    /**This function prints out the help message giving a brief explanation in how to use this program
     */
    public static void showHint() {
        System.out.println("USAGE : " + Daemon.class.getCanonicalName() + " config background logging logfile");
        System.out.println(" config       path to config file (valid wellformed XML)");
    }

    /**This function starts this application
     */
    public static void main(String[] argV) {
        if (argV.length > 0) {
            Daemon d = new Daemon(argV[0]);
            d.start();
        } else {
            showHint();
        }
    }
}
