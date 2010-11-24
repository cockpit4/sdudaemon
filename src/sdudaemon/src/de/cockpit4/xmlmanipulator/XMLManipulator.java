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

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.jdom.xpath.*;

import java.io.StringReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
*This class simply manipulates an XML-Document
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */
public class XMLManipulator{
	private Document xmlDocument;

	/**
	*Constructor loads a xml formated String as Document
	*@param xmlContent String to load. Make sure it is wellformed XML
	*/
	public XMLManipulator(String xmlContent) throws Exception{
		//System.out.println("creating object and loading XML Document...");
		//System.out.println("XML-CONTENT:\n"+xmlContent+"\n-------");
		
		if(xmlContent == null ? "" != null : !xmlContent.equals(""))
			try{
				xmlDocument = (new SAXBuilder()).build(new StringReader(xmlContent));
			}
			catch(JDOMParseException e){
				xmlDocument = new Document();
			}
		else
			xmlDocument = new Document();

		//System.out.println("XML-DOCUMENT:\n"+xmlDocument.toString());
		//if(xmlDocument==null){
		//	System.out.println("DOCUMENT IS NULL!");
		//}
		//System.err.println("OK");
	}
	
	/**
	*Loads a File and returns a String of its content
	*@param filePath readable file to load
	*@return content of the loaded file.
	*/
	public static String readFileAsString(String filePath) throws IOException{
	    if(filePath == null ? "" != null : !filePath.equals("")){

		File file = new File(filePath);

		if(!file.exists()){
		    FileWriter fw = new FileWriter(file);
		    fw.close();
		}

		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead=0;
		while((numRead=reader.read(buf)) != -1){
			fileData.append(buf, 0, numRead);
		}
		reader.close();
		return fileData.toString();
	    }
	    else{
		return "";
	    }
	}

	/**Sets XML content.
	 *
	 * @param xmlContent to set
	 */
	public void setXMLContent(String xmlContent) throws JDOMException, IOException{
		if(xmlContent == null ? "" != null : !xmlContent.equals(""))
			try{
				xmlDocument = (new SAXBuilder()).build(new StringReader(xmlContent));
			}
			catch(JDOMParseException e){
				xmlDocument = new Document();
			}
		else
			xmlDocument = new Document();
	}

