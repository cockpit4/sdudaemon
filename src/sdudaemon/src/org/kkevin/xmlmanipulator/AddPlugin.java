package org.kkevin.xmlmanipulator;

import org.webharvest.exception.*;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperContext;
import org.webharvest.runtime.processors.WebHarvestPlugin;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.EmptyVariable;
import org.webharvest.runtime.variables.ListVariable;
import org.webharvest.runtime.variables.Variable;
import org.webharvest.utils.*;
/**This Class provides the functionality to add nodes to a given XML-Document by defining an XPath.<br>
 * If your Path is not found, the XMLManipulator tries to reconstruct it by backtracking.
 * All seleting attributes will be setted to, so xpath will match the document next time you try to access it.
 * This Class returns an XML File to the XMLManipulator plugin
 * @category Webscraper
 * @package org.kkevin.xmlmanipulator
 * @subpackage org.kkevin.xmlmanipulator
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
*/

public class AddPlugin extends WebHarvestPlugin{
	
	private String xpath;
	private String value;
	/**Returns the name of the control tag
	*@return the name tag of this plug in
	*/
	public String getName() {
		return "add";
	}
	/**Returns the the name of valid attributes, since this plugin has just one it returns an string array containing "xpath"
	*@return empty string array
	*/
	public String[] getValidAttributes() {
		return new String[] {"xpath"};
	}
	/**Returns the the name of required attributes, since this plugin has just one it returns an string array containing "xpath"
	*@return empty string array
	*/
	public String[] getRequiredAttributes() {
		return new String[] {"xpath"};
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
	/** Attributes but no suggesstions which causes ParseExcetion if they were missing.
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
	        BaseProcessor processor = scraper.getRunningProcessorOfType(XMLManipulatorPlugin.class);

		XMLManipulatorPlugin mp = (XMLManipulatorPlugin) processor; //retrieve our processor

		xpath = evaluateAttribute("xpath",scraper); //retrieve the XPath
		value = executeBody(scraper,context).toString(); // execute subprocessors to get futher data

		mp.addChangeInfo(new ChangeInfo(xpath,value,ChangeInfo.ACTION_ADD)); // add the change
		
		return new EmptyVariable();//return nothing since everything necessary was done
	}
}
