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

import de.cockpit4.sdudaemon.tool.Sortable;

/**
 * This Class describes a data column of a XMLDatabaseTable.
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class XMLDataColumn implements Sortable{
	/**name of this column
	 */
	public String name;
	/**Type definition of this column
	 */
	public String type;

        /**Determines if this column describes an index Column.
         * This is required to create a dataset fingerprint deterministicaly.
         * All columns assigned to a index will be used to generate a unique fingerprint of a Dataset.
         * The index is a integer started from 0. -1 means the column is not an index column.
         * It also defines the order how assign data to create a fingerprint.
         */
        public int index = -1; //default column is not an index column

        public String refTable = null;
        public String refKey = null;

	/**Constructor generating a new data column
	 * @param name of the column
	 * @param type of the column
	 */
	public XMLDataColumn(String name, String type){
		this.name = name;
		this.type = type;
	}

	/**Constructor generating a new data column
	 * @param name of the column
	 * @param type of the column
         * @param index index identfier of this column
	 */
	public XMLDataColumn(String name, String type,int index){
		this.name = name;
		this.type = type;
                this.index = index;
	}
        /**Creates a new column referencing to another table
         * @param name of the column
         * @param type of the column
         * @param table to reference to
         * @param key to reference to (foreign key)
         */
        public XMLDataColumn(String name,String type,String table,String key){
		this.name = name;
		this.type = type;
                this.refTable = table;
                this.refKey = key;
                //System.out.println("Column : name : "+name+" type : "+type+" index : "+index+" references : "+refTable+"."+refKey);
        }
        /**Creates a new Database Column referencing to another table, marked as index column
         * @param name of the column
         * @param type of the column
         * @param table table to reference to
         * @param key foreign key to reference to
         * @param index identifier this column has
         */
        public XMLDataColumn(String name,String type,String table,String key,int index){
		this.name = name;
		this.type = type;
                this.refTable = table;
                this.refKey = key;
                this.index = index;
        }
        /**Specify a column to reference to another table. Similiar to foraign key procedure.
         * The database management system keeps an eye on the consistency so XMLDatabaseTable does not know if reference is correct.
         * @param table to reference to
         * @param key foreign key
         */
        public void referenceTo(String table,String key){
            this.refTable = table;
            this.refKey = key;
        }

    public boolean equalTo(Sortable t) {
        XMLDataColumn n = (XMLDataColumn) t;
        return n.index == index;
    }

    public boolean inequalTo(Sortable t) {
        XMLDataColumn n = (XMLDataColumn) t;
        return n.index != index;
    }

    public boolean lesserThan(Sortable t) {
        XMLDataColumn n = (XMLDataColumn) t;
        return n.index > index;
    }

    public boolean greaterThan(Sortable t) {
        XMLDataColumn n = (XMLDataColumn) t;
        return n.index < index;
    }

    public boolean lesserOrEqualTo(Sortable t) {
        XMLDataColumn n = (XMLDataColumn) t;
        return n.index >= index;
    }

    public boolean greaterOrEqualTo(Sortable t) {
        XMLDataColumn n = (XMLDataColumn) t;
        return n.index <= index;
    }

    public void swap(Sortable t) {
        XMLDataColumn n = (XMLDataColumn) t;
        XMLDataColumn tmp = new XMLDataColumn(name, type, refTable, refKey, index);

        name = n.name;
        type = n.type;
        refTable = n.refTable;
        refKey = n.refKey;
        index = n.index;

        n.index = tmp.index;
        n.name = tmp.name;
        n.type = tmp.type;
        n.refTable = tmp.refTable;
        n.refKey = tmp.refKey;        
    }
    @Override
    public String toString(){
        return "name : "+name+" type : "+type+" index : "+index+" references : "+refTable+"."+refKey;
    }
}
