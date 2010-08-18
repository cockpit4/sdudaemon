package org.kkevin.xmlmanipulator;
/**This class stores Metainformation for the manipulator plug in
 * @category Webscraper
 * @package org.kkevin.xmlmanipulator
 * @subpackage org.kkevin.xmlmanipulator
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
*/
public class ChangeInfo{
	public String xpath;
	public String value;
	public byte type;
	/**
	*Determine if a change info changes anything ...
	*/
	public static final byte ACTION_CHANGE = 1;
	/**
	*Determine if a change info adds anything ...
	*/
	public static final byte ACTION_ADD    = 2;
	/**
	*Determine if a change info removes anything ...
	*/
	public static final byte ACTION_REMOVE = 3;

	/**Constructor to initialize the class
	*@param xpath XPath to 
	*@param value
	*@param type 
	*/
	public ChangeInfo(String xpath, String value,byte type){
		this.xpath = xpath;
		this.value = value;
		this.type = type;
	}
	/**
	*Returns a string representation for this change info
	*@return String telling a debuglogger whats coming on next...
	*/
	public String toString(){

		switch(type){
			case 1: //change
				return "CHANGE : "+xpath+" TO "+value;
			case 2: //add
				return "ADD : "+value+" TO "+xpath;
			case 3:
				return "REMOVE NODE/ATTRIBUTE AT: "+xpath;
			default:
				return "UNKNOWN ACTION";
		}
	}
}
