package org.kkevin.xmlmanipulator;
/**This class handles XML Node Attributes described by an XPath
 * @category Webscraper
 * @package org.kkevin.xmlmanipulator
 * @subpackage org.kkevin.xmlmanipulator
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
*/
public class XPathNodeAttribute{
	private String name;
	private String value;
	/**Constructor
	*@param name of the attribute 
	*@param value of this attribute
	*/
	public XPathNodeAttribute(String name,String value){
		this.name = name;
		if(value == null)
			this.value = "";
		else
			this.value = value;
	}
	/**Returns the name of an attribute
	*@return name of the attribute
	*/
	public String getName(){
		return name;
	}
	/**Returns the value of an attribute
	*@return attributes value
	*/
	public String getValue(){
		return value;
	}
	/**Return a string representation of this attribute in xpath format
	*@return handled attribute in xpath format
	*/
	public String toString(){
		return "[@"+name+"=\'"+value+"\']";
	}
}
