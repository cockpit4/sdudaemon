/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kkevin.sdudaemon.threads;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import org.kkevin.sdudaemon.configuration.ScraperConfig;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;


/**
 * This Class describes a thread
 * @category
 * @package
 * @subpackage
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
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
                //props.setProperty("log4j.appender.file.File", workingDir + "/out.log");
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
