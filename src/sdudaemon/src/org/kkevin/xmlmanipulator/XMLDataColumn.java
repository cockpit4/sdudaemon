/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kkevin.xmlmanipulator;

/**
 * This Class describes a data column of a XMLDatabaseTable.
 * @category Webscraper
 * @package org.kkevin.xmlmanipulator
 * @subpackage org.kkevin.xmlmanipulator
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
 */
public class XMLDataColumn {
	/**name of this column
	 */
	public String name;
	/**Type definition of this column
	 */
	public String type;
	/**Constructor generating a new data column
	 * @param name of the column
	 * @param type of the column
	 */
	public XMLDataColumn(String name, String type){
		this.name = name;
		this.type = type;
	}
}
