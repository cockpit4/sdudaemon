/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cockpit4.xmlmanipulator;

import org.omg.CORBA.SystemException;

/**
 *
 * @author kneo
 */
public abstract class XPathParser {
    public static final int TYPE_NODE = 0; // //node or /node
    public static final int TYPE_PREDICATE = 1; //[@id='2']
    public static final int TYPE_NUMBER_PREDICATE = 2; //[4]
    public static final int TYPE_FUNCTION = 3; //[4]
    public static final int TYPE_MATH_EXPRESSION = 4; //[func()]
    public static final int TYPE_BOOLEAN_EXPRESSION = 5; //[d<3]
    public static final int TYPE_ATTRIBUTE = 6; //@name

    public static final int TYPE_ABSOLUTE_PATH=7;
    public static final int TYPE_RELATIVE_PATH=8;

    
    private String expression;

    public XPathParser(String exp){
        this.expression = exp;
    }

    private void debugPrint(int pos,int delim){

        System.err.println("---------------------------\nExpression :");
        System.err.println(expression);
        for(int i = 0 ; i<pos;i++){
            System.err.print(" ");
        }
        System.err.print("^");
        if(delim>pos){
            for(int i = pos;i<delim-1;i++){
                System.err.print(".");
            }
            System.err.println("^\n---------------------------");
        }
    }

    public void start(){
        String stack = expression;
        int delimiter = 0;
        for(int pos = 0 ; pos<expression.length();){  //disect string per character
            char character = stack.charAt(pos);
            System.err.println("current character : "+character);
            switch(character){
                case '/':
                        int posbrak = stack.indexOf("[",pos+1);
                        int posslash = stack.indexOf("/",pos+1);
                        
                        if(posbrak<posslash)
                            delimiter = posbrak;
                        else
                            delimiter = posslash;

                        debugPrint(pos, delimiter);
                        parse(stack.substring(pos, delimiter),pos,(delimiter-pos),XPathParser.TYPE_NODE);
                    break;

                case '[':
                    
                    break;

                default:
                    break;
            }



            pos+=delimiter;
        }
    }

    public abstract void parse(String token,int pos,int len,int type);

    public static void main(String[] argV){
        XPathParser xp = new XPathParser("/test/expression/with[2][@predicate][@id='s']/@test") {
            @Override
            public void parse(String token, int pos, int len, int type) {
                switch(type){
                    case XPathParser.TYPE_NODE:
                        System.err.println("Token : "+token);
                    break;

                    case XPathParser.TYPE_PREDICATE:
                    break;

                    case XPathParser.TYPE_NUMBER_PREDICATE:
                    break;

                    case XPathParser.TYPE_BOOLEAN_EXPRESSION:
                    break;

                    case XPathParser.TYPE_FUNCTION:
                    break;

                    case XPathParser.TYPE_MATH_EXPRESSION:
                    break;

                    case XPathParser.TYPE_ATTRIBUTE:
                    break;
                }
            }
        };

        xp.start(); //start parsing
    }
}
