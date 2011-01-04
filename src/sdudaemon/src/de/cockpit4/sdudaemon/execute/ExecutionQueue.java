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

package de.cockpit4.sdudaemon.execute;

import bsh.EvalError;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import de.cockpit4.sdudaemon.configuration.Configuration;
import de.cockpit4.sdudaemon.configuration.RecyclerConfig;
import de.cockpit4.sdudaemon.configuration.ScraperConfig;
import de.cockpit4.sdudaemon.configuration.UpdaterConfig;
import de.cockpit4.sdudaemon.threads.RecyclerThread;
import de.cockpit4.sdudaemon.threads.ScraperThread;
import de.cockpit4.sdudaemon.threads.UpdaterThread;
import java.util.logging.Level;
import java.util.logging.Logger;

/** This class defines the execution queue of a scraping process
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class ExecutionQueue extends Thread {

	private LinkedList<Thread> processes;
	private Configuration config;

	/**This class handles the execution behavor or a sdu project. Each task is add to a queue after the termination of a task the next job will comes up.
	 * @param config the config of a project
	 */
	public ExecutionQueue(Configuration config) throws EvalError, FileNotFoundException, ClassNotFoundException, IOException, Exception {
		//create execution queue
		processes = new LinkedList<Thread>();

		this.config = config;
                Logger.getLogger("SystemLogger").log(Level.INFO, "initializing Execution Queue : {0}", config.getProjectName());
		for (ScraperConfig a : config.getScrapers()) {
                        Logger.getLogger("SystemLogger").log(Level.INFO, "Adding scraper...");
			if (a.active && !a.finished && !a.error) {
				processes.offer(new ScraperThread(a));
			}
		}


		for (RecyclerConfig a : config.getRecyclers()) {
			Logger.getLogger("SystemLogger").log(Level.INFO, "Adding recycler...");

			if (a.active && !a.finished && !a.error) {
				processes.offer(new RecyclerThread(a));
			}
		}

		for (UpdaterConfig a : config.getUpdater()) {
			Logger.getLogger("SystemLogger").log(Level.INFO, "Adding updater...");
			if (a.active && !a.finished && !a.error) {
				processes.offer(new UpdaterThread(a));
			}
		}
	}

	/**Each thread is designed to handle interrupt signals asap. if you execute this function each Thread makes sure that all data will be stored so no data lost would occur.
	 */
	public void killQueue() {
		//kills the ExecutionQueue

		processes.peek().interrupt();
                Logger.getLogger("SystemLogger").log(Level.WARNING, "Current Process interrupted!");
		interrupt();
                Logger.getLogger("SystemLogger").log(Level.WARNING,"Execution Queue interrupted!");
	}

	/**Life cycle of the execution queue this function runs through the queue and waits for each Sub-Thread to terminate.
	 */
	@Override
	public void run() {
                Logger.getLogger("SystemLogger").log(Level.INFO, "Execution Queue \"{0}\" running!", config.getProjectName());
		while (!interrupted()) {
			if (!processes.isEmpty()) { // here is our Work if there is no work kill yourself
				try {
					Thread t = processes.peek(); //get the lastes thread
					if (t instanceof ScraperThread) {
                                            Logger.getLogger("SystemLogger").log(Level.FINE, "Scraping process started!");
					}

					if (t instanceof RecyclerThread) {
                                            Logger.getLogger("SystemLogger").log(Level.FINE, "Recycling process started!");
					}

					if (t instanceof UpdaterThread) {
                                            Logger.getLogger("SystemLogger").log(Level.FINE, "Updating process started!");
					}

					t.start(); //start it
					t.join(); //wait for it to die

					//TODO :(Minor) Dispatch configs and save them into the save file
					if (t instanceof ScraperThread) {
                                            Logger.getLogger("SystemLogger").log(Level.FINER, "Scraping process started!");
					}

					if (t instanceof RecyclerThread) {
                                            Logger.getLogger("SystemLogger").log(Level.FINER, "Recycling process started!");
					}

					if (t instanceof UpdaterThread) {
                                            Logger.getLogger("SystemLogger").log(Level.FINER, "Updating process started!");
					}

					processes.poll();//make space for the next job...
				}
				catch (InterruptedException ex) {
                                        Logger.getLogger("SystemLogger").log(Level.SEVERE, "Current Process interrupted!");
					System.out.println(ex);
					interrupt();
                                        return;
				}
			}
			else {
                                Logger.getLogger("SystemLogger").log(Level.FINEST, "Execution Queue {0} successfully.",config.getProjectName());
				interrupt();
                                return;
			}
		}
                
	}
}
