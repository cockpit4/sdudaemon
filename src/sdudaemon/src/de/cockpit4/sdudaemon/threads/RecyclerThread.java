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

import bsh.EvalError;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.cockpit4.sdudaemon.configuration.RecyclerConfig;
import bsh.Interpreter;
import bsh.This;
import de.cockpit4.sdudaemon.tool.FileSorter;
import de.cockpit4.xmlmanipulator.XMLDatabaseManager;
import java.io.File;
import de.cockpit4.xmlmanipulator.XMLDatabaseTable;

/**
 * This class executes a Datarecycler in a Thread. 
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
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
//
//		File output = new File(config.outputPath  + File.separator + "recycler"+File.separator + config.id + File.separator);
//		if (!output.exists()) {
//			output.mkdirs();
//		}

//
		Logger.getLogger("SystemLogger").log(Level.CONFIG, "output Path:{0}", config.outputPath);
	}

        public class FileSortTreeNode{
            public File data;
            public FileSortTreeNode left;
            public FileSortTreeNode right;

            public FileSortTreeNode(File data){
                this.data = data;
                left=null;
                right=null;
            }
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
			File[] input = new FileSorter((new File(config.scrapeyardPath)).listFiles()).byDate(); // this is what we transform. The files in your specified directory

                        XMLDatabaseManager resDatabase = new XMLDatabaseManager(config.outputPath);

			Logger.getLogger("SystemLogger").log(Level.CONFIG, "new Configuration : {0}", config.outputPath);
                        bsh.set("resultDatabase", resDatabase);
			
                        int i=0;
			while (!interrupted() && i < (input.length)) {
                                Logger.getLogger("SystemLogger").log(Level.INFO, "Dispatching File {0}",input[i].getName());
				synchronized (this) {
                                        if (input[i].isFile()) {
                                            if(input[i].canRead()){
                                                bsh.set("syslog", Logger.getLogger("SystemLogger"));
                                                //bsh.set("iteration", i);
                                                bsh.set("inputFile", input[i]);
                                                bsh.eval(config.code);
                                                resDatabase = (XMLDatabaseManager) bsh.get("resultDatabase");
                                                resDatabase.store();
                                            }
                                            else{
                                                Logger.getLogger("SystemLogger").log(Level.WARNING, "Skipping file {0} because reading is not permitted!",input[i].getName());
                                            }
                                        }
                                        else{
                                            Logger.getLogger("SystemLogger").log(Level.INFO, "Skipping file {0} because it is an directory!",input[i].getName());
                                        }


					//System.out.println("DOCUMENT : \n"+resultDocument+"\n-----------------");

					i++;
				}
				if (i % 1000 == 0 && i > 0) {
					//resultDocument = (XMLDatabaseTable) bsh.get("resultDocument");
					//Finalize document add hash values to each row ...
					/*resultDocument.writeFile(config.outputPath + (i / 1000) + ".xml");
					resultDocument = new XMLDatabaseTable(config.table, config.database);
					bsh.set("resultDocument", resultDocument);*/
                                        resDatabase.store();
				}
			}
			resDatabase = (XMLDatabaseManager) bsh.get("resultDatabase");
                        resDatabase.store();
		}
		catch (EvalError ex) {
			Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
                        config.error=true;
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