	/**
	*returns the JDOM Documenttree as String
	*@return String containing the document
	*/
	public String returnXMLContent() throws Exception{
		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat());
		return out.outputString(xmlDocument);
	}

	/**
	*returns the String representation of a Element or Attribute selected by XPath
	*@param xpath select Object, throws Exception if not valid or non-existend
	*@return String of XPath evaluation result
	*/
	public String getXPathValue(String xpath) throws Exception{
		Object result = org.jdom.xpath.XPath.selectSingleNode(xmlDocument,xpath);

		//if(result==null){
		//	System.out.println("RESULT RETURNS NULL!");
		//}

		if(result != null){
		    if(result instanceof Attribute){
			    return ((org.jdom.Attribute) result).getValue();
		    }

		    if(result instanceof Element){
			    return ((org.jdom.Element) result).getText();
		    }
		}
		else{
		    addXPathNode(xpath, "");
		}
		
		return null;
	}
	/**
	*Sets a XPath resulting node value.
	*If the node does not exist it will  be created
	*@param xpath to evaluate
	*@param value to set
	*/
	public void setXPathValue(String xpath, String value) throws Exception{
		Object result = org.jdom.xpath.XPath.selectSingleNode(xmlDocument,xpath);
		
		//if(result==null){
		//	System.out.println("RESULT RETURNS NULL!");
		//}

		if(value == null){
		    value = "";
		}

		if(result != null){
		    if(result instanceof Attribute){
		    //	System.out.println("RESULT WAS ATTRIBUTE!");
			    ((org.jdom.Attribute) result).setValue(value);
		    }

		    if(result instanceof Element){
		    //	System.out.println("RESULT WAS ELEMENTNODE!");
			    ((org.jdom.Element) result).setText(value);
		    }
		}
		else{ // if the node does not exist create it
		    addXPathNode(xpath, value);
		}
	}
	/**This function adds a new Node to an valid xpath, to keep the well-form of a document, this function works this way:<br>
	*- Begin with the first node defined in the XPath<br>
	*- if it just an attribute add a new node named "node" containing this attribute with an empty value if "value" is empty<br>
	*to the document root node<br>
	*- if the document has none this node becomes the root node<br>
	*- if it is an node check if any node in this document matches this node with all his attributes (first appearence only)<br>
	*- <b>Matches if a node has all the same attributes and values</b><br>
	*- if there is one append the rest of the nodes described by the xpath expression (this will add the attributes contained in predicates too)<br>
	*- if there is no matching node append the path to the document root node<br>
	*- if it's an empty document, create this root node using the first node discribed by the xpath<br>
	*@param xpath xpath string to traverse
	*@param value value to set into the xpath described node position of the document tree
	*/
	public void addXPathNode(String xpath, String value) throws JDOMException,IllegalArgumentException{
		//System.out.println("call->addXPathNode(\""+xpath+"\",\""+value+"\") ...");

		XPathDissembler xd        = new XPathDissembler(xpath); //decomposite an xpath
		Object         result     = null;
		try{
			result = org.jdom.xpath.XPath.selectSingleNode(xmlDocument,xpath); //evaluate XPath in order to find a valid path
		}
		catch(IllegalStateException e){
			//System.out.println("Result returns null!");
			result = null;
		}

		boolean	relative  = xpath.substring(0,2).equals("//"); // determine whether an xpath is absolut or relativ

		if(relative){
			//System.out.println("Relative path detected!");
		}


		if(result != null){ // path exists and is either a Element or an Attribute
			//System.out.println("\tPath matches document!");
			if(result instanceof Element){ // if it is an Element set its text
				//System.out.println("\tPath points to node ... setting value");
				((Element) result).setText(value);
			}
			else{ // else set the attribute to value
				if(result instanceof Attribute){
				//System.out.println("\tPath points to attribute ... setting value");
					((Attribute) result).setValue(value);
				}
			}
		}
		else{	
			//Elsewise the Path does not exists, 
			//so try if any node in the document matches the first node name of our XPath
			//if yes append our path to the parent if it has attributes or to the result node if of this node
			//else append out path to the root node of the document if there is any
			//System.out.println("\tPathfinding failed ... reconstructing ... Path length : "+xd.getPathDepth());
			int start = 0;
			for(int i = 0; i<xd.getPathDepth(); i++){
				try{
					//System.out.println("\ttrying to match :"+xd.composePathOf(i,true));
					Object test = XPath.selectSingleNode(xmlDocument,xd.composePathOf(i,relative));

					if(test!=null){
						result = XPath.selectSingleNode(xmlDocument,xd.composePathOf(i,relative));
						start ++;
						//System.out.println("\tPath found!");
					}
				}
				catch(IllegalStateException e){
					//result = XPath.selectSingleNode(xmlDocument,xd.composePathOf(i-1,true));;
					//System.out.println("\tPath failed breaking...");
					result = null;
					break;
				}
			}
			
			if(result != null){ //yes there is a node matching our first xpath node
				//System.out.println("\tFirst path node matches ... appending data");
				//int start = 0;
				Element parent = ((Element) result);
				Element add;

				if(parent != null){ //does our node have a parent?
					//yes so append out path to this nodes parent
					for(int i = start; i<xd.getPathDepth();i++){ //for each node do
						add = new Element(xd.getNode(i).getName()); // create a new element

						if(xd.getNode(i).hasAttributes()){ // check if our xpath contains a predicated node
							//System.out.println("\t\t"+xd.getNode(i).getName()+" Node has Attributes");

							for(int j = 0; j<xd.getNode(i).attributeCount();j++){ //if yes add every attribute and its value to our node

								//System.out.println("\t\t adding ... "+xd.getNode(i).getAttribute(j).getName());
								add.setAttribute(xd.getNode(i).getAttribute(j).getName(),xd.getNode(i).getAttribute(j).getValue());
							}
						}
						parent.addContent(add);
						parent = add;
					}

					if(xd.pathPointsToAttribute()){ //determine if path describes an attribute ?
						//result = XPath.selectSingleNode(xmlDocument,xd.toString()); //yes so get the attribute
						parent.setAttribute(xd.getNode(xd.getPathDepth()-1).getAttribute(xd.getNode(xd.getPathDepth()-1).attributeCount()-1).getName(),value);
	
					} // see if value is set and add its content as text if necessary 
					else{ // set Text
						if(!value.equals("") && value != null){ // is there some Text to add?
							parent.setText(value);
						}
					}
				}
				else{ // our node has no parent so add our path elements as sub elements of our result node
					//yes so append out path to this nodes parent
					
					for(int i = 1; i<xd.getPathDepth();i++){ //for each node do
						add = new Element(xd.getNode(i).getName()); // create a new element

						if(xd.getNode(i).hasAttributes()){ // check if our xpath contains a predicated node
							for(int j = 0; j<xd.getNode(i).attributeCount();j++){ //if yes add every attribute and its value to our node
								add.setAttribute(xd.getNode(i).getAttribute(j).getName(),xd.getNode(i).getAttribute(j).getValue());
							}
						}

						((Element) result).addContent(add);
						result = add;
					}

					if(xd.pathPointsToAttribute()){ //determine if path describes an attribute ?
						//result = XPath.selectSingleNode(xmlDocument,xd.toString()); //yes so get the attribute
						((Element) result).setAttribute(xd.getNode(xd.getPathDepth()-1).getAttribute(xd.getNode(xd.getPathDepth()-1).attributeCount()-1).getName(),value);
					}
					else{ //Set Text
						if(!value.equals("") && value != null){ // is there some Text to add?
							((Element) result).setText(value);
						}
					}
				}
			}
			else{	// no matching node found
				//so check for a document root node
				//if there is any append our path
				//else the first node of our xpath defines the new documents root node
				//append all nodes of the path afterwards 
				//System.out.println("\t Path does not match ! ");

				Element node = new Element(xd.getNode(0).getName());
			
				if(xmlDocument.hasRootElement()){
					xmlDocument.getRootElement().addContent(node);
					//System.out.println("\t Document has a root node");
				}
				else{
					//System.out.println("\t Document has no root node");
					xmlDocument.setRootElement(node);
				}

				if(xd.getNode(0).hasAttributes()){ //add all his attributes to our new node
					//System.out.println("\t root "+xd.getNode(0).getName()+" node has Attributes!");
					for(int j = 0; j < xd.getNode(0).attributeCount();j++){
						//System.out.println("\t\t"+xd.getNode(0).getAttribute(j).getName());
						node.setAttribute(xd.getNode(0).getAttribute(j).getName(),xd.getNode(0).getAttribute(j).getValue());
					}
				}

				Element root = node;

				for(int i = 1; i < xd.getPathDepth(); i++){ // add all other nodes
					//System.out.println("\t Adding further nodes!");
					node = new Element(xd.getNode(i).getName()); // create a new node 

					if(xd.getNode(i).hasAttributes()){ // add all its attributes
						for(int j = 0; j < xd.getNode(i).attributeCount();j++){
							node.setAttribute(xd.getNode(i).getAttribute(j).getName(),xd.getNode(i).getAttribute(j).getValue());
						}
					}

					root.addContent(node); // add the content to the root
					root = node; // set the root a level deeper 
				}

				if(xd.pathPointsToAttribute()){ //determine if path describes an attribute ?
					root.setAttribute(xd.getNode(xd.getPathDepth()-1).getAttribute(xd.getNode(xd.getPathDepth()-1).attributeCount()-1).getName(),value);
				}
				else{ //Set Text
					if(!value.equals("") && value != null){ // is there some Text to add?
						root.setText(value);
					}
				}
			}
		}
	}
	/** Supposed to remove a node and or just its value if it does no value is defined, not implemented yet
	*
	*/
	public void removeXPathNode(String xpath, String value){
		//Object result = org.jdom.xpath.XPath.selectSingleNode(xmlDocument,xpath);
	}
	/**Returns true if a node to the assigned "xpath" exists, false otherwise.
	*@param xpath to check
	*@return result of XPath query.
	*/
	public boolean nodeExists(String xpath)throws JDOMException{
		return XPath.selectSingleNode(xmlDocument,xpath) != null;
	}

	/**Returns the jdom.Document holded in this XMLManipulator Object
	*@return the Content of this XMLManipulator as jdom Document object (reference)
	*/
	public Document getDocument(){
		return xmlDocument;
	}

	public static void main(String [ ] args){
		try{
			//test case here ...
			
			String test = "<?xml version=\"1.0\"?>\n<db></table ready=\"false\"></db>";
			XMLManipulator xm = new XMLManipulator(test);

			for(String a : args){
				//System.out.println(xm);

				xm.addXPathNode(a,"");
				System.out.println(xm);
			}

			for(String a : args){
				System.out.print(a+" ");
				if(xm.nodeExists(a)){
					System.out.println("XPath Node exists!");
				}
			}

			if(xm.nodeExists("//dc[@a='b']")){
				System.out.println("Single attribute query works too.");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{

		}		
	}
	/**Returns the xml document as a String
	*@return the xml document manipulated by this object
	*/
	public String toString(){
		try{
			return returnXMLContent();
		}
		catch(Exception e){
			return "";
		}
	}
}


