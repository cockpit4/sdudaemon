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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;

/**
 * @author Kevin Krüger
 */
public class XMLDatabaseManager {

    ArrayList<XMLDatabaseTable> tables = new ArrayList<XMLDatabaseTable>();
    String tablePath;

    /**Creates a new Database Manager loading files from the xml data path
     * @param tablePath path where the xml table files are stored
     * @throws IOException on file issions
     * @throws Exception
     */
    public XMLDatabaseManager(String tablePath) throws IOException, JDOMException {
        File tableDir = new File(tablePath);
        if (tableDir.exists()) {
            if (tableDir.canRead()) {
                if (tableDir.canWrite()) {
                    this.tablePath = tableDir.getAbsolutePath();
                    System.out.println("Table Path : " + tablePath);

                    File[] tablefiles = tableDir.listFiles(new FileFilter() {

                        public boolean accept(File pathname) {
                            return pathname.isFile() && pathname.getName().endsWith(".xml");
                        }
                    });


                    for (File t : tablefiles) {
                        if (XMLDatabaseTable.tableExists(t.getAbsolutePath())) {
                            if (XMLDatabaseTable.tableAccessible(t.getAbsolutePath())) {
                                //TODO: (Major) add valider for Tables
                                this.tables.add(new XMLDatabaseTable(t.getAbsolutePath()));
                            } else {
                                //throw exception
                            }
                        } else {
                            //throw exception
                        }
                    }
                }
            }
        }
    }

    /**Find a table in the DBMS context
     * @param name of the table
     * @return the Table if it exists, null otherwise
     */
    private XMLDatabaseTable find(String name) {
        for (XMLDatabaseTable t : tables) {
            if (t.getTableName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    /**Aquire a Table from the database
     * @param name
     * @return null if table does not exist
     */
    public XMLDatabaseTable aquireTable(String name) {
        //TODO:(Major) table locking
        return find(name);
    }

    /**returns true if a table of the given name exists
     * @param name
     * @return
     */
    public boolean tableExists(String name) {
        return find(name) != null;
    }

    /**Returs an boolean array mapped 1:1 to the names array determining if a Table exists or not
     * @param name
     * @return
     */
    public boolean[] tablesExist(String[] name) {
        if (name != null) {
            boolean[] res = new boolean[name.length];

            for (int i = 0; i
                    < name.length; i++) {
                res[i] = tableExists(name[i]);
            }
            return res;
        }
        return null;
    }

    /**returns a string array of all registered Tables
     * @return
     */
    public String[] listTables() {
        String[] res = new String[tables.size()];
        int i = 0;
        for (XMLDatabaseTable t : tables) {
            res[i] = t.getTableName();
            i++;
        }
        return res;
    }

    /**Determines if a Database keeps tables
     * @return true if database containes some files false if it is empty 
     */
    public boolean hasTables() {
        return tables.size() > 0;
    }

    /**Adds a non existing table to the Database schema
     * @param template to add
     */
    public void addTable(XMLDatabaseTable template) {
        if (!tableExists(template.getTableName())) {
            tables.add(template);
        }
    }

    /** imports a table into database. If the table is none existent, it will be inserted. If it exists an Exception is thrown noting that it would be a better idea to appent the table to the existing one.
     * @param filename file to import
     * @throws IllegalAccessException if you try to insert an existing table
     * @throws Exception
     */
    public void importTable(String filename) throws IllegalAccessException, Exception {
        try {
            if (XMLDatabaseTable.tableAccessible(filename)) {
                XMLDatabaseTable t = new XMLDatabaseTable(filename);
                if (!tableExists(t.getTableName())) {
                    tables.add(t);
                } else {
                    XMLDatabaseTable s = find(t.getDatabaseName());
                    if (!t.getDatabaseName().equals(s.getDatabaseName())) {
                        tables.add(t);
                    } else {
                        throw new IllegalAccessException("Table already exists you may intend to appent them see XMLDatabaseTable.append(XMLDatabaseTable)");
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(XMLDatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**checks if a column t has a valid reference
     * @param t column to check
     * @return true if the link is valid
     */
    public boolean linkComplete(XMLDataColumn t) {
        try {
            if (t.refTable != null && t.refKey != null) {
                if (tableExists(t.refTable)) {

                    XMLDatabaseTable c = find(t.refTable);
                    return c.hasColumn(t.refKey);
                }
            }
        } catch (JDOMException ex) {
            Logger.getLogger(XMLDatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**Does a simple reference check. It checks if referenced tables exist and if these tables keep the referenced column.
     * @return true of all referenced tables and columns exists
     * @throws JDOMException
     */
    public boolean checkReferences() throws JDOMException {
        for (XMLDatabaseTable table : tables) {
            XMLDataColumn[] cols = table.getReferencingColumns();
            if (cols != null) {
                for (XMLDataColumn t : cols) {
                    if (!linkComplete(t)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
    public boolean checkConsistency() {
    return false;
    }*/
    /**Call this method to store the entire managed database into the disignated folder.
     */
    public void store() throws IllegalAccessException {
        synchronized (tables) {
            for (XMLDatabaseTable t : tables) {
                try {
                    if (checkReferences()) {
                        t.finalizeDocument();
                        File table = new File(tablePath);
                        t.writeFile(table.getAbsolutePath() + File.separator + t.getDatabaseName() + "." + t.getTableName() + ".xml");
                    } else {
                        throw new IllegalAccessException("tried to store an inconsistent database.");
                    }
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
                } catch (JDOMException ex) {
                    Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String[] argV) throws Exception {
        XMLDatabaseManager m = new XMLDatabaseManager("/home/kneo/Documents/cockpit4/Projects/Praktikum/XML-Tables");


        String[] tables = m.listTables();

        System.out.println("References complete : " + m.checkReferences());


        m.store();
    }
}
