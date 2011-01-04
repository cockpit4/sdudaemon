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

package de.cockpit4.sdudaemon.threads;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator; //TODO: reintegrate Apache log4j!
import de.cockpit4.sdudaemon.configuration.ScraperConfig;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;


/**
 * This Class describes a thread
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class ScraperThread extends Thread{
	private ScraperConfig conf;
	private ScraperConfiguration scraperConfig;
	private Scraper webscraper;
	/***/
	public ScraperThread(ScraperConfig config) throws FileNotFoundException{
		conf = config;

		Properties props = new Properties();

                props.setProperty("log4j.rootLogger", "INFO, stdout");
                props.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
                props.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
                props.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%-5p (%20F:%-3L) - %m\n");

                props.setProperty("log4j.appender.file", "org.apache.log4j.DailyRollingFileAppender");
                props.setProperty("log4j.appender.file.File", conf.outputPath + "log/out-"+config.projectName+".log");
                props.setProperty("log4j.appender.file.DatePattern", "yyyy-MM-dd");
                props.setProperty("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
                props.setProperty("log4j.appender.file.layout.ConversionPattern", "%-5p (%20F:%-3L) - %m\n");

		PropertyConfigurator.configure(props);
		
		scraperConfig = new ScraperConfiguration(config.configPath);
		webscraper    = new Scraper(scraperConfig,config.outputPath);

		File path = new File(config.outputPath);

		path.mkdirs();
	}

	@Override
	public void run(){//TODO: check if this function can be interrupted.
		webscraper.execute();
	}

	public ScraperConfig getConfig(){
		return conf;
	}
}
