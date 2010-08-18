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
public class ScraperConfig {
	public int id; // id of the scraper, necessary for identification in the configfile
	public boolean finished; // is the scraper finished
	public boolean active; // is the scraper active?
	public boolean error; // determine if an error has occured
	public String projectName;
	public String configPath; //define where to find the scrapers configuration
	public String outputPath; //define the output and working directory

	public ScraperConfig(int id,String projectName,boolean active, boolean finished, boolean error,String configPath, String outputPath){
		this.id = id;
		this.active = active;
		this.error = error;
		this.finished = finished;
		this.configPath = configPath;
		this.outputPath = outputPath;
		this.projectName = projectName;
	}
}
