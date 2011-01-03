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

import de.cockpit4.sdudaemon.configuration.gui.model.Project;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.AbstractObservableModel;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import de.cockpit4.sdudaemon.tool.ToolHelper;
import de.cockpit4.xmlmanipulator.XMLManipulator;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
    }

    public void onChange() {
        save();
    }
}
