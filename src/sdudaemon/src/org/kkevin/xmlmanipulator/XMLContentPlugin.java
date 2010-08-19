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
package org.kkevin.xmlmanipulator;

import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperContext;
import org.webharvest.runtime.processors.WebHarvestPlugin;
import org.webharvest.runtime.variables.Variable;
/**This Class returns an XML File to the XMLManipulator plugin
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class XMLContentPlugin extends WebHarvestPlugin{
	/**Returns the name of the control tag
	*@return the name tag of this plug in
	*/
	public String getName() {
		return "xml-content";
	}

	/**Returns the the name of valid attributes, since this plugin has none it returns an empty string array
	*@return empty string array
	*/
	public String[] getValidAttributes() {
		return new String[] {};
	}
	/**Returns the the name of required attributes, since this plugin has none it returns an empty string array
	*@return empty string array
	*/
	public String[] getRequiredAttributes() {
		return new String[] {};
	}
	/**Returns a list of valid subprocessors since it does not matter what kind of Plugins were encapsuled withing this plugin null is return which means all processors can be applied.
	*@return null;
	*/
	public String[] getValidSubprocessors() { //all processors allowed
		return null;
	}
	/**Returns a list of dependant subprocessors since this Plugins has none it returns null which means this plugin is completly independent on subprocessors
	*@return null;
	*/
	public Class[] getDependantProcessors() { //indepedent plugin
		return null;
	}
	/** no Attributes so no suggestions
	*@return null
	*/
	public String[] getAttributeValueSuggestions(String attributeName) {
		return null;
	}
	/**The XML-Tag can keep data between the tags so this will return true
	*@return true
	*/
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
		return executeBody(scraper,context);
	}
}
