/*
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: GameBoard.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: 
 */

package parser;

public class Var extends Expression
{
    public Var(String ID, Expression arrayLength)
    {
        this.ID = ID;
        this.dereferenceExpression = arrayLength;
    }
    
    public Var(String ID)
    {
        this.ID = ID;
        this.dereferenceExpression = null;
    }
    
    private final String ID;
    private final Expression dereferenceExpression;
    
    public boolean isArray()
    {
        return dereferenceExpression != null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ID).append(" ");
        
        if (isArray())
        {
            sb.append(" [ ").append(dereferenceExpression).append(" ] ");
        }
        
        return sb.toString();
    }
    
    public void printMe(String spaces) {
        System.out.println(spaces + "Var");
        spaces += "    ";
        System.out.println(spaces + "ID: " + this.ID);
        System.out.println(spaces + "[");
        this.dereferenceExpression.printMe(spaces);
        System.out.println(spaces + "[");
    }
}
