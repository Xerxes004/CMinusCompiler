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
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.Function;

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
    
    public CodeItem genCode() 
        throws CodeGenerationException
    {
        if (declarations.isEmpty()) return null;
        
        CodeItem head = null;
        
        int i = 0;
        CodeItem tail = head;
        for (Declaration decl : declarations)
        {
            if (i == 0)
            {
                Declaration first = declarations.get(0);
        
                switch (first.getType())
                {
                    case Declaration.TYPE_VAR:
                        head = new Data(Data.TYPE_INT, first.getId());
                        tail = head;
                        break;
                    case Declaration.TYPE_FUN:
                        break;
                    default:
                        break;
                }
            }
            else
            {
                switch (decl.getType())
                {
                    case Declaration.TYPE_VAR:
                        tail.setNextItem(new Data(Data.TYPE_INT, decl.getId()));
                        tail = tail.getNextItem();
                        break;
                        
                    case Declaration.TYPE_FUN:
                        FunDeclaration funDecl = (FunDeclaration)decl;
                        
                        int type = decl.returnType().equals("int") ? 
                                   Data.TYPE_INT : Data.TYPE_VOID;
                        
                        if (funDecl.hasParams())
                        {
                            
                            tail.setNextItem(new Function(type, decl.getId()));
                        }
                        
                        
                        break;
                        
                    default:
                        throw new CodeGenerationException(
                            "Code Item in Program.genCode was not a var or fun decl");
                }
            }
            i++;
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
