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
/**This class handles XML Node Attributes described by an XPath
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class XPathNodeAttribute{
	private String name;
	private String value;
	private boolean numberIdentifier=false;
	/**Constructor
	*@param name of the attribute 
	*@param value of this attribute
	*/
	public XPathNodeAttribute(String name,String value){
		this.name = name;
		if(value == null)
			this.value = null;
		else
			this.value = value;
	}

	/**Constructor
	*@param name of the attribute
	*@param value of this attribute
	*@param numberIdentifier determine if attribute is a ordinal identifier
	*/
	public XPathNodeAttribute(String name,String value,boolean numberIdentifier){
	    if(!numberIdentifier){
		this.name = name;
		if(value == null)
			this.value = null;
		else
			this.value = value;
	    }else{
		this.name = name; // if attribute looks like "node[0]" just store the 0 as name so the Dissambler can compile the path correctly
		this.value = null;
		this.numberIdentifier = true;
	    }
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
	/**Determine if the attribute describes a node by a numeral such as //nodes/node[0]
	 * @return true if attribute enumerates a selected node
	 */
	public boolean isNumberIdentifier(){
	    return numberIdentifier;
	}
	/**Return a string representation of this attribute in xpath format
	*@return handled attribute in xpath format
	*/
	public String toString(){
	    if(numberIdentifier){
		return "["+name+"]";
	    }else{
		if(value == null){
		    return "[@"+name+"]";
		}
		return "[@"+name+"=\'"+value+"\']";
	    }
	}
}
