/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Var.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Var
 */


package parser;

import lowlevel.Function;
import lowlevel.Operation;

public class Var 
    extends Expression
{
    public Var(String id, Expression array)
    {
        this.id = id;
        this.dereferenceExpression = array;
    }
    
    public Var(String ID)
    {
        this.id = ID;
        this.dereferenceExpression = null;
    }
    
    private final String id;
    private final Expression dereferenceExpression;
    
    public boolean isArray()
    {
        return dereferenceExpression != null;
    }
    
    public String getId()
    {
        return id;
    }
    
    @Override
    public ExpressionType getExpressionType()
    {
        return ExpressionType.VAR;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ");
        
        if (isArray())
        {
            sb.append(" [ ").append(dereferenceExpression).append(" ] ");
        }
        
        return sb.toString();
    }
    
    public void printMe(String spaces) {
        System.out.println(spaces + "Var");
        spaces += "    ";
        System.out.println(spaces + "ID: " + this.id);
        if(this.isArray()) {
            System.out.println(spaces + "[");
            this.dereferenceExpression.printMe(spaces);
            System.out.println(spaces + "]");
        }
        
    }
    
    @Override
    public Operation genCode(Function function){return null;}
}
