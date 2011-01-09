/*
Copyright (c) 2011 cockpit4, Kevin Krüger

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

package de.cockpit4.xmlmanipulator;

import java.util.ArrayList;

/**
 * @author Kevin Krüger
 */
public class XMLDatabaseManager {
    ArrayList<XMLDatabaseTable> tables;

    public XMLDatabaseManager(){
        tables = new ArrayList<XMLDatabaseTable>();
    }

    private XMLDatabaseTable find(String name){
        for(XMLDatabaseTable t : tables){
            if(t.getTableName().equals(name)){
                return t;
            }
        }
        return null;
    }

    public XMLDatabaseTable aquireTable(String name){
        return find(name);
    }

    public boolean tableExists(String name){
        return find(name) != null;
    }

    public boolean[] tablesExist(String[] name){
        if(name!=null){
            boolean[] res = new boolean[name.length];

            for(int i = 0; i<name.length;i++){
                res[i] = tableExists(name[i]);
            }
            return res;
        }
        return null;
    }

    public void addTable(XMLDatabaseTable template){
        tables.add(template);
    }

    public boolean checkConsistency(){
        return true;
    }
}
