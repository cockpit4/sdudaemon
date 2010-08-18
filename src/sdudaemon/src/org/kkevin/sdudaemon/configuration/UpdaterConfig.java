/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kkevin.sdudaemon.configuration;

/**
 * Database Loader loads data of an XMLDataTable into a PostGRES Database;
 * @category Webscraper
 * @package org.kkevin.sdudaemon.configuration
 * @subpackage org.kkevin.sdudaemon.configuration
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
 */
public class UpdaterConfig {
	public int id;
	public String host;
	public String port;
	public String user;
	public String pass;
	public String inputFile;
	public String db;
	public String table;

	public boolean finished;
	public boolean active;
	public boolean error;

	public UpdaterConfig(int id,String host, String port, String user, String pass, String inputFile,String db,String table,boolean act, boolean fin, boolean err){
		this.host      = host;
		this.port      = port;
		this.user      = user;
		this.pass      = pass;
		this.inputFile = inputFile;
		this.db        = db;
		this.table     = table;
		error          = err;
		active         = act;
		finished       = fin;
	}
}
