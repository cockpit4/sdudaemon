/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kkevin.sdudaemon.configuration;

/** This class stores the states and configuration data of a 
 * @category WebScraper
 * @package org.kkevin.sdudaemon
 * @subpackage org.kkevin.sdudaemon.configuration
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
 */
public class RecyclerConfig {
	/**Keeps the project name in from the config XML File
	 */
	public String projectName;

	/**Determines an integer for indication purposes
	 */
	public int id;

	/** Determines where the recycler finds data for his work
	*/
	public String scrapeyardPath;

	/**Determines where to store generated Files;
	 */
	public String outputPath;

	/**Determines whether this recycler is active or not
	 */
	public boolean active;

	/**Determines if the recycler has finished his work
	*/
	public boolean finished;

	/**Determine whether a recycler exited with an error
	 */
	public boolean error;

	/**Stores the Beanshell Code executed by the Interpreter
	 */
	public String code;

	/**Database, containing data
	 */
	public String database;

	/**name of the table containing data
	 */
	public String table;

	/**Constructor initializing a RecyclerConfig
	 * @param id the ID of the recycler
	 * @param projectName stores the project name as part of a 
	 * @param fin defines if a recycler is finished
	 * @param act defines if a recycler is active
	 * @param error determines whether a recycler returns with an error
	 * @param path input directory for existing data loaded by a scraper
	 * @param output output directory for generated data
	 * @param code Beanshell code telling the program how to convert your raw data into a compatible supported form for the updater
	 */
	public RecyclerConfig(int id,String projectName, boolean fin, boolean act, boolean error,String path,String output, String code,String table, String database){
		this.id             = id;
		finished            = fin;
		active              = act;
		this.scrapeyardPath = path;
		this.outputPath     = output;
		this.code           = code;
		this.projectName    = projectName;
		this.database       = database;
		this.table          = table;
	}

	@Override
	public String toString(){
		return "Recycler Config id:"+id+" active:"+this.active+" finished:"+this.finished+" Path:"+this.scrapeyardPath+" output path : "+outputPath+" - contains:"+code.length()+" bytes of beanshell code.";
	}
}
