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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.kkevin.xmlmanipulator.XMLManipulator;

/** This class stores project configuration
  * Each configuration consists of scraper recycler and updater definitions.
  * The scraper shall provide simple data fetching ability
  * The recycler is a user defined program which reads files the scraper created, and transforms them into a format processible by the database updater
  * The updater provides a database interface, and reads data the recycler creates, and store its bare content into a database. The database is defined by a server and authentication credentials. Once data loaded and recycled the updater reads every existing row from the list and updates it. If necessary the table will be altered too.
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */

public class Configuration {

	private final static String[] sections = {"scraper", "recycler", "updater"};
	private ArrayList<ScraperConfig> scraper;
	private ArrayList<RecyclerConfig> recycler;
	private ArrayList<UpdaterConfig> updater;
	private String stateFilePath;
	private String dumpFilePath;

	/**Create configuration and creating necessary project paths. All paths are requested to be writable and accessible. Exceptions are thrown otherwise.
	 * @param configPath path to the project configuration
	 * @param dumpPath path to the place where each projects data is stored
	 * @param statePath each project consists of subroutines whose states of execution needed to be known
	 */
	public Configuration(String configPath, String dumpPath, String statePath) throws IOException, JDOMException, Exception {
		Logger.getLogger("SystemLogger").log(Level.CONFIG, "loading Config for :{0}...", configPath);

		scraper = new ArrayList<ScraperConfig>(); //create the scraper list
		recycler = new ArrayList<RecyclerConfig>(); //create the recycler list
		updater = new ArrayList<UpdaterConfig>(); //create the updater list
		//load the project config file
		Document doc = (new SAXBuilder()).build(new StringReader(XMLManipulator.readFileAsString(configPath + "config.xml")));
		//get the project name
		String projectName = ((Attribute) XPath.selectSingleNode(doc, "/config/@name")).getValue();

		//state document, stores the state of each part of this application
		Document state;

		//Dumpdir and Statedir creation
		//
		File stateDir = new File(statePath + projectName); //project state directory
		File stateFile = new File(statePath + projectName + "/state.xml"); //project  state file
		File dumpDir = new File(dumpPath); //where to store outputs of the project parts

		if (!stateDir.exists()) { //create the directories if they do not exist
			stateDir.mkdirs();
		}
		//TODO : Exception handling and better Path tolerance
		
		XMLManipulator statefile; //maniputlator for the state file. It is new created if it does not exist, and loaded otherwise

		if (!stateFile.exists()) { //Create new file and Document tree
			stateFile.createNewFile();
			statefile = new XMLManipulator("");
			Logger.getLogger("SystemLogger").log(Level.CONFIG, "State file path : {0}", stateFile.getAbsolutePath());
			List elements;
			//TODO : Exception handling for malformed documents
			for (String s : sections) { // create each section (scraper,recycler,updater)
				elements = XPath.selectNodes(doc, "//" + s + "/" + s); //add each node to the state file
				Logger.getLogger("SystemLogger").log(Level.CONFIG, "XPath : //{0}/{1}", new Object[]{s, s});
				List att; // attribute list
				for (Object a : elements) { //for each a in list elements
					//add each scraper recycler etc ...
					//TODO: add scraper, and updater
					//TODO: Dispatch further attributes
					if (a instanceof Element) {
						if (((Element) a).getName().equals(sections[0])) { //element name is "scraper"
							att = ((Element) a).getAttributes(); //get the attribute list of this element
							for (Object attribute : att) { //for each attribute
								if (((Attribute) attribute).getName().equals("id")) { //dispatch attribute "id"
									statefile.addXPathNode("/state/scraper/scraper[@id=\'" + ((Attribute) attribute).getValue() + "\'][@finished=\'false\'][@error=\'false\'][@active=\'true\']", "");
								}
							}
						}

						if (((Element) a).getName().equals(sections[1])) { //element name is "recycler"
							att = ((Element) a).getAttributes();//get Attributes
							for (Object attribute : att) {//for each attribute
								if (((Attribute) attribute).getName().equals("id")) {//dispatch attribute "id"
									statefile.addXPathNode("/state/recycler/recycler[@id=\'" + ((Attribute) attribute).getValue() + "\'][@finished=\'false\'][@error=\'false\'][@active=\'true\']", "");
								}
							}
						}

						if (((Element) a).getName().equals(sections[2])) { //element name is "updater"
							att = ((Element) a).getAttributes();//get attributes

							for (Object attribute : att) {//for each attribute
								if (((Attribute) attribute).getName().equals("id")) {
									statefile.addXPathNode("/state/updater/updater[@id=\'" + ((Attribute) attribute).getValue() + "\'][@finished=\'false\'][@error=\'false\'][@active=\'true\']", "");
								}
							}
						}
					}
				}
			}
			FileWriter fw = new FileWriter(stateFile); //write the State file

			fw.write(statefile.toString()); //write new File
			fw.close(); //close the File ..
		}

		//Load (now) existing file
		state = (new SAXBuilder()).build(new StringReader(XMLManipulator.readFileAsString(stateFile.getPath())));
		//TODO: dispatch state flags
		//now iterate through the sections again to read out the states of each project part
		for (String a : sections) {
			//get Nodes from the document
			// "//scraper/scraper" gets all scrapers found in the state file

			List nodes = XPath.selectNodes(state, "//" + a + "/" + a);

			for (Object section : nodes) { // now iterate through all existing nodes of the path created above
				if (section instanceof Element) { // only Elements are interesting here
					Object scr; // result saver ...
					//TODO: dispatch "depends" attribute in the xml configuration
					//Now dispatch the sections if there are any and load the attributes, passing them to the new config
					if (((Element) section).getName().equals(sections[0])) { //scraper
						scr = XPath.selectSingleNode(doc, "//" + a + "/" + a + "[@id=\'" + ((Element) section).getAttributeValue("id") + "\']"); //get some attributes of node where id = ...
						if (scr instanceof Element) {
							int id            = Integer.parseInt(((Element) section).getAttributeValue("id"));
							boolean active    = Boolean.parseBoolean(((Element) section).getAttributeValue("active"));
							boolean finished  = Boolean.parseBoolean(((Element) section).getAttributeValue("finished"));
							boolean error     = Boolean.parseBoolean(((Element) section).getAttributeValue("error"));
							String outputPath = ((Element) scr).getAttributeValue("output");
							String path       = ((Element) scr).getAttributeValue("config");
							scraper.add(new ScraperConfig(id, projectName, active, finished, error, path, outputPath));
						}

					}

					if (((Element) section).getName().equals(sections[1])) {//recycler
						scr = XPath.selectSingleNode(doc, "//" + a + "/" + a + "[@id=\'" + ((Element) section).getAttributeValue("id") + "\']"); //get attributes of node where id = ...
						if (scr instanceof Element) {
							int id              = Integer.parseInt(((Element) section).getAttributeValue("id"));
							boolean active      = Boolean.parseBoolean(((Element) section).getAttributeValue("active"));
							boolean finished    = Boolean.parseBoolean(((Element) section).getAttributeValue("finished"));
							boolean error       = Boolean.parseBoolean(((Element) section).getAttributeValue("error"));

							String database	    = ((Element) scr).getAttributeValue("database");
							String table	    = ((Element) scr).getAttributeValue("table");
							String code         = ((Element) XPath.selectSingleNode(doc, "//recycler/recycler[@id=\'" + id + "\']/code")).getText();
							String path         = ((Element) scr).getAttributeValue("path");
							String output       = ((Element) scr).getAttributeValue("output");
							RecyclerConfig conf = new RecyclerConfig(id,projectName, finished, active, error, path, output, code, table,database);
							Logger.getLogger("SystemLogger").config(conf.toString());
							
							//System.out.println(code);
							recycler.add(conf);
						}
					}
					//TODO: add updater too
					
					if(((Element) section).getName().equals(sections[2])){//updater
						scr = XPath.selectSingleNode(doc, "//"+a+"/"+a+"[@id=\'"+((Element) section).getAttributeValue("id")+"\']"); //get some attributes of node where id = ...
						if(scr instanceof Element){
							int id           = Integer.parseInt(((Element) section).getAttributeValue("id"));
							boolean active   = Boolean.parseBoolean(((Element) section).getAttributeValue("active"));
							boolean finished = Boolean.parseBoolean(((Element) section).getAttributeValue("finished"));
							boolean error    = Boolean.parseBoolean(((Element) section).getAttributeValue("error"));

							String host = ((Element) scr).getAttributeValue("host");
							String port = ((Element) scr).getAttributeValue("port");
							String user = ((Element) scr).getAttributeValue("user");
							String pass = ((Element) scr).getAttributeValue("password");
							String file = ((Element) scr).getAttributeValue("file");
							String db   = ((Element) scr).getAttributeValue("db");
							String table = ((Element) scr).getAttributeValue("table");
							updater.add(new UpdaterConfig(id,host,port,user,pass,file,db,table,active,finished,error));
						}
					}
				}
			}
		}
		//TODO: load recycler config first add "additional" configs later...
	}
	/**This method returns all Recycler configurations in a list
	 * @return List full of Recycler configurations
	 */
	public ArrayList<RecyclerConfig> getRecyclers() {
		return recycler;
	}
	/**This method returns all Scraper configurations in a list
	 * @return List full of Scraper configurations
	 */
	public ArrayList<ScraperConfig> getScrapers() {
		return scraper;
	}
	/**This method returns all Updater configurations in a list
	 * @return List full of Updater configurations
	 */
	public ArrayList<UpdaterConfig> getUpdater() {
		return updater;
	}
}
