package org.kkevin.xmlmanipulator;
import java.util.ArrayList;
/**
 * This class describes an XPath Node
 * @category Webscraper
 * @package org.kkevin.xmlmanipulator
 * @subpackage org.kkevin.xmlmanipulator
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
 */
public class XPathNode{
	private String name; // name of a node
	private ArrayList<XPathNodeAttribute> attributes = null; //each attribute of a node
	private String text; // everything not a node

	/**Constructor
	*@param name of the xpath node
	*/
	public XPathNode(String name){
		this.name=name;
		attributes = new ArrayList<XPathNodeAttribute>();
	}

	/**Name getter
	*@return name of a specific node
	*/
	public String getName(){
		return name;
	}

	/**Get an attribute
	*@param i index of the attribute
	*@return xpath attribute if existent, throws exception if it doesn't
	*/
	public XPathNodeAttribute getAttribute(int i){
		if(i>=0 && i<attributes.size()){
			return attributes.get(i);
		}
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	/**Add an attribute to a Node
	*@param att attribute to add
	*/
	public void addAttribute(XPathNodeAttribute att){
		if(att != null){
			attributes.add(att);
		}
		else
			throw new NullPointerException();
	}

	/**Determine whether a node has attributes or not
	*@return true if node has at least one attribute
	*/
	public boolean hasAttributes(){
		return (attributes.size()>0);
	}

	/**Return how many attributes exist
	*@return absolute count of attributes hold by this node
	*/
	public int attributeCount(){
		return attributes.size();
	}

	/**String representation of the node including every attribute in xpath notation
	*@return xpath notation of a node which might contain attributes
	*/
	public String toString(){
		String result = "";
		if(hasAttributes()){
			for(XPathNodeAttribute a : attributes){
				result += a;
			}	
		}

		return name+result;
	}
}
