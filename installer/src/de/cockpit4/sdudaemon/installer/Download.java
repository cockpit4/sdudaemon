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
