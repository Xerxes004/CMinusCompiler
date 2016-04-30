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
import lowlevel.FuncParam;

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
        CodeItem tail = null;
        Declaration firstDecl = declarations.get(0);
        
        switch(firstDecl.getDeclType())
        {
            case Declaration.DECL_TYPE_VAR:
                head = new Data(Data.TYPE_INT, firstDecl.getId());
                head.setNextItem(head);
                tail = head;
                break;
                
            case Declaration.DECL_TYPE_FUN:
                FunDeclaration funDecl = (FunDeclaration)firstDecl;
                if (funDecl.hasParams())
                {
                    ArrayList<Param> params = funDecl.getParams();
                    FuncParam firstParam = params.get(0).genCode();
                    FuncParam last = firstParam;
                    for (Param p : params.subList(1, params.size()))
                    {
                        last.setNextParam(p.genCode());
                        last = last.getNextParam();
                    }

                    head = new Function(
                        firstDecl.getDeclType(), 
                        firstDecl.getId(), 
                        firstParam);
                }
                else
                {
                    head = new Function(funDecl.getDeclType(), funDecl.getId());
                }
                tail = head;
                break;
                
            default:
                throw new CodeGenerationException(
                    "Code Item in Program.genCode was not a var or fun decl");
        }
        
        for (Declaration decl : declarations.subList(1, declarations.size()))
        {
            switch(decl.getDeclType())
            {
            case Declaration.DECL_TYPE_VAR:
                tail.setNextItem(new Data(Data.TYPE_INT, decl.getId()));
                break;

            case Declaration.DECL_TYPE_FUN:
                FunDeclaration funDecl = (FunDeclaration)decl;
                if (funDecl.hasParams())
                {
                    ArrayList<Param> params = funDecl.getParams();
                    FuncParam firstParam = params.get(0).genCode();
                    FuncParam lastParam = firstParam;
                    for (Param p : params.subList(1, params.size()))
                    {
                        lastParam.setNextParam(p.genCode());
                        lastParam = lastParam.getNextParam();
                    }

                    tail.setNextItem(new Function(
                        decl.getDeclType(), 
                        decl.getId(), 
                        firstParam));
                }
                else
                {
                    tail.setNextItem(
                        new Function(funDecl.getDeclType(), funDecl.getId())
                    );
                }
                break;
            default:
                throw new CodeGenerationException(
                    "Code Item in Program.genCode was not a var or fun decl");
            }
            
            tail = tail.getNextItem();
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
