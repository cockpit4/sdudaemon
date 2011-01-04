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

package de.cockpit4.sdudaemon.configuration.gui.controller;

import de.cockpit4.sdudaemon.configuration.RecyclerConfig;
import de.cockpit4.sdudaemon.configuration.ScraperConfig;
import de.cockpit4.sdudaemon.configuration.UpdaterConfig;
import de.cockpit4.sdudaemon.configuration.gui.model.Project;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.AbstractObservableModel;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import de.cockpit4.sdudaemon.tool.ToolHelper;
import de.cockpit4.xmlmanipulator.XMLManipulator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

/**
 *
 * @author kneo
 */
public class ProjectController extends AbstractObservableModel implements ModelChangeListener{
    private Project model;

    public ProjectController(Project model){
        this.model=model;
        model.addListener(this);
    }

    public void setModel(Project model){
        this.model = model;
        load();
         model.addListener(this);
    }

    public void save(){
        File projectConfig = new File(model.getPath());
        if(projectConfig.exists()){
            if(projectConfig.canWrite()){
                try {
                    XMLManipulator xm = new XMLManipulator("<config name=\""+model.getName()+"\"/>");
                    xm.addXPathNode("/config/scraper", "");
                    xm.addXPathNode("/config/recycler", "");
                    xm.addXPathNode("/config/updater", "");

                    for(int i = 0;i<model.getScraperCount();i++){
                        xm.addXPathNode("/config/scraper/scraper[@id=\""+model.getScraper(i).id+"\"][@active=\""+model.getScraper(i).active+"\"][@config=\""+model.getScraper(i).configPath+"\"][@output=\""+model.getScraper(i).outputPath+"\"]", "");
                    }

                    for(int i = 0;i<model.getRecyclerCount();i++){
                        xm.addXPathNode("/config/recycler/recycler[@id=\""+model.getRecycler(i).id+"\"][@active=\""+model.getRecycler(i).active+"\"][@database=\""+model.getRecycler(i).database+"\"][@table=\""+model.getRecycler(i).table+"\"][@path=\""+model.getRecycler(i).scrapeyardPath+"\"][@output=\""+model.getRecycler(i).outputPath+"\"]/code", model.getRecycler(i).code);
                    }

                    for(int i = 0;i<model.getUpdaterCount();i++){
                        xm.addXPathNode("/config/updater/updater[@id=\""+model.getUpdater(i).id+"\"][@active=\""+model.getUpdater(i).active+"\"][@host=\""+model.getUpdater(i).host+"\"][@port=\""+model.getUpdater(i).port+"\"][@user=\""+model.getUpdater(i).user+"\"][@password=\""+model.getUpdater(i).pass+"\"][@db=\""+model.getUpdater(i).db+"\"][@table=\""+model.getUpdater(i).table+"\"][@file=\""+model.getUpdater(i).inputFile+"\"][@output=\""+model.getRecycler(i).outputPath+"\"]","");
                    }

                    FileWriter fw = new FileWriter(projectConfig);
                    fw.write(xm.returnXMLContent());
                    fw.close();

                } catch (Exception ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                //TODO (minor) : some exception
            }
        }
        else{
            if(ToolHelper.installPathWritable(projectConfig)){
                projectConfig.mkdirs(); //create directory structure
                try {
                    XMLManipulator xm = new XMLManipulator("<config name=\""+model.getName()+"\"/>");
                    xm.addXPathNode("/config/scraper", "");
                    xm.addXPathNode("/config/recycler", "");
                    xm.addXPathNode("/config/updater", "");

                    for(int i = 0;i<model.getScraperCount();i++){
                        xm.addXPathNode("/config/scraper/scraper[@id=\""+model.getScraper(i).id+"\"][@active=\""+model.getScraper(i).active+"\"][@config=\""+model.getScraper(i).configPath+"\"][@output=\""+model.getScraper(i).outputPath+"\"]", "");
                    }

                    for(int i = 0;i<model.getRecyclerCount();i++){
                        xm.addXPathNode("/config/recycler/recycler[@id=\""+model.getRecycler(i).id+"\"][@active=\""+model.getRecycler(i).active+"\"][@database=\""+model.getRecycler(i).database+"\"][@table=\""+model.getRecycler(i).table+"\"][@path=\""+model.getRecycler(i).scrapeyardPath+"\"][@output=\""+model.getRecycler(i).outputPath+"\"]/code", model.getRecycler(i).code);
                    }

                    for(int i = 0;i<model.getUpdaterCount();i++){
                        xm.addXPathNode("/config/updater/updater[@id=\""+model.getUpdater(i).id+"\"][@active=\""+model.getUpdater(i).active+"\"][@host=\""+model.getUpdater(i).host+"\"][@port=\""+model.getUpdater(i).port+"\"][@user=\""+model.getUpdater(i).user+"\"][@password=\""+model.getUpdater(i).pass+"\"][@db=\""+model.getUpdater(i).db+"\"][@table=\""+model.getUpdater(i).table+"\"][@file=\""+model.getUpdater(i).inputFile+"\"][@output=\""+model.getRecycler(i).outputPath+"\"]","");
                    }

                    FileWriter fw = new FileWriter(projectConfig);
                    fw.write(xm.returnXMLContent());
                    fw.close();

                } catch (Exception ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void load(){
        File projectConfig = new File(model.getPath());

        if(projectConfig.exists()){ //exists ? no it will be created propably soon
            if(projectConfig.canRead()){
                try {
                    XMLManipulator xm = new XMLManipulator(XMLManipulator.readFileAsString(model.getPath()));
                    //public ScraperConfig(int id,
                    //                  String projectName,
                    //                  boolean active,
                    //                  boolean finished,
                    //                  boolean error, String configPath,
                    //                  String outputPath)
                    //
                    List project = XPath.selectNodes(xm.getDocument(), "/config/scraper/scraper");
                    Element e;
                    for(Object node : project){
                        e = (Element) node;
                        ScraperConfig s = new ScraperConfig(Integer.parseInt(e.getAttributeValue("id")),
                                                               e.getAttributeValue("name"),
                                                               Boolean.parseBoolean(e.getAttributeValue("active")),
                                                               false,
                                                               false,
                                                               e.getAttributeValue("config"),
                                                               e.getAttributeValue("output"));
                        model.addScraper(s);
                        
                    }
                    /*public RecyclerConfig(int id,
                                            String projectName,
                                            boolean fin,
                                            boolean act,
                                            boolean error,
                                            String path,
                                            String output,
                                            String code,
                                            String table,
                                            String database){*/
                    project = XPath.selectNodes(xm.getDocument(), "/config/recycler/recycler");
                    for(Object node : project){
                        e = (Element) node;
                        RecyclerConfig r = new RecyclerConfig(Integer.parseInt(e.getAttributeValue("id")),
                                                            null,
                                                            false,
                                                            true,
                                                            false,
                                                            e.getAttributeValue("path"),
                                                            e.getAttributeValue("output"),
                                                            e.getChild("code").getText(),
                                                            e.getAttributeValue("table"),
                                                            e.getAttributeValue("database"));
                        model.addRecycler(r);
                    }

                    /*public UpdaterConfig(int id,
                                        String host,
                                        String port,
                                        String user,
                                        String pass,
                                        String inputFile,
                                        String db,
                                        String table,
                                        boolean act,
                                        boolean fin,
                                        boolean err)*/
                    project = XPath.selectNodes(xm.getDocument(), "/config/updater/updater");
                    for(Object node : project){
                        e = (Element) node;
                        UpdaterConfig u = new UpdaterConfig(Integer.parseInt(e.getAttributeValue("id")),
                                                            e.getAttributeValue("host"),
                                                            e.getAttributeValue("port"),
                                                            e.getAttributeValue("user"),
                                                            e.getAttributeValue("password"),
                                                            e.getAttributeValue("file"),
                                                            e.getAttributeValue("db"),
                                                            e.getAttributeValue("table"),
                                                            true,
                                                            false,
                                                            false);
                    }
                }
                catch (Exception ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                //Throw some exception
            }
        }
        else{
            //Do nothing and wait for the file to be created
        }
    }

    public void onChange() {
        save();
    }
}
