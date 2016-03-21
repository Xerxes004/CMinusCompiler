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

public class Program 
{
    public Program()
    {
        declarations = new ArrayList<>();
    }
    
    private final ArrayList<Declaration> declarations;
    
    @Override
    public String toString()
    {
        String string = "";
        
        for (Declaration d : declarations)
        {
            string += d.toString();
        }
        
        return string;
    }
}
