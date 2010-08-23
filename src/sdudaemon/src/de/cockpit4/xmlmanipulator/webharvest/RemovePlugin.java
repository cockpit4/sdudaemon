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

import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperContext;
import org.webharvest.runtime.processors.WebHarvestPlugin;
import org.webharvest.runtime.processors.BaseProcessor;
import org.webharvest.runtime.variables.EmptyVariable;
import org.webharvest.runtime.variables.Variable;
/**This Class returns an XML File to the XMLManipulator plugin
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
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
