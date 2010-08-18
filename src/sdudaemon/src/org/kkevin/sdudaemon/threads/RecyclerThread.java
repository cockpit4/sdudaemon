/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kkevin.sdudaemon.threads;

import bsh.EvalError;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kkevin.sdudaemon.configuration.RecyclerConfig;
import bsh.Interpreter;
import java.io.File;
import org.kkevin.xmlmanipulator.XMLDatabaseTable;

/**
 * This class executes a Datarecycler in a Thread. 
 * @category WebScraper
 * @package org.kkevin.sdudaemon
 * @subpackage org.kkevin.sdudaemon.threads
 * @author Kevin Krüger
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
 */
public class RecyclerThread extends Thread {

	private RecyclerConfig config;
	private Interpreter bsh;

	/**Initializes a new RecyclerThread with configuration
	 * @param config configuration to initialize
	 */
	public RecyclerThread(RecyclerConfig config) throws EvalError {
		this.config = config;
		bsh = new Interpreter();
		bsh.setOut(System.out);
		bsh.setErr(System.err);

		File output = new File(config.outputPath + config.projectName + "/" + "recycler/" + config.id + "/");
		if (!output.exists()) {
			output.mkdirs();
		}

		config.outputPath = output.getAbsolutePath() + "/";
		Logger.getLogger("SystemLogger").log(Level.CONFIG, "output Path:{0}", config.outputPath);
	}

	/**This function contains the main code to perform transformation from scraper output to updater input<br>
	 * So the input is a directory, containing your files. The output is an XML-Object accessible through the XMLManipulator object "resultDocument", which will be written into the output path under the name choosen by you. The algorithm checks if there is a preexistent file. So this process might be interrupted and resumed anytime.
	 * This actually is bean shell code so you are flexible to tell the Program how to transform you data<br>
	 * This works this way<br>
	 * -the path attribute defined in the configuration XML, leads to a directory containing files<br>
	 * -these files may be created by a former scraping process<br>
	 * -With this information and readable files the program now passes every file through to your <br>
	 *  bean shell code also defined in the configuration XML<br>
	 * -<b>So keep in mind that your code shall describe a single iteration step</b><br>
	 * -this is supposed to provide thread security, so the recycling process can be interrupted after each execution of this one step<br>
	 * -So you can make sure that after every performance of your code your files and data are stated in some way such that a resume of this process is possible.<br>
	 *So just load your file, do your magic and make sure its data will be written somewhere and prepare for a new iteration
	 *Feel free to add variables and objects, you can create additional files too, but not that this part awaits many files to be transformed in one XML-File
	 *This actual xmlDocument is accessible in you bean shell code trough "resultDocument", see org.kkevin.xmlmanipulator.XMLManipulator shipped with this package for further information
	 */
	@Override
	public void run() {
		try {
			Logger.getLogger("SystemLogger").log(Level.CONFIG, "loading : {0}", config.scrapeyardPath);
			File[] input = (new File(config.scrapeyardPath)).listFiles(); // this is what we transform. The files in your specified directory
			XMLDatabaseTable resultDocument;

			Logger.getLogger("SystemLogger").log(Level.CONFIG, "new Configuration : {0}", config.outputPath);


			resultDocument = new XMLDatabaseTable(config.table, config.database);


			bsh.set("resultDocument", resultDocument);
			bsh.set("steps", input.length);
			int i = 0; //this will terminate the loop if there are no more files

			while (!interrupted() && i < input.length) {
				synchronized (this) {

					bsh.set("iteration", i);

					if (input[i].isFile()) {
						bsh.set("inputFile", input[i]);
					}
					bsh.eval(config.code);


					//System.out.println("DOCUMENT : \n"+resultDocument+"\n-----------------");

					i++;
				}//TODO : change below such that projectname is part of the filename
				if (i % 1000 == 0 && i > 0) {
					resultDocument = (XMLDatabaseTable) bsh.get("resultDocument");
					//Finalize document add hash values to each row ...
					resultDocument.writeFile(config.outputPath + (i / 1000) + ".xml");
					resultDocument = new XMLDatabaseTable(config.table, config.database);
					bsh.set("resultDocument", resultDocument);
				}
			}
			
			resultDocument = (XMLDatabaseTable) bsh.get("resultDocument");
			resultDocument.writeFile(config.outputPath + ((i / 1000) + 1) + ".xml");



			File[] outputFiles = (new File(config.outputPath)).listFiles();
			XMLDatabaseTable finalFile = new XMLDatabaseTable(config.table,config.database);

			for(File file : outputFiles){
				if(file.isFile()){
					System.out.println("Appending file "+file.getName()+" ...");
					finalFile.append(new XMLDatabaseTable(file.getAbsolutePath()));
				}
			}

			finalFile.writeFile(config.outputPath+"../final-"+config.id+".xml");
		}
		catch (EvalError ex) {
			Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
		}
		catch (Exception ex) {
			Logger.getLogger(RecyclerThread.class.getName()).log(Level.SEVERE, null, ex);
			config.error=true;
		}
	}

	public RecyclerConfig getConfig(){
		return config;
	}
}

