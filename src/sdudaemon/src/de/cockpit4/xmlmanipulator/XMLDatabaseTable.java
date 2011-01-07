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

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
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
public final class XMLDatabaseTable{
	private XMLManipulator xm;
	private String name;
	private String db;
        private int datalength = 0;
	/**Constructor creating a new empty XML-Database Table
	 * @param db name of the target database
	 * @param name name of the table
	 */
	public XMLDatabaseTable(String name, String db) throws Exception{
		this.db   = db;
		this.name = name;
		xm = new XMLManipulator("");
		xm.addXPathNode("/table[@name=\'"+name+"\'][@database=\'"+db+"\']", "");
		xm.addXPathNode("/table[@name=\'"+name+"\'][@database=\'"+db+"\']/head", "");
		xm.addXPathNode("/table[@name=\'"+name+"\'][@database=\'"+db+"\']/body", "");
	}

	/**Constructor creates a new object based on an existing filename
	 * @param filename file to load
	 */
	public XMLDatabaseTable(String filename) throws IOException, Exception{
		loadFile(filename);


		Object res = XPath.selectSingleNode(getDocument(), "/table");

		name = ((Element) res).getAttributeValue("name");
		db   = ((Element) res).getAttributeValue("database");
	}
	//creates a new table column labeled "name" of type "type" if it exists this method does nothing
	public void addColumn(String name,String type) throws JDOMException{
		if(!xm.nodeExists("/table/head/column[@name=\'"+name+"\']")){
                    try {
                        //System.out.println("Document "+xm);
                        xm.addXPathNode("/table/head/column[@name=\'" + name + "\'][@type=\'" + type + "\']", "");
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(XMLDatabaseTable.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(XMLDatabaseTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}

        public void addColumns(XMLDataColumn[] columns) throws JDOMException{
            if(columns!=null){
                for(XMLDataColumn c : columns){
                    //System.out.println(" name :"+c.name+" type : "+c.type);
                    addColumn(c.name, c.type);
                }
            }
        }

        /**Counts the data sets stored in this database
         * @return count of Datarows stored in this table
         */
        public int getSize(){
           try {
            datalength = XPath.selectNodes(getDocument(), "/table/body/row").size();
            } catch (JDOMException ex) {
                Logger.getLogger(XMLDatabaseTable.class.getName()).log(Level.SEVERE, null, ex);
            }
           return datalength;
        }

	/**creates a non-existent row value, or updates it if it exists
	 * a none existend column will also be added to the table head.
	 * @param id of the data row
	 * @param column which to assign value
	 * @param value to assign
	 */
	public void insertValue(int id,XMLDataColumn column, String value) throws JDOMException, Exception{
		//exists the column ? no => throw exception
		//does the row id not exists create a new row
		//does the row exist insert a new one
                //System.out.println("id : "+id+" column : "+column.name+" type : "+column.type+" value : "+value);
		if( xm.nodeExists("/table/body/row[@id=\'"+id+"\']/column[@name=\'"+column.name+"\']")  ){
			//node Exists
			xm.setXPathValue("/table/body/row[@id=\'"+id+"\']/column[@name=\'"+column.name+"\']", value);
		}
		else{
			//subtree none existend ... so add it ...
			if(xm.nodeExists("/table/head/column[@name=\'"+column.name+"\']")){
				xm.addXPathNode("/table/body/row[@id=\'"+id+"\']/column[@name=\'"+column.name+"\']", value);
			}
			else{
				addColumn(column.name,column.type);
				xm.addXPathNode("/table/body/row[@id=\'"+id+"\']/column[@name=\'"+column.name+"\']", value);
			}
		}
	}

	/**This will insert a row into the XML database
	 * @param dat XMLDataRow containing data you want to store
	 */

	public void insertValue(XMLDataRow dat) throws JDOMException, Exception{
		//exists the column ? no => throw exception
		//does the row id not exists create a new row
		//does the row exist insert a new one
		if(dat!=null){
			if(dat.columnsNames.length == dat.values.length){
				for(int i = 0 ; i<dat.columnsNames.length;i++){
					insertValue(dat.id,dat.columnsNames[i],dat.values[i]);
				}
			}
		}
	}

	/**Inserts value an amount of XMLDataRow's into the XML database
	 * @param rows
	 */
	public void insertValues(XMLDataRow[] rows) throws Exception{
		for(XMLDataRow row : rows){
			if(row.columnsNames.length == row.values.length){
				for(int i = 0 ; i<row.columnsNames.length; i++){
					insertValue(row.id,row.columnsNames[i],row.values[i]);
				}
			}
		}
	}

	/**determines whether the table holds a column labeled "name" or not
	 * @param name column to test
	 * @return true if column exists
	 */
	public boolean hasColumn(String name) throws JDOMException{
		return (XPath.selectSingleNode(getDocument(),"/table/head/column[@name=\'"+name+"\']") != null);
	}

	/**Check if a row if given id exists
	 * @param id of the row in the XML database
	 * @return true if database keeps an row with given id
	 */
	public boolean hasRow(int id) throws JDOMException{
		return xm.nodeExists("/table/body/row[@id=\'"+id+"\']");
	}
	/**Checks if a column of a given id is set. <b>not implemented yet</b>
	 * @param id of the row to check
	 * @param column checking for keeping data
	 * @return true if column at id is not empty
	 */
	public boolean isSet(int id, String column) throws JDOMException, Exception{
                XMLDataColumn[] cols = getXMLDataColumns();
                if(hasColumn(column)){
                    if(hasRow(id)){
                        return xm.getXPathValue("/table/body/row[@id=\'"+id+"\']/column[@name=\'"+column+"\']") != null;
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
        public XMLDataColumn getColumn(String name) throws JDOMException, Exception{
            if(xm.nodeExists("/table/head/column[@name=\'"+name+"\']")){
                return new XMLDataColumn(name, xm.getXPathValue("/table/head/column[@name=\'"+name+"\']/@type"));
            }
            return null;
        }
        /**Returns the XMLDataRow Object assigned to id
         * @param id of the data row
         * @return null if datarow does not exist, The Data Row Object if it does
         */
        public XMLDataRow getRow(int id){
            try {
                if (hasRow(id)) {
                    List columns = XPath.selectNodes(getDocument(), "/table/body/row[@id="+id+"]/column");
                    XMLDataColumn[] columnNames = new XMLDataColumn[columns.size()];
                    String[] data = new String[columns.size()];

                    int i = 0;

                    for(Object o : columns){
                        columnNames[i] = getColumn(((Element )o).getAttributeValue("name"));
                        data[i] = ((Element )o).getText();
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

	/**Returns the database name of the XML Database
	 * @return name of the database
	 */
	public String getDatabaseName(){
		return db;
	}

	/**Returns the name of the table
	 * @return name of the table
	 */
	public String getTableName(){
		return name;
	}

	/**Returns an array containing structures describing a database column
	 * @return all columns as XMLDataColumn array
	 */
	public XMLDataColumn[] getXMLDataColumns() throws JDOMException{
		List columns = XPath.selectNodes(getDocument(), "/table/head/column"); //load head columns

		//System.out.println(" COLUMNS : "+columns.size());

		XMLDataColumn[] result = new XMLDataColumn[columns.size()]; //create a new array keeping the result

		for(int i = 0 ; i<columns.size(); i++){ //iterate through and assing each name to its type
			String cname = ((Element) columns.get(i)).getAttributeValue("name");
			String type = ((Element) columns.get(i)).getAttributeValue("type");
			
			result[i] = new XMLDataColumn(cname,type); //Create a new data column
		}

		return result; //return the final array
	}
	/**Returns all columns as String array
	 * @return String array of all column names. This does not contain type definition see getXMLDataColumns instead.
	 */
	public String[] getColumns() throws JDOMException{
		List res = XPath.selectNodes(xm.getDocument(), "/table/head/column/@name");

		String[] list = new String[res.size()];

		for(int a = 0 ; a < res.size(); a++){
			list[a] = ((Attribute) res.get(a)).getValue();
			//System.out.println("Column : "+list[a]);
		}
		return list;
	}
	//finds and returns an existing column hidden in a XMLDataColumn set
	//returns the element if it was found, null otherwise.
	private XMLDataColumn findColumn(XMLDataColumn[] columnSet, String column){

		for(XMLDataColumn a : columnSet){
			if(a.name.equals(column)){
				return a;
			}
		}
		return null;
	}
	/**Returns the data in a XMLDataRow array
	 * @return array of all data
	 */
	public XMLDataRow[] getData() throws JDOMException{
		List rows                 = XPath.selectNodes(getDocument(), "/table/body/row"); //load each data row
		List head                 = XPath.selectNodes(getDocument(), "/table/head/column"); //load head to assign correct columns and data types
		XMLDataColumn[] columnSet = new XMLDataColumn[head.size()];

		int i = 0;
		for(Object column : head){
			String fieldname  = ((Element) column).getAttributeValue("name");
			String type  = ((Element) column).getAttributeValue("type");
			columnSet[i] = new XMLDataColumn(fieldname,type);
			i++;
		}

		XMLDataRow[] result  = new XMLDataRow[rows.size()];
                //System.out.println("Rows.size() : "+rows.size());
		i=0;
		
		for(Object row : rows){ //dispatch each row
			int id       = Integer.parseInt(((Element) row).getAttributeValue("id")); //ID of the dataset
			List columns = XPath.selectNodes(getDocument(), "/table/body/row[@id=\'"+id+"\']/column"); //select column names and data

			String[]        data        = new String[columns.size()];
			XMLDataColumn[] datacolumns = new XMLDataColumn[columns.size()];

			int j = 0;

			for(Object column : columns){
				String fieldname    = ((Element) column).getAttributeValue("name");
				data[j]        = ((Element) column).getText();
				datacolumns[j] = findColumn(columnSet,fieldname);
				j++;
			}
			result[i] = new XMLDataRow(id,datacolumns,data);
			i++;
		}
		return result;
	}

	/**returns the JDOM Document for this xml file
	 * @return the JDOM Document of this database table
	 */
	public Document getDocument(){
		return xm.getDocument();
	}

	//
	//
	/**finalize document, add hashes to rows and the table head
	 *this makes it easy to detect changes among the rows and makes it easy to update data where necessary.
	 * <b>not stable yet!</b>
	 */
	public void finalizeDocument(){ //TODO : make this deterministic
		Document doc = xm.getDocument();
		try {
			List rows = XPath.selectNodes(doc, "/table/body");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			if(rows instanceof Element){
				md5.reset();
				for(Object row : rows){
					List columns = XPath.selectNodes(rows, "/row/column");
					StringBuilder hash = new StringBuilder(1000);
					
					for(Object column : columns){
						hash.append(((Element) column).getAttributeValue("name"));
					}
					byte[] res = hash.toString().getBytes();

					md5.update(res);
					res = md5.digest();

					String md = new String(res);

					((Element) row).setAttribute("hash", md); //store the md5 hash for eah line
				}
			}
		}
		catch (NoSuchAlgorithmException ex){
			Logger.getLogger(XMLDatabaseTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (JDOMException ex) {
			Logger.getLogger(XMLDatabaseTable.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//Appents two XMLDatabaseTables
	/**Appends a new Document to this one,
	 */
	public void append(XMLDatabaseTable tab) throws JDOMException, Exception{
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
	public static boolean tableExists(String filename) throws IOException{
		File check = new File(filename);
		return (check.exists() && check.isFile());
	}
	//
	/**Writes the content of this Document into a file
	 * @param filename to write
	 */
	public void writeFile(String filename) throws IOException{
		//System.out.println("writing "+filename);

		File out = new File(filename);

		if(!out.exists()){
			out.createNewFile();
		}

		if(out.exists() && out.isFile() && out.canRead()){
			FileWriter fw = new FileWriter(out);



			fw.write(xm.toString());
			fw.close();
		}
		else{
			//throw some exceptions here
		}
	}

	/**load the xml file
	 * @param filename and path to load
	 */
	public void loadFile(String filename) throws IOException,Exception{
		File in = new File(filename);
		
		if(in.exists() && in.isFile() && in.canRead()){
			xm = new XMLManipulator(XMLManipulator.readFileAsString(filename));
		}
		else{
			throw new Exception("no such file!");
		}
	}
	/**returns a String of the xml content of this XMLDatabaseTable
	 */
	@Override
	public String toString(){
		return xm.toString();
	}
	/**Standalone testing function
	 */
	public static void main(String[] argV) throws Exception{
            XMLDatabaseTable table =  new XMLDatabaseTable("test","db");
            System.out.println(table.toString());
            XMLDataColumn[] cols = new XMLDataColumn[3];
            cols[0]=new XMLDataColumn("name", "varchar(20)");
            cols[1]=new XMLDataColumn("surname", "varchar(20)");
            cols[2]=new XMLDataColumn("adress", "varchar(100)");

            table.addColumns(cols);

            XMLDataRow[] rows = new XMLDataRow[2];
            String[] data = new String[3];
            data[0]="test";
            data[1]="surtest";
            data[2]="testaddress";
            rows[0] = new XMLDataRow(0, cols, data);


            data[0]="test second";
            data[1]="surtest second";
            data[2]="testaddress second";
            rows[1] = new XMLDataRow(1, cols, data);
            table.insertValues(rows);

            System.out.println(table.getRow(0));
	}
}

