/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kkevin.sdudaemon.threads;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;
import org.kkevin.sdudaemon.configuration.UpdaterConfig;
import org.kkevin.xmlmanipulator.XMLDatabaseLoader;
import org.kkevin.xmlmanipulator.XMLDatabaseTable;

/**
 * This class handles project database and table operations
 * @category
 * @package
 * @subpackage
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
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

		if(XMLDatabaseTable.tableExists(conf.inputFile)){
			loader = new XMLDatabaseLoader(conf.host, conf.port,conf.user,conf.pass,new XMLDatabaseTable(conf.inputFile));
		}
		else{
			loader = new XMLDatabaseLoader(conf.host, conf.port,conf.user,conf.pass,new XMLDatabaseTable(conf.db,conf.table));
		}
	}
	/**running function, here the thread loads inserts and updated row in the table. If the table is nonexistent this function creates a new based on the data found in the path, the recycler used to store its output.
	 */
	@Override
	public void run(){
		try {
			loader.load();
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
