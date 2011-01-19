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
package de.cockpit4.xmlmanipulator;

import de.cockpit4.sdudaemon.tool.ToolHelper;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

/** This class provides the functionality to create updater compatible XML Tables easily. 
 *
 * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
 * @version 1.0
 *
 *  Copyright (c) 2010 cockpit4 GmbH
 *  sdudeamon is released under the MIT license
 */
public final class XMLDatabaseTable {

    private XMLManipulator xm;
    private String tableName;
    private String db;
    private int datalength = 0;

    /**Constructor creating a new empty XML-Database Table
     * @param db name of the target database
     * @param name name of the table
     */
    public XMLDatabaseTable(String name, String db) throws Exception {
        this.db = db;
        this.tableName = name;
        xm = new XMLManipulator("");
        xm.addXPathNode("/table[@name=\'" + name + "\'][@database=\'" + db + "\']", "");
        xm.addXPathNode("/table[@name=\'" + name + "\'][@database=\'" + db + "\']/head", "");
        xm.addXPathNode("/table[@name=\'" + name + "\'][@database=\'" + db + "\']/body", "");
    }

    /**Constructor creates a new object based on an existing filename
     * @param filename file to load
     */
    public XMLDatabaseTable(String filename) throws IOException, JDOMException {
        loadFile(filename);


        Object res = XPath.selectSingleNode(getDocument(), "/table");

        tableName = ((Element) res).getAttributeValue("name");
        db = ((Element) res).getAttributeValue("database");
    }
    //creates a new table column labeled "name" of type "type" if it exists this method does nothing
    /**adds an new column to the table
     * @param name of the column
     * @param type of the column
     * @throws JDOMException
     */
    public void addColumn(String name, String type) throws JDOMException {
        if (!xm.nodeExists("/table/head/column[@name=\'" + name + "\']")) {
            try {
                //System.out.println("Document "+xm);
                xm.addXPathNode("/table/head/column[@name=\'" + name + "\'][@type=\'" + type + "\']", "");
            } catch (IllegalArgumentException ex) {
                Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            }
        }
    }

    /**Add an Data Column which is an index column
     * @param name
     * @param type
     * @param index
     * @throws JDOMException
     */
    public void addColumn(String name, String type, int index) throws JDOMException {
        if (!xm.nodeExists("/table/head/column[@name=\'" + name + "\']")) {
            try {

                //System.out.println("added Column : " + name + "  " + type + " " + index);
                xm.addXPathNode("/table/head/column[@name=\'" + name + "\'][@type=\'" + type + "\'][@index=\'" + index + "\']", "");
            } catch (IllegalArgumentException ex) {
                Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            }
        }
    }
    /**Adds a new not existing column to the table
     * @param name of the column
     * @param type of the column
     * @param refTab referencing table
     * @param refKey referencing keys
     * @throws JDOMException
     */
    public void addColumn(String name, String type, String refTab, String refKey) throws JDOMException {
        if (!xm.nodeExists("/table/head/column[@name=\'" + name + "\']")) {
            try {

                xm.addXPathNode("/table/head/column[@name=\'" + name + "\'][@type=\'" + type + "\'][@reference-table=\'" + refTab + "\'][@reference-key=\'" + refKey + "\']", "");
            } catch (IllegalArgumentException ex) {
                Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            }
        }
    }
    /**adds a new not existing column to the table
     * @param name of the column
     * @param type of the column
     * @param refTab reference table
     * @param refKey reference key
     * @param index , indicating the order the data will have to produce the md5checksum. should be a series starting by 0
     * @throws JDOMException
     */
    public void addColumn(String name, String type, String refTab, String refKey, int index) throws JDOMException {
        if (!xm.nodeExists("/table/head/column[@name=\'" + name + "\']")) {
            try {
                xm.addXPathNode("/table/head/column[@name=\'" + name + "\'][@type=\'" + type + "\'][@reference-table=\'" + refTab + "\'][@reference-key=\'" + refKey + "\'][@index=\'" + index + "\']", "");
            } catch (IllegalArgumentException ex) {
                Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            }
        }
    }
    /**adds a single column not existing column to the table
     * @param column to add
     * @throws JDOMException
     */
    public void addColumn(XMLDataColumn column) throws JDOMException {
        if (!xm.nodeExists("/table/head/column[@name=\'" + column.name + "\']")) {

            boolean index = column.index > -1;
            boolean reference = (column.refTable != null) && (column.refKey != null);

            if (index && reference) {
                addColumn(column.name, column.type, column.refTable, column.refKey, column.index);
            } else {
                if (reference) {
                    addColumn(column.name, column.type, column.refTable, column.refKey);
                } else {
                    if (index) {
                        addColumn(column.name, column.type, column.index);
                    } else {
                        addColumn(column.name, column.type);
                    }
                }
            }
        }
    }
    /**adds columns not exisiting by name to the table
     * @param columns to add
     * @throws JDOMException
     */
    public void addColumns(XMLDataColumn[] columns) throws JDOMException {
        if (columns != null) {
            for (XMLDataColumn c : columns) {
                addColumn(c);
            }
        }
    }

