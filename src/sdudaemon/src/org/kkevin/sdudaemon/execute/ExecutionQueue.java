/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kkevin.sdudaemon.execute;

import bsh.EvalError;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import org.kkevin.sdudaemon.configuration.Configuration;
import org.kkevin.sdudaemon.configuration.RecyclerConfig;
import org.kkevin.sdudaemon.configuration.ScraperConfig;
import org.kkevin.sdudaemon.configuration.UpdaterConfig;
import org.kkevin.sdudaemon.threads.RecyclerThread;
import org.kkevin.sdudaemon.threads.ScraperThread;
import org.kkevin.sdudaemon.threads.UpdaterThread;

/** This class defines the execution queue of a scraping process
 * @category WebScraper
 * @package org.kkevin.sdudaemon
 * @subpackage org.kkevin.sdudaemon.execute
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
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


		for (ScraperConfig a : config.getScrapers()) {
			System.out.println("Adding scraper...");
			if (a.active && !a.finished && !a.error) {
				processes.offer(new ScraperThread(a));
			}
		}


		for (RecyclerConfig a : config.getRecyclers()) {
			System.out.println("Adding recycler...");

			if (a.active && !a.finished && !a.error) {
				processes.offer(new RecyclerThread(a));
			}
		}


		for (UpdaterConfig a : config.getUpdater()) {
			System.out.println("Adding updater...");
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

		interrupt();
	}

	/**Life cycle of the execution queue this function runs through the queue and waits for each Sub-Thread to terminate.
	 */
	@Override
	public void run() {
		while (!interrupted()) {
			if (!processes.isEmpty()) { // here is our Work if there is no work kill yourself
				try {
					Thread t = processes.peek(); //get the lastes thread

					t.start(); //start it
					t.join(); //wait for it to die

					//TODO : Dispatch configs and save them into the save file
					if (t instanceof ScraperThread) {
					}

					if (t instanceof RecyclerThread) {
					}

					if (t instanceof UpdaterThread) {
					}

					processes.poll();//make space for the next job...
				}
				catch (InterruptedException ex) {
					System.out.println(ex);
					interrupt();
				}
			}
			else {
				interrupt();
			}
		}
	}
}
