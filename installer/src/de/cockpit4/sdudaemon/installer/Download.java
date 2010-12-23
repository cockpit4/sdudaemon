/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.sdudaemon.installer;

import de.cockpit4.sdudaemon.installer.eventhandling.ChangeAdapter;
import de.cockpit4.sdudaemon.installer.eventhandling.ChangeEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kneo
 */
public class Download extends ChangeAdapter{
    private URL url;
    private String target;
    private int size = 0;
    private URLConnection con;

    public Download(URL url, String dest){
        try{
            this.url = url;
            target = dest;
            con = url.openConnection();
            size=con.getContentLength();
        } catch (IOException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() throws IOException{
        OutputStream out = null;
        InputStream in = null;

            URL base = url;
            
            in = con.getInputStream();
            
            
            byte[] buffer = new byte[4096];
            int len1 = 0;
            int length = 0;
            out = new FileOutputStream(target);
            while(-1 != (len1 = in.read(buffer))){
                out.write(buffer, 0, len1);
                length+=len1;
                fireChangeEvent(new ChangeEvent(length));
            }

            out.flush();
            out.close();
            in.close();
    }

    public String getTarget(){
        return target;
    }

    public int getSize(){
        return size;
    }
}
