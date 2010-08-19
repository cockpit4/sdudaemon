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
package org.kkevin.xmlmanipulator;

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
}
