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
package de.cockpit4.xmlmanipulator.webharvest;

import de.cockpit4.xmlmanipulator.XMLManipulator;
import java.io.FileWriter;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperContext;
import org.webharvest.runtime.processors.WebHarvestPlugin;
import org.webharvest.runtime.variables.EmptyVariable;
import org.webharvest.runtime.variables.Variable;
/**This Class provides xml manipulating capabilities such as XML element changing and node addition. 
 * This changes are described within the body of the xml configuration tag of initializing this plugin.
 * See also ElementPlugin AddPlugin and RemovePlugin
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class XMLManipulatorPlugin extends WebHarvestPlugin{

	private String body; //body between the tags
	private String xpath;
	private String value;
	private XMLManipulator xm;

	private java.util.List<ChangeInfo> changeList;

	/**Returns the name of the control tag
	*@return the name tag of this plug in
	*/
	public String getName() {
		return "manipulate-xml";
	}
	/**Returns the the name of required attributes, since this plugin has none it returns an empty string array
	*@return empty string array
	*/
	@Override
	public String[] getValidAttributes() {
		return new String[] {"file"};
	}
	/**Returns the the name of required attributes, since this plugin has none it returns an empty string array
	*@return empty string array
	*/
	@Override
	public String[] getRequiredAttributes() {
		return new String[] {"file"};
	}
	/**Returns a list of valid subprocessors
	*@return {"add","change","remove","xml-content"};
	*/
	@Override
	public String[] getValidSubprocessors() {
		return new String[] {"add","change","remove","xml-content"};
	}
	/**Returns a list of dependant subprocessors since this Plugins has none it returns null which means this plugin is completly independent on subprocessors
	*@return null;
	*/
	@Override
	public Class[] getDependantProcessors() { //indepedent plugin
		return new Class[] {ElementPlugin.class,XMLContentPlugin.class,AddPlugin.class};
	}
	/** no Attributes so no suggestions
	*@return null
	*/
	@Override
	public String[] getAttributeValueSuggestions(String attributeName) {
		return null;
	}
	/**The XML-Tag can keep data between the tags so this will return true
	*@return true
	*/
	@Override
	public boolean hasBody() { //there is content (XML) between the manipulate-xml tags
		return true;
	}
	/**This method executes subprocessors and returns the result to the xml manipulator
	*@param scraper runtime scraper with information about the current scraping environment
	*@param context current scrapers runtime context
	*@return result of further scraper execution
	*/
	public Variable executePlugin(Scraper scraper, ScraperContext context){
		// your code to execute plugin
		//System.setProperty("java.class.path",System.getProperty("java.class.path")+";../lib/jdom.jar;../lib/jaxen-1.1.1.jar");
		try{
			body = executeBody(scraper,context).toString(); //execute body result needs to be XML, exception is thrown otherwise
			xm = new XMLManipulator(body); // load xml into the Manipulator

			//System.out.println("BODY:\n"+body+"\n changeList.length():"+changeList.size());

			for(ChangeInfo c : changeList){
				System.out.println(c);
				switch(c.type){
					case ChangeInfo.ACTION_ADD:
						xm.addXPathNode(c.xpath,c.value);
					break;

					case ChangeInfo.ACTION_CHANGE:
						//System.out.println(c+" CURRENT VALUE IS : "+xm.getXPathValue(c.xpath));
						xm.setXPathValue(c.xpath,c.value); // set the path value throws Exception if invalid...
					break;

					case ChangeInfo.ACTION_REMOVE:
						xm.removeXPathNode(c.xpath,c.value);
					break;
				}
			}

			String result = xm.toString();
			//System.out.println("RESULT:\n"+result);
			String path = evaluateAttribute("path",scraper);
			FileWriter fw = new FileWriter(path);
			fw.write(result);
			fw.close();
			return new EmptyVariable(); //return manipulated XML Document (String);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**This method is called by the subprocessors in order to define the changes of an XML-Document.<br>
	*The changes were added sequencially so the order of addition matters, to the XMLManipulator.<br>
	*If you add an node then change or delete it meight work, if you remove it before you add it it will cause exceptions
	*@param change to apply on the XML Document
	*/
	public void addChangeInfo(ChangeInfo change){
		if(changeList == null){
			changeList = new java.util.ArrayList<ChangeInfo>(); //create a new list if there no changes
		}
		changeList.add(change);// then add the change
	}
}
