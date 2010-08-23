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

package de.cockpit4.sdudaemon.configuration;

/**
 * Database Loader loads data of an XMLDataTable into a PostGRES Database;
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
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
