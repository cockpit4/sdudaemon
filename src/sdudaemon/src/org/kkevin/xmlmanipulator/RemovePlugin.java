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
/**This Class returns an XML File to the XMLManipulator plugin
 * @category Webscraper
 * @package org.kkevin.xmlmanipulator
 * @subpackage org.kkevin.xmlmanipulator
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
*/
public class RemovePlugin extends WebHarvestPlugin{
	
	private String xpath;
	private String value;

	public String getName() {
		return "remove";
	}

	public String[] getValidAttributes() {
		return new String[] {"xpath"};
	}

	public String[] getRequiredAttributes() {
		return new String[] {"xpath"};
	}

	public String[] getValidSubprocessors() { //all processors allowed
		return null;
	}

	public Class[] getDependantProcessors() { //indepedent plugin
		return null;
	}

	public String[] getAttributeValueSuggestions(String attributeName) {
		return null;
	}

	public boolean hasBody() { //there is content (XML) between the manipulate-xml tags
		return true;
	}

	public Variable executePlugin(Scraper scraper, ScraperContext context){
		// your code to execute plugin
	        BaseProcessor processor = scraper.getRunningProcessorOfType(XMLManipulatorPlugin.class);

		XMLManipulatorPlugin mp = (XMLManipulatorPlugin) processor;

		xpath = evaluateAttribute("xpath",scraper);
		value = executeBody(scraper,context).toString();

		mp.addChangeInfo(new ChangeInfo(xpath,value,ChangeInfo.ACTION_REMOVE));
		
		return new EmptyVariable();
	}
}
