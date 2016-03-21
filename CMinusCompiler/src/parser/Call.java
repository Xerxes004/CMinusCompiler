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

import java.util.ArrayList;

public class Call 
{
    public Call(ArrayList<Expression> args)
    {
        this.args = args;
    }
    
    ArrayList<Expression> args;
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        for (Expression e : args)
        {
            sb.append(e.toString());
        }
        
        return sb.toString();
    }
}
