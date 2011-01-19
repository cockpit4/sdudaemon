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

/**This class contains an XPath and enables it to access specific nodes and attributes of an XPath as well as composing a new XPath from your original XPath of specific depth.
 * For example you have an XPath expression like : //street/house/floor[1]/room[1]/@door-closed and you want to get the path only to second node so just execute : (new XPathDissambler(...Path here...)).composePathOf(1,true) which result //street/house
 * This class is part of a XML construction and reconstruction framework the sdudaemon needs for data adaptation and pre-database table construction (XML Files the updater just loads into the database).
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

	public static void main(String[] argV) throws Exception{
            String bla = "/config/projects[@id='2'][@path='/a/mmg/']/test/bla/suelz::text()";

            
	    System.err.println("Entered : "+bla);

	    XPathDissembler xd = new XPathDissembler(bla);
            
            for(int i = 0 ; i<xd.getPathDepth(); i++){
                System.out.println(xd.getNode(i).getName());
            }


	    System.err.println("reconstructed : "+xd.composePathOf(xd.getPathDepth()-1, bla.matches("^([/]{1,2})?")));
	}
        
        //solving the path separator problem by simple character stuffing
        private static String prepareString(String exp){
            String result = "";
            boolean quote = false;
            
            for(int i = 0; i<exp.length();i++){
                if(exp.charAt(i)=='/'&&!quote){
                    result += exp.charAt(i)+"-";
                    continue;
                }

                if(exp.charAt(i)=='\''){
                    quote = !quote;
                    result+=exp.charAt(i);
                    continue;
                }

                result+=exp.charAt(i);
            }
            //System.err.println("Expression : "+result);
            return result;
        }

	/**Create a new XPathDissebler instance with xpath
	*@param xpath you want to dissemble
	*/
	public XPathDissembler(String xpath) throws Exception{
		//check if xpath maches an xpath expression
		//XPath RegExp 
		//
                //System.out.println("XPath : "+xpath);

		this.xpath = prepareString(xpath);
		nodes = new ArrayList<XPathNode>();

		String xpreg_start    = "^([/]{1,2})?"; // match / or // at the begin of a path sequence
		String xpreg_attr     = "(@[A-Za-z]+[0-9]*[-]*[:]*[A-Za-z0-9]+)";   // match an single attribute like : //@style or @dt:dt
		String xpreg_node     = "[A-Za-z]+[0-9]*[-]*[:]*[A-Za-z0-9]*(\\(\\))?";       // a node without an predicate like : //body and //body/child::text()
		String xpreg_node_pre = "(\\[@[A-Za-z]+[0-9]*[-]*[:]*[A-Za-z0-9]*[=][\\'][A-Za-z0-9-_./\\\\:\\.\\(\\)]*\\'\\])?"; //or an node with an predicate like : //body[@id='body0']
		String xpreg_node_num = "(\\[([0-9]+)\\])"; // or a node with number quantifier like : //table/tr[10]
                
                //deprecated! --v
		String xpreg = "^([/]{1,2})?((@[\\w]+)|([\\w]+(\\[@[\\w]+=['][\\w]+[']\\])*))((/([\\w]+(\\[@[\\w]+=[\\'][\\w]+[\\']\\])*))*|@[\\w]+)";
		
		String simple_node           = xpreg_node;                          //match a simple node
		String simple_attribute      = xpreg_attr;                          //match a simple attribute
		String node_number           = xpreg_node+"("+xpreg_node_num+")+";  //match a node with one or more number(s) identifier(s) such as //td[2] to get the every second TD element of a table for example
		String node_predicate        = xpreg_node+"("+xpreg_node_pre+")+";  //match a node with one or more predicate(s)
		String node_number_predicate = xpreg_node+"("+xpreg_node_num+"|"+xpreg_node_pre+")+";

		String[] tokens = this.xpath.split("/-"); // split the path into partial strings for dispatching
		if(tokens.length>0){
//			for(String s : tokens){
//				if(s.length()>0){
//					System.out.println("Token : "+s);
//				}
//			}

			for(String s : tokens){
				if(s.length()>0){

					if(s.matches(simple_node)){
						//System.out.println("simple NODE Detected!");

						nodes.add(getSimpleNode(s));
						continue; //work over this ...
					}

					if(s.matches(node_predicate)){ //
						//System.out.println("NODE WITH PREDICATE Detected!");

						nodes.add(getPredicatedNode(s));
						continue; // work over this ...
					}

					if(s.matches(node_number)){
					    //System.out.println("NODE WITH NUMBER QUANTIFIER Detected!");
					    nodes.add(getPredicatedNode(s));
					    continue;
					}

					if(s.matches(node_number_predicate)){
					    //System.out.println("NODE WITH NUMBER QUANTIFIERS AND PREDICATES Detected!");
					    nodes.add(getPredicatedNode(s));
					    continue;
					}

					if(s.matches(simple_attribute)){ //An attribute is the last token of an xpath so finalize and break the loop
						//System.out.println("ATTRIBUTE Detected!");

						if(nodes.size()>0)
							getLastNode(s,nodes.get(nodes.size()-1)); //just add the last attribute with value "" to the last node
						else
							nodes.add(getLastNode(s,null)); //your xpath contains just an attribute so make an empty node and add an attribute with an empty value

						pointsToAttribute = true;
						break;
					}
                                        throw new Exception("Token "+s+" not recognized!");
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
		String[] tokens = token.split("[\\[||=|\\]\']");

		int mode = 0;
		String attName = null; // to store the Attribute name...

		for(String t : tokens){
			//System.out.println("\tToken: "+t);
			if(t.length()>0)
				switch(mode){
					case 0: // first encounter of a String
						//System.out.println("\tNode: "+t);
						mode = 1; // next token has to be an attribute
						result = new XPathNode(t);
					break;
	
					case 1: //further Strings
						//System.out.println("\tAttribute:"+t);
						if(t.charAt(0) != '@'){
						    XPathNodeAttribute att = new XPathNodeAttribute(t,null,true); // append a new numeral
						    result.addAttribute(att);
						    continue;
						}

						mode = 2; //next token must be an value
						attName = t;
					break;

					case 2:
						//System.out.println("\tValue:"+t);

						XPathNodeAttribute att = new XPathNodeAttribute(attName.substring(1, attName.length()),t);
	
						result.addAttribute(att);
						attName = ""; // drop the name to make sure any attribute even if it keeps an empty value is added
						mode = 1; // next token is an attribute name again
					break;
				}
		}
		
		if(attName!=null && !attName.equals("")){
			result.addAttribute(new XPathNodeAttribute(attName.substring(1, attName.length()),null));
		}
		//System.out.println("\tNode: "+result);
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
}
