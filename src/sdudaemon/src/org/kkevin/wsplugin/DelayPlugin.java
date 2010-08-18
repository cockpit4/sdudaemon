package org.kkevin.wsplugin;
import org.webharvest.exception.*;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperContext;
import org.webharvest.runtime.processors.WebHarvestPlugin;
import org.webharvest.runtime.variables.EmptyVariable;
import org.webharvest.runtime.variables.ListVariable;
import org.webharvest.runtime.variables.Variable;
import org.webharvest.utils.*;

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
