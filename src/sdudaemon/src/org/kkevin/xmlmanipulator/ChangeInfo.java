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
/**This class stores Metainformation for the manipulator plug in
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
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
