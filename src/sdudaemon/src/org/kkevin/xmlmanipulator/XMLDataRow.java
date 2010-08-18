/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kkevin.xmlmanipulator;

/**
 * This Class describes a Row of data in a XMLDatabaseTable.
 * @category
 * @package
 * @subpackage
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
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
