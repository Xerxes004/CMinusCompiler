/*
/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Program.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a C Minus Program
 */


package parser;

import java.io.IOException;
import java.util.ArrayList;

public class Program 
{
    public Program(ArrayList<Declaration> declarations)
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
    
    public void printMe() {
        String spaces = "    ";
        System.out.println("Program");
        for(int i = 0; i < this.declarations.size(); i ++) {
            declarations.get(i).printMe(spaces);
        }
    }
}
