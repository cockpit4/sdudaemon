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

package de.cockpit4.sdudaemon.threads;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;
import de.cockpit4.sdudaemon.configuration.UpdaterConfig;
import de.cockpit4.xmlmanipulator.XMLDatabaseLoader;

/**
 * This class handles project database and table operations
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class UpdaterThread extends Thread{
	private UpdaterConfig config;
	private XMLDatabaseLoader loader;
	/**Create a new updater Thread this loads the database driver and connects to the database
	 * afterwards the the thread closes the connection if it has finished execution
	 * @param conf updater config containing all necessary information in how to connect and where to store data assigned in the files defined by the path 
	 */
	public UpdaterThread(UpdaterConfig conf) throws ClassNotFoundException, IOException, Exception{
		this.config = conf;
                loader = new XMLDatabaseLoader(conf.host, conf.port, conf.user, conf.pass, conf.db, conf.databaseRepository);
	}
	/**running function, here the thread loads inserts and updated row in the table. If the table is nonexistent this function creates a new based on the data found in the path, the recycler used to store its output.
	 */
	@Override
	public void run(){
		try {
                        Logger.getLogger("SystemLogger").log(Level.INFO, "Loading new Table");
			loader.load();
                        Logger.getLogger("SystemLogger").log(Level.FINEST, "Table successfully loaded! Database now up to date!");
		}
		catch (JDOMException ex) {
			Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
		}
		catch (SQLException ex) {
			Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
		}
	}
	/**returns the configuration of the current
	 */
	public UpdaterConfig getConfig(){
		return config;
	}
}
