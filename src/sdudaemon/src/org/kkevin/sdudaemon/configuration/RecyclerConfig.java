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

package org.kkevin.sdudaemon.configuration;

/** This class stores the states and configuration data of a 
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
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
	/**Returns a String of the current configuration
	 * @return String representation
	 */
	@Override
	public String toString(){
		return "Recycler Config id:"+id+" active:"+this.active+" finished:"+this.finished+" Path:"+this.scrapeyardPath+" output path : "+outputPath+" - contains:"+code.length()+" bytes of beanshell code.";
	}
}
