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

/**
 * This Class describes a Row of data in a XMLDatabaseTable.
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class XMLDataRow {
	/**ID of this row
	 */
	public int id;
	/**Columns of this row, containing type definitions
	 */
	public XMLDataColumn[] columnsNames;
	/**values for each column its an 1:1 map to columns which means column[0] is assigned to values[0]
	 */
	public String[] values;

        public String checksum;
	/**Constructor assigning column descriptions data to a table row with id
	 * @param id id of the data row
	 * @param columns columns to assign
	 * @param data data 1:1 assigned to columns
	 */
	public XMLDataRow(int id, XMLDataColumn[] columns, String[] data){
		this.id = id;
		this.columnsNames = columns;
		this.values = data;
	}
        /** creates a new Data Row object with a specified checksum
         * @param id of the data set
         * @param columns assigned to
         * @param data values to store
         * @param checksum over the datavalue specified by the index columns
         */
	public XMLDataRow(int id, XMLDataColumn[] columns, String[] data, String checksum){
		this.id = id;
		this.columnsNames = columns;
		this.values = data;
	}
        /**Returns the data assigned to a specified column
         * @param col column to retrieve the data from
         * @return null if column does not exist, the value string otherwise
         */
        public String getData(XMLDataColumn col){
            int i = 0;
            for(XMLDataColumn c : columnsNames){
                //System.err.println("XMLDataRow.getData(XMLDataColumn) : columnnames : "+c.name);
                //System.err.println("XMLDataRow.getData(XMLDataColumn) : columnname: "+col.name);
                if(c.name.equals(col.name)){
                    //System.err.println("Value fits!");
                    return values[i];
                }
                i++;
            }

            return null;
        }

        public String getData(String col){
            int i = 0;
            for(XMLDataColumn c : columnsNames){
                if(c.name.equals(col)){
                    return values[i];
                }
                i++;
            }

            return null;
        }
        /* (no-javadoc)
         * @see java.lang.Object.toString()
         */
        public String toString(){
            String res = "id : "+id+"\n";
            for(int i = 0 ; i<values.length;i++){
                res += columnsNames[i].name + " : "+values[i]+"\n";
            }
            res+="MD5 : "+checksum;
            return res;
        }
}
