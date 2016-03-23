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

import java.io.IOException;
import java.util.ArrayList;

public class Program 
{
    public Program(ArrayList<Declaration> declarations) 
        throws IOException
    {
        this.declarations = declarations;
    }
    
    private final ArrayList<Declaration> declarations;
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        for (Declaration d : declarations)
        {
            sb.append(d.toString());
        }
        
        return sb.toString();
    }
}
