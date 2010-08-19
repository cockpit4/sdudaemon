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
import java.util.ArrayList;

/**This class contains an XPath and enables it to access specific nodes and attributes of an XPath as well as composing a new XPath from your original XPath of specific depth.
  *
  * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
  * @version 1.0
  *
  *  Copyright (c) 2010 cockpit4 GmbH
  *  sdudeamon is released under the MIT license
  */

public class XPathDissembler{
	private ArrayList<XPathNode> nodes = null;
	private String xpath;
	private boolean pointsToAttribute = false;

	/**Create a new XPathDissebler instance with xpath
	*@param xpath you want to disseble
	*/
	public XPathDissembler(String xpath){
		//check if xpath maches an xpath expression
		//XPath RegExp 
		//
		this.xpath = xpath;
		nodes = new ArrayList<XPathNode>();

		String xpreg = "([/]{1,2})?((@[\\w]+)|([\\w]+(\\[@[\\w]+=['][\\w]+[']\\])*))((/([\\w]+(\\[@[\\w]+=[\\'][\\w]+[\\']\\])*))*|@[\\w]+)";
		//TODO:change regex to match some special characters like "_-"
		String node      = "[\\w]+"; //match a node 
		String attribute = "@[\\w]+"; // match an attribute
		String predicate = "([[\\w]+][\\[@[\\w]+=\'[\\w]+\'\\]]*)"; // match a predicate
		

		String[] tokens = xpath.split("/"); // split the path into partial strings for dispatching
		if(tokens.length>0){
			for(String s : tokens){
				if(s.length()>0){
					//System.out.println("Token : "+s);
				}
			}

			for(String s : tokens){
				if(s.length()>0){

					if(s.matches(node)){
						//System.out.println("NODE Detected!");

						nodes.add(getSimpleNode(s));
						continue; //work over this ...
					}

					if(s.matches(predicate)){ //
						//System.out.println("PREDICATE Detected!");

						nodes.add(getPredicatedNode(s));
						continue; // work over this ...
					}

					if(s.matches(attribute)){ //An attribute is the last token of an xpath so finalize and break the loop
						//System.out.println("ATTRIBUTE Detected!");

						if(nodes.size()>0)
							getLastNode(s,nodes.get(nodes.size()-1)); //just add the last attribute with value "" to the last node
						else
							nodes.add(getLastNode(s,null)); //your xpath contains just an attribute so make an empty node and add an attribute with an empty value

						pointsToAttribute = true;
						break;
					}
				}
			}
			//System.out.println("Fetching finished!");
		}
		else
			throw new IllegalArgumentException();
	}
	//function to fetch nodes and predicates to create a new node containing all its attributes and values
	private static XPathNode getPredicatedNode(String token){
		//System.out.println("\tAdding predicated node : "+token);
		XPathNode result = null;
		String[] tokens = token.split("[\\[|@|=|\\]\']");

		int mode = 0;
		String attName = ""; // to store the Attribute name...

		for(String t : tokens){
			if(t.length()>0)
				switch(mode){
					case 0: // first encounter of a String
						//System.out.println("\tNode: "+t);
						mode = 1; // next token has to be an attribute
						result = new XPathNode(t);
					break;
	
					case 1: //further Strings
						//System.out.println("\tAttribute:"+t);
						mode = 2; //next token must be an value
						attName = t;
					break;

					case 2:
						//System.out.println("\tValue:"+t);

						XPathNodeAttribute att = new XPathNodeAttribute(attName,t);
	
						result.addAttribute(att);
						attName = ""; // drop the name to make sure any attribute even if it keeps an empty value is add
						mode = 1; // next token is an attribute name again
					break;
				}
		}

		if(attName == null ? "" != null : !attName.equals("")){
			result.addAttribute(new XPathNodeAttribute(attName,""));
		}

		return result;
	}
	//function to fetch a simple node without predicates.
	private static XPathNode getSimpleNode(String token){
		//System.out.println("\tAdding simple node and attributes: "+token);
		return new XPathNode(token);
	}
	//this function is called if only an attribute or an node is left to dispatch
	private static XPathNode getLastNode(String token, XPathNode lastNode){
		if(lastNode == null){
			//System.out.println("\tfound Attribute only!");
			XPathNodeAttribute att = new XPathNodeAttribute(token.substring(1,token.length()),"");
			XPathNode node = new XPathNode("node");
			node.addAttribute(att);
			return node;
		}
		else{
			//System.out.println("\tfound full Path!");
			lastNode.addAttribute(new XPathNodeAttribute(token.substring(1,token.length()),""));
			return lastNode;
		}
	}

	/**Get the depth of the Path 
	*@return how many nodes a path contains
	*/
	public int getPathDepth(){
		return nodes.size();
	}

	/**Returns a specific node
	*@param depth Level of your node
	*@return a specific node of the xpath if it exists thows exception if not
	*/

	public XPathNode getNode(int depth){
		//System.out.println("\t getNode("+depth+")"+" nodes.size() : "+nodes.size()+"(depth > 0) && (depth < nodes.size()):"+((depth > 0) && (depth < nodes.size()))+" depth :"+depth);

		if((depth >= 0) && (depth < nodes.size())){
			return nodes.get(depth);
		}
		else{
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**Composes an XPath of level depth. relative determines whether you want a relative resulting xpath or make it absolute.<br>
	*Example : Let "/a/test[@t='2']/path" be our inialized xpath so <br>
	* compose(2,true) will return "//a/test[@t='2']"<br>
	* compose(1,false) will return "/a" instead.
	*@param depth positive definite depth of your XPath you want to return
	*@param relative in laymens terms : shall there be one or two slashes in front of the path ?
	*@return the XPath composition of all nodes of depth (depth) including predicates
	*/
	public String composePathOf(int depth,boolean relative){
		String result = "";

		if(relative){
			result = "/";
		}

		for(int i = 0 ; i <= depth; i++){
			result += "/"+nodes.get(i).toString();
		}

		return result;
	}

	public String toString(){
		return xpath;
	}


	public boolean pathPointsToAttribute(){
		return pointsToAttribute;
	}

	public static void main(String[] args){
		if(args.length>0){
			String xpath = args[0];

			XPathDissembler xd = new XPathDissembler(xpath);

			System.out.println("Path : "+xd.composePathOf(xd.getPathDepth(),true));
		}
		else
			System.out.println("Pass XPath as first CL argument!");
	}
}