    /**Counts the data sets stored in this database
     * @return count of Datarows stored in this table
     */
    public int getSize() {
        try {
            return XPath.selectNodes(getDocument(), "/table/body/row").size();
        } catch (JDOMException ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**creates a non-existent row value, or updates it if it exists
     * a none existend column will also be added to the table head.
     * @param id of the data row
     * @param column which to assign value
     * @param value to assign
     */
    public void insertValue(int id, XMLDataColumn column, String value) throws JDOMException, Exception {
        //exists the column ? no => throw exception
        //does the row id not exists create a new row
        //does the row exist insert a new one
        //System.out.println("id : "+id+" column : "+column.name+" type : "+column.type+" value : "+value);
        if (xm.nodeExists("/table/body/row[@id=\'" + id + "\']/column[@name=\'" + column.name + "\']")) {
            //node Exists
            xm.setXPathValue("/table/body/row[@id=\'" + id + "\']/column[@name=\'" + column.name + "\']", value);
        } else {
            //subtree none existend ... so add it ...
            if (xm.nodeExists("/table/head/column[@name=\'" + column.name + "\']")) {
                xm.addXPathNode("/table/body/row[@id=\'" + id + "\']/column[@name=\'" + column.name + "\']", value);
            } else {
                addColumn(column.name, column.type);
                xm.addXPathNode("/table/body/row[@id=\'" + id + "\']/column[@name=\'" + column.name + "\']", value);
            }
        }
    }

    /**This will insert a row into the XML database
     * @param dat XMLDataRow containing data you want to store
     */
    public void insertValue(XMLDataRow dat) throws JDOMException, Exception {
        //exists the column ? no => throw exception
        //does the row id not exists create a new row
        //does the row exist insert a new one
        if (dat != null) {
            if (dat.columnsNames.length == dat.values.length) {
                for (int i = 0; i < dat.columnsNames.length; i++) {
                    //System.out.println(" value "+dat.values[i]);
                    insertValue(dat.id, dat.columnsNames[i], dat.values[i]);
                }
            }
        }
    }

    /**Inserts value an amount of XMLDataRow's into the XML database
     * @param rows to insert
     */
    public void insertValues(XMLDataRow[] rows) throws Exception {
        for (int i = 0; i < rows.length; i++) {
            if (!hasRow(rows[i].id)) {
                insertValue(rows[i]);
            } else {
            }
        }
    }
    /**Finds the highest id in data rows and returns it
     * @return integer highest id found in the data set
     * @throws NoSuchFieldException if table is empty
     */
    public int getHighestID() throws NoSuchFieldException {
        try {
            List rows = XPath.selectNodes(xm.getDocument(), "/table/body/row/@id");
            if (rows != null) {
                int max = -1;

                for (Object c : rows) {
                    int p = ((Attribute) c).getIntValue();
                    if (p > max) {
                        max = p;
                    }
                }
                return max;
            }
        } catch (JDOMException ex) {
            Logger.getLogger(XMLDatabaseTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new NoSuchFieldException("no highest index found, table empty!");
    }

    /**determines whether the table holds a column labeled "name" or not
     * @param name column to test
     * @return true if column exists
     */
    public boolean hasColumn(String name) throws JDOMException {
        return (XPath.selectSingleNode(getDocument(), "/table/head/column[@name=\'" + name + "\']") != null);
    }

    /**Check if a row if given id exists
     * @param id of the row in the XML database
     * @return true if database keeps an row with given id
     */
    public boolean hasRow(int id) throws JDOMException {
        return xm.nodeExists("/table/body/row[@id=\'" + id + "\']");
    }

    /**Checks if a column of a given id is set. <b>not implemented yet</b>
     * @param id of the row to check
     * @param column checking for keeping data
     * @return true if column at id is not empty
     */
    public boolean isSet(int id, String column) throws JDOMException, Exception {
        XMLDataColumn[] cols = getXMLDataColumns();
        if (hasColumn(column)) {
            if (hasRow(id)) {
                return xm.getXPathValue("/table/body/row[@id=\'" + id + "\']/column[@name=\'" + column + "\']") != null;
            }
        }
        return false;
    }

    /**Returns the XMLDataColumn object assigned to name
     * @param name name of the column
     * @return XMLDataColumn assigned to name, null if this column does not exist.
     * @throws JDOMException
     * @throws Exception
     */
    public XMLDataColumn getColumn(String name) throws JDOMException, Exception {
        if (xm.nodeExists("/table/head/column[@name=\'" + name + "\']")) {
            XMLDataColumn res;

            boolean reference = xm.nodeExists("/table/head/column[@name=\'" + name + "\']/@reference-table");
            boolean indexcol = xm.nodeExists("/table/head/column[@name=\'" + name + "\']/@index");
            int index = -1;
            String refTab;
            String refKey;
            String type = xm.getXPathValue("/table/head/column[@name=\'" + name + "\']/@type");

            if (indexcol) {
                index = Integer.parseInt(xm.getXPathValue("/table/head/column[@name=\'" + name + "\']/@index"));
            }

            if (indexcol && reference) {
                refTab = xm.getXPathValue("/table/head/column[@name=\'" + name + "\']/@reference-table");
                refKey = xm.getXPathValue("/table/head/column[@name=\'" + name + "\']/@reference-key");
                res = new XMLDataColumn(name, type, refTab, refKey, index);
            } else {
                if (reference) {
                    refTab = xm.getXPathValue("/table/head/column[@name=\'" + name + "\']/@reference-table");
                    refKey = xm.getXPathValue("/table/head/column[@name=\'" + name + "\']/@reference-key");
                    res = new XMLDataColumn(name, type, refTab, refKey);
                } else {
                    if (indexcol) {
                        res = new XMLDataColumn(name, type, index);
                    } else {
                        res = new XMLDataColumn(name, type); //Create a new data column
                    }
                }
            }
            return res;
        }
        return null;
    }

    /**Returns all columns with setted index attribute
     * @return XMLColumns intended to be used as index tables
     */
    public XMLDataColumn[] getIndexColumns() {
        try {
            List columns = XPath.selectNodes(getDocument(), "/table/head/column[@index]"); //load head columns

           //System.err.println(" COLUMNS SIZE: "+columns.size());

            XMLDataColumn[] result = new XMLDataColumn[columns.size()]; //create a new array keeping the result

            for (int i = 0; i < columns.size(); i++) { //iterate through and assing each name to its type
                String cname = ((Element) columns.get(i)).getAttributeValue("name");
                String type = ((Element) columns.get(i)).getAttributeValue("type");
                int index = Integer.parseInt(((Element) columns.get(i)).getAttributeValue("index"));
                String refTab;
                String refKey;
                boolean reference = xm.nodeExists("/table/head/column[@name=\'" + cname + "\'][@type=\'" + type + "\']/@reference-table");
                //boolean indexcol = xm.nodeExists("/table/head/column[@name=\'" + cname + "\'][@type=\'" + type + "\']/@index");

                if (reference) {
                    refTab = ((Element) columns.get(i)).getAttributeValue("reference-table");
                    refKey = ((Element) columns.get(i)).getAttributeValue("reference-key");
                    result[i] = new XMLDataColumn(cname, type, refTab, refKey, index);
                } else {
                    result[i] = new XMLDataColumn(cname, type, index);
                }


            }
        return result;
        } catch (JDOMException ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**Data columns referencing another table
     * @return an array of columns referencing another table
     * @throws JDOMException
     */
    public XMLDataColumn[] getReferencingColumns() throws JDOMException {
        try {
            List columns = XPath.selectNodes(getDocument(), "/table/head/column[@reference-table][@reference-key]"); //load head columns

            XMLDataColumn[] result = new XMLDataColumn[columns.size()]; //create a new array keeping the result

            for (int i = 0; i < columns.size(); i++) { //iterate through and assing each name to its type
                String cname = ((Element) columns.get(i)).getAttributeValue("name");
                String type = ((Element) columns.get(i)).getAttributeValue("type");
                int index = -1; // not an index column
                String refTab;
                String refKey;
                boolean indexcol = xm.nodeExists("/table/head/column[@name=\'" + cname + "\'][@type=\'" + type + "\']/@index");

                if (indexcol) {
                    index = Integer.parseInt(((Element) columns.get(i)).getAttributeValue("index"));
                }

                if (indexcol) {
                    refTab = ((Element) columns.get(i)).getAttributeValue("reference-table");
                    refKey = ((Element) columns.get(i)).getAttributeValue("reference-key");
                    result[i] = new XMLDataColumn(cname, type, refTab, refKey, index);
                } else {
                    refTab = ((Element) columns.get(i)).getAttributeValue("reference-table");
                    refKey = ((Element) columns.get(i)).getAttributeValue("reference-key");
                    result[i] = new XMLDataColumn(cname, type, refTab, refKey);
                }
            }
            return result;
        } catch (JDOMException ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
        }
//        finally{
            return null;
//        }
    }

    /**Returns the XMLDataRow Object assigned to id
     * @param id of the data row
     * @return null if datarow does not exist, The Data Row Object if it does
     */
    public XMLDataRow getRow(int id) {
        try {
            if (hasRow(id)) {
                List columns = XPath.selectNodes(getDocument(), "/table/body/row[@id=" + id + "]/column");
                XMLDataColumn[] columnNames = new XMLDataColumn[columns.size()];
                String[] data = new String[columns.size()];

                int i = 0;

                for (Object o : columns) {
                    columnNames[i] = getColumn(((Element) o).getAttributeValue("name"));
                    data[i] = ((Element) o).getText();
                    i++;
                }
                return new XMLDataRow(id, columnNames, data);
            }
        } catch (Exception ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    /**Index check on row. Generates the MD5 Sum over the index colums and querys the XML Document for a fitting row
     * @param row to check its existence
     * @return true if row exists
     * @throws JDOMException should not occur unless your XML Document is not iniatialized correctly
     */
    public boolean rowExists(XMLDataRow row) throws JDOMException {
        String md5 = makeChecksum(row);
        return xm.nodeExists("/table/body/row[@checksum=\'" + md5 + "\']");
    }

    /**Finds a row by checksum. 
     * @param checkSum to lookup
     * @return Data row if found, null otherwise
     */
    public XMLDataRow findRowByChecksum(String checkSum) {
        try {
            List columns = XPath.selectNodes(getDocument(), "/table/body/row[@checksum=\'" + checkSum + "\']/column");
            if (columns != null) {
                XMLDataColumn[] xcol = new XMLDataColumn[columns.size()];
                String[] values = new String[columns.size()];
                int id = 0;
                int i = 0;
                for (Object o : columns) {
                    Element e = (Element) o;
                    id = Integer.parseInt(e.getParentElement().getAttributeValue("id"));
                    String name = e.getAttributeValue("name");
                    String value = e.getText();
                    xcol[i] = getColumn(name);
                    values[i] = value;
                    i++;
                }
                return new XMLDataRow(id, xcol, values);
            }
        } catch (JDOMException ex) {
            Logger.getLogger(XMLDatabaseTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(XMLDatabaseTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**Returns the database name of the XML Database
     * @return name of the database
     */
    public String getDatabaseName() {
        return db;
    }

    /**Returns the name of the table
     * @return name of the table
     */
    public String getTableName() {
        return tableName;
    }

    /**Returns an array containing structures describing a database column
     * @return all columns as XMLDataColumn array
     */
    public XMLDataColumn[] getXMLDataColumns() throws JDOMException {
        List columns = XPath.selectNodes(getDocument(), "/table/head/column"); //load head columns

        //System.out.println(" COLUMNS : "+columns.size());

        XMLDataColumn[] result = new XMLDataColumn[columns.size()]; //create a new array keeping the result

        for (int i = 0; i < columns.size(); i++) { //iterate through and assing each name to its type
            String cname = ((Element) columns.get(i)).getAttributeValue("name");
            String type = ((Element) columns.get(i)).getAttributeValue("type");
            //System.out.println("COLUMN name : "+cname+" TYPE : "+type);
            int index = -1; // not an index column
            String refTab;
            String refKey;
            boolean reference = xm.nodeExists("/table/head/column[@name=\'" + cname + "\'][@type=\'" + type + "\']/@reference-table");
            boolean indexcol = xm.nodeExists("/table/head/column[@name=\'" + cname + "\'][@type=\'" + type + "\']/@index");

            if (indexcol) {
                index = Integer.parseInt(((Element) columns.get(i)).getAttributeValue("index"));
            }

            if (indexcol && reference) {
                refTab = ((Element) columns.get(i)).getAttributeValue("reference-table");
                refKey = ((Element) columns.get(i)).getAttributeValue("reference-key");
                result[i] = new XMLDataColumn(cname, type, refTab, refKey, index);
            } else {
                if (reference) {
                    refTab = ((Element) columns.get(i)).getAttributeValue("reference-table");
                    refKey = ((Element) columns.get(i)).getAttributeValue("reference-key");
                    result[i] = new XMLDataColumn(cname, type, refTab, refKey);
                } else {
                    if (indexcol) {
                        result[i] = new XMLDataColumn(cname, type, index);
                    } else {
                        result[i] = new XMLDataColumn(cname, type); //Create a new data column
                    }
                }
            }
        }

        return result; //return the final array
    }

    /**Returns all columns as String array
     * @return String array of all column names. This does not contain type definition see getXMLDataColumns instead.
     */
    public String[] getColumns() throws JDOMException {
        List res = XPath.selectNodes(xm.getDocument(), "/table/head/column/@name");

        String[] list = new String[res.size()];

        for (int a = 0; a < res.size(); a++) {
            list[a] = ((Attribute) res.get(a)).getValue();
            //System.out.println("Column : "+list[a]);
        }
        return list;
    }
    //finds and returns an existing column hidden in a XMLDataColumn set
    //returns the element if it was found, null otherwise.

    private XMLDataColumn findColumn(XMLDataColumn[] columnSet, String column) {

        for (XMLDataColumn a : columnSet) {
            if (a.name.equals(column)) {
                return a;
            }
        }
        return null;
    }

    /**Returns the data in a XMLDataRow array
     * @return array of all data
     */
    public XMLDataRow[] getData() throws JDOMException {
        List rows = XPath.selectNodes(getDocument(), "/table/body/row"); //load each data row
        List head = XPath.selectNodes(getDocument(), "/table/head/column"); //load head to assign correct columns and data types
        XMLDataColumn[] columnSet = new XMLDataColumn[head.size()];

        int i = 0;
        for (Object column : head) {
            String cname = ((Element) column).getAttributeValue("name");
            String type = ((Element) column).getAttributeValue("type");
            int index = -1; // not an index column
            String refTab;
            String refKey;
            boolean reference = xm.nodeExists("/table/head/column[@name=\'" + cname + "\'][@type=\'" + type + "\']/@reference-table");
            boolean indexcol = xm.nodeExists("/table/head/column[@name=\'" + cname + "\'][@type=\'" + type + "\']/@index");

            if (indexcol) {
                index = Integer.parseInt(((Element) column).getAttributeValue("index"));
            }

            if (indexcol && reference) {
                refTab = ((Element) column).getAttributeValue("reference-table");
                refKey = ((Element) column).getAttributeValue("reference-key");
                columnSet[i] = new XMLDataColumn(cname, type, refTab, refKey, index);
            } else {
                if (reference) {
                    refTab = ((Element) column).getAttributeValue("reference-table");
                    refKey = ((Element) column).getAttributeValue("reference-key");
                    columnSet[i] = new XMLDataColumn(cname, type, refTab, refKey);
                } else {
                    if (indexcol) {
                        columnSet[i] = new XMLDataColumn(cname, type, index);
                    } else {
                        columnSet[i] = new XMLDataColumn(cname, type); //Create a new data column
                    }
                }
            }



            //columnSet[i] = new XMLDataColumn(cname, type);
            i++;
        }

        XMLDataRow[] result = new XMLDataRow[rows.size()];
        //System.out.println("Rows.size() : "+rows.size());
        i = 0;

        for (Object row : rows) { //dispatch each row
            int id = Integer.parseInt(((Element) row).getAttributeValue("id")); //ID of the dataset
            List columns = XPath.selectNodes(getDocument(), "/table/body/row[@id=\'" + id + "\']/column"); //select column names and data
            String md5 = "";

            try{
                md5 = ((Attribute)XPath.selectSingleNode(getDocument(), "/table/body/row[@id=\'" + id + "\']/@checksum")).getValue();
            }catch(Exception e){}
            
            String[] data = new String[columns.size()];
            XMLDataColumn[] datacolumns = new XMLDataColumn[columns.size()];

            int j = 0;

            for (Object column : columns) {
                String fieldname = ((Element) column).getAttributeValue("name");
                data[j] = ((Element) column).getText();
                datacolumns[j] = findColumn(columnSet, fieldname);
                j++;
            }
            result[i] = new XMLDataRow(id, datacolumns, data);
            result[i].checksum = md5;
            i++;
        }
        return result;
    }

    /**returns the JDOM Document for this xml file
     * @return the JDOM Document of this database table
     */
    public Document getDocument() {
        return xm.getDocument();
    }
    /**Generates the MD5 Checksum of a data row. The checksum is defined by the tables index columns noted with a index attribute starting at 0. -1 means the column is not an index column.
     * @param row to calculate its checksum from
     * @return md5sum over the the index columns
     */
    public String makeChecksum(XMLDataRow row) {
        XMLDataColumn[] indexColumns = getIndexColumns();
//        for(XMLDataColumn t : indexColumns){
//            System.out.println("checksum "+t);
//        }
        ToolHelper.quicksort(indexColumns, 0, indexColumns.length - 1);


        String message = "";
        for (XMLDataColumn c : indexColumns) {
            message += row.getData(c);
        }

        byte[] b = ToolHelper.md5sum(message.getBytes());

        return ToolHelper.bytesToHexString(b);
    }

    /**finalize document, add hashes to rows and the table head
     *this makes it easy to detect changes among the rows and makes it easy to update data where necessary.
     * 
     */
    public void finalizeDocument() throws NoSuchFieldException { //TODO : make this deterministic
        Document doc = xm.getDocument();
        try {
            String message = "";

            List indexColumns = XPath.selectNodes(doc, "/table/head/column[@index]");

            Element[] columns = new Element[indexColumns.size()];

            for (int i = 0; i < indexColumns.size(); i++) {
                columns[i] = (Element) XPath.selectSingleNode(doc, "/table/head/column[@index=\'" + i + "\']");
            }

            List rowData = XPath.selectNodes(doc, "/table/body/row");

            if (indexColumns != null) {
                for (Object r : rowData) {
                    int id = Integer.parseInt(((Element) r).getAttributeValue("id"));

                    XMLDataRow row = getRow(id);


                    for (Element e : columns) {
                        message += row.getData(e.getAttributeValue("name"));
                    }
                    //System.out.println("message " + message);
                    byte[] b = ToolHelper.md5sum(message.getBytes());
                    String checksum = ToolHelper.bytesToHexString(b);

                    ((Element) r).setAttribute("checksum", checksum);
                    message = "";
                    checksum = "";
                }
            } else {
                throw new NoSuchFieldException("There is at least one index row required!");
            }


        } catch (JDOMException ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
        }
    }
    /**Appends a new Document to this one,
     */
    public void append(XMLDatabaseTable tab) throws JDOMException, Exception {
        //append head
        //System.out.println("Document :\n"+tab.toString());
        XMLDataRow[] data = tab.getData();

        //System.out.println(data[0].values[0]);

        insertValues(data);
    }

    //Check if a table exists.
    /**Checks if a table exists.
     * @param filename to check
     * @return true if XML File exists
     */
    public static boolean tableExists(String filename) throws IOException {
        File check = new File(filename);
        return (check.exists() && check.isFile());
    }

    //Check if a table exists.
    /**Checks if a table exists.
     * @param filename to check
     * @return true if XML File is accessible to the user
     */
    public static boolean tableAccessible(String filename) throws IOException {
        File check = new File(filename);
        return (check.exists() && check.isFile() && check.canRead() && check.canWrite());
    }
    //

    /**Writes the content of this Document into a file
     * @param filename to write
     */
    public void writeFile(String filename) throws IOException {
        //System.out.println("writing "+filename);

        File out = new File(filename);

        if (!out.exists()) {
            out.createNewFile();
        }

        if (out.exists() && out.isFile() && out.canRead()) {
            FileWriter fw = new FileWriter(out);



            fw.write(xm.toString());
            fw.close();
        } else {
            //throw some exceptions here
        }
    }

    /**load the xml file
     * @param filename and path to load
     */
    public void loadFile(String filename) throws IOException, JDOMException {
        File in = new File(filename);

        if (in.exists() && in.isFile() && in.canRead()) {
            xm = new XMLManipulator(XMLManipulator.readFileAsString(filename));
        } else {
            throw new IOException("no such file!");
        }
    }

    /**returns a String of the xml content of this XMLDatabaseTable
     */
    @Override
    public String toString() {
        return xm.toString();
    }
    /**Assigns a referenced table to a column
     * @param column column referencing
     * @param table foreign table referenced
     * @param key foreign key
     */
    public void addReference(String column, String table, String key) {
        try {
            XMLDataColumn c = findColumn(getXMLDataColumns(), column);
            
            if(c!=null){
                c.refTable = table;
                c.refKey   = key;
            }
            
        } catch (JDOMException ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
        }
    }
    /**Returns a list of tables columns of this table are referencing.
     * @return list of foreign tables, null if no reference is found.
     */
    public String[] getReferencedTables() throws JDOMException{
        XMLDataColumn[] refCols = getReferencingColumns();
        String[] refTables = new String[refCols.length];

        for(int i = 0;i<refCols.length;i++){
            refTables[i]=refCols[i].refTable;
        }

        return refTables;
    }


    /**Standalone testing function
     */
    public static void main(String[] argV) throws Exception {
        XMLDatabaseTable table = new XMLDatabaseTable("test", "db");
        System.out.println(table.toString());


        XMLDataColumn[] cols = new XMLDataColumn[3];
        cols[0] = new XMLDataColumn("name", "varchar(20)", "testtable", "testkey", 0);
        cols[1] = new XMLDataColumn("surname", "varchar(20)");
        cols[2] = new XMLDataColumn("adress", "varchar(100)");

        table.addColumns(cols);

        XMLDataRow[] rows = new XMLDataRow[3];
        String[] data = new String[3];
        data[0] = "test first";
        data[1] = "surtest first";
        data[2] = "testaddress";
        rows[0] = new XMLDataRow(0, cols, data);

        //System.err.println("Row 0 : " + rows[0]);

        String[] data2 = new String[3];
        data2[0] = "test second";
        data2[1] = "surtest second";
        data2[2] = "testaddress second";
        rows[1] = new XMLDataRow(1, cols, data2);

        String[] data3 = new String[3];
        data3[0] = "test third";
        data3[1] = "surtest third";
        data3[2] = "testaddress third";
        rows[2] = new XMLDataRow(2, cols, data3);

        table.insertValues(rows);
        table.finalizeDocument();

        XMLDataRow[] dataRows = table.getData();

        System.out.println("Table : "+table);

        for(XMLDataRow r : dataRows){
            System.out.println("Data : "+r);
        }

        //System.out.println("Data : " + table.findRowByChecksum(table.makeChecksum(rows[2])));

    }
}
