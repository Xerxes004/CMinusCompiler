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

import java.util.ArrayList;
import java.util.HashMap;
import lowlevel.CodeItem;

public class Program 
{
    public Program(ArrayList<Declaration> declarations)
    {
        this.declarations = declarations;
    }
    
    private final ArrayList<Declaration> declarations;
    
    public ArrayList<Declaration> getDeclarations()
    {
        return declarations;
    }
    
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
    
    public CodeItem genCode() throws CodeGenerationException
    {
        if (declarations.isEmpty()) return null;
        
        CodeItem head = null;
        CodeItem tail = null;
        ArrayList<String> globals = new ArrayList<>();
            
        // build a linked list of top-level CodeItems (Datas and Functions)
        for (Declaration decl : declarations)
        {
            if (head == null || tail == null)
            {
                head = decl.genCode(globals);
                tail = head;
            }
            else
            {
                tail.setNextItem(decl.genCode(globals));
                tail = tail.getNextItem();
            }
        }
        
        return head;
    }
    
    public void printMe() throws NullPointerException{
        String spaces = "    ";
        System.out.println("Program");
        for(int i = 0; i < this.declarations.size(); i ++) {
            declarations.get(i).printMe(spaces);
        }
    }
}
