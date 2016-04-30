/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Param.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Param
 */


package parser;

import lowlevel.Data;
import lowlevel.FuncParam;

public class Param 
{
    public Param(String ID, boolean isArray)
    {
        this.id = ID;
        this.isArray = isArray;
    }
    
    private final String id;
    private final boolean isArray;
    
    public String getId()
    {
        return this.id;
    }
    
    public boolean isArray()
    {
        return this.isArray;
    }
    
    public FuncParam genCode()
    {
        return new FuncParam(Data.TYPE_INT, id, isArray);
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("int ")
          .append(id)
          .append(isArray ? " [ ] " : "");
        return sb.toString();
    }
    
    public void printMe(String spaces) {
        System.out.println(spaces + "Param");
        spaces += "   ";
        System.out.println(spaces + "int");
        System.out.println(spaces + "ID: " +this.id);
        if(this.isArray()) {
            System.out.println(spaces + "[");
            System.out.println(spaces + "]");
        }
    }
}
