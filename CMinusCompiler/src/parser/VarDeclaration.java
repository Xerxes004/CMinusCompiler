/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: CMinusParser.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a variable declaration
 */


package parser;

import scanner.Token.TokenType;

public class VarDeclaration extends Declaration 
{
    public VarDeclaration(String ID)
    {
        super("int", ID);
        this.arraySize = -1;
        this.isArray = false;
    }
        
    public VarDeclaration(String ID, int arraySize) 
        throws CMinusParserError
    {
        super("int", ID);
        this.arraySize = arraySize;
        this.isArray = true;
    }

    private final int arraySize;
    private final boolean isArray;
    
    public int arraySize()
    {
        return arraySize;
    }
    
    public boolean isArray()
    {
        return isArray;
    }
    
    public int getType()
    {
        return TYPE_VAR;
    }
    
    @Override
    public String toString()
    {
        String string = returnType() + " " + getId();
        
        if (isArray)
        {
            string += " [" + arraySize + "]"; 
        }
        
        string += " ;";
        
        return string;
    }
    
    public void printMe(String spaces) {
        System.out.println(spaces + "VarDeclaration");
        spaces += "    ";
        System.out.println(spaces + "int");
        System.out.println(spaces + "ID: " + this.getId());
        if(this.isArray()) {
            System.out.println(spaces + "[");
            System.out.println(spaces + "NUM: " + this.arraySize());
            System.out.println(spaces + "]");
        }
        System.out.println(spaces + ";");
    }
    
}
