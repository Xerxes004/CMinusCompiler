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
    public Var(String ID, int arrayLength)
    {
        this.ID = ID;
        this.arrayLength = arrayLength;
        this.isArray = true;
    }
    
    public Var(String ID)
    {
        this.ID = ID;
        this.arrayLength = -1;
        this.isArray = false;
    }
    
    private final String ID;
    private final int arrayLength;
    private final boolean isArray;
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ID).append(" ");
        
        if (isArray)
        {
            sb.append(" [ ").append(arrayLength).append(" ] ");
        }
        
        return sb.toString();
    }
}
