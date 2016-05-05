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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lowlevel.CodeItem;
import lowlevel.Data;

public class VarDeclaration extends Declaration 
{
    public VarDeclaration(String id)
    {
        super(id);
        this.arraySize = -1;
        this.isArray = false;
    }
        
    public VarDeclaration(String id, int arraySize) 
        throws CMinusParserError
    {
        super(id);
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
    
    @Override
    public int getDeclType()
    {
        return DECL_TYPE_VAR;
    }
    
    @Override
    public String toString()
    {
        String string = "int" + " " + getId();
        
        if (isArray)
        {
            string += " [" + arraySize + "]"; 
        }
        
        string += " ;";
        
        return string;
    }
    
    @Override
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
    
    @Override
    public CodeItem genCode(ArrayList<String> globals)
    {
        String id = getId();
        
        if (!globals.contains(id))
        {
            globals.add(id);
        }
        else
        {
            try
            {
                System.out.println("Duplicate global found: " + id);
                throw new CodeGenerationException("Duplicate global found");
            }
            catch (CodeGenerationException ex)
            {
                Logger.getLogger(
                    VarDeclaration.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                System.exit(1);
            }
        }
        
        return new Data(Data.TYPE_INT, getId());
    }
}
