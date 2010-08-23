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

package de.cockpit4.wsplugin;

import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperContext;
import org.webharvest.runtime.processors.WebHarvestPlugin;
import org.webharvest.runtime.variables.Variable;

/**Webharvest Plugin handlings user defined delays
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */

public class DelayPlugin extends WebHarvestPlugin{

	public String getName() {
		return "delayplugin";
	}

	public String[] getValidAttributes() {
		return new String[] {"delay"};
	}

	public String[] getRequiredAttributes() {
		return new String[] {"delay"};
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

	public boolean hasBody() { //no body between the the delayplugin tags in configuration
		return false;
	}

	public Variable executePlugin(Scraper scraper, ScraperContext context){
		// your code to execute plugin
		long delay;
		try{
			delay = Long.parseLong(evaluateAttribute("delay",scraper)); //retrieve delay from the 
			System.err.println("Delay fetched : "+delay); //
		}
		catch(NumberFormatException e){
				throw new NumberFormatException("wrong data type for delay");	
		}
		try{
			Thread.sleep(delay); //generate delay
		}
		catch(Exception e){}
		//scraper.execute();

		return null; //this plugin time so now return
	}
}
