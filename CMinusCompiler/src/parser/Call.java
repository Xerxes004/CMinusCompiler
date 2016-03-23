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
    extends Expression
{
    public Call(String id, ArrayList<Expression> args)
    {
        this.id = id;
        this.args = args;
    }
    
    private final ArrayList<Expression> args;
    private final String id;
    
    public boolean hasArgs()
    {
        return args != null;
    }
    
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
