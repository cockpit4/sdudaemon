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

package de.cockpit4.sdudaemon.tool;

import java.io.File;

/**
 *
 * @author kneo
 */
public class ToolHelper {
    /** Determine if a path subtree is writable to the current user.
     * @param filename File to check (actual file or directory)
     * @return true if user has write access to a parent directory of the path, so the required subdirectory may be created. False if there is no write privilege to the user.
     */
    public static boolean installPathWritable(File filename){ //check a path if a parent directory is writable
        File parent = filename;
        do{
            if(parent.exists()){
                if(parent.canWrite()){
                    return true;
                }
            }
            parent = parent.getParentFile();
        }while(parent!=null); //stop if the root appears
        return false;
    }
}
