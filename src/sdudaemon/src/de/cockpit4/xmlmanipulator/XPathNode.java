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
import java.util.ArrayList;
/**
 * This class describes an XPath Node
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
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
