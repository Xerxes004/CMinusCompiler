
/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: CompoundStatement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: This class uses a CMinusScanner to read tokens from an input
 * file and parse them into a tree.
 */

package parser;

import java.util.*;
import lowlevel.Operation;

public class CompoundStatement
    extends Statement
{
    public CompoundStatement(ArrayList<Declaration> localDeclarations,
                             ArrayList<Statement> statementList)
    {
        this.localDeclarations = localDeclarations;
        this.statementList = statementList;
    }

    private final ArrayList<Declaration> localDeclarations;
    private final ArrayList<Statement> statementList;

    public String toString()
    {
        String toOutput = null;
        for (int i = 0; i < this.localDeclarations.size(); i++)
        {
            toOutput += this.localDeclarations.get(i).toString();
            toOutput += " ";
        }
        for (int i = 0; i < this.statementList.size(); i++)
        {
            toOutput += this.statementList.get(i).toString();
            toOutput += " ";
        }
        return toOutput;
    }

    @Override
    public void printMe(String spaces)
    {
        System.out.println(spaces + "CompoundStatement");
        spaces += "    ";
        System.out.println(spaces + "{");
        for (int i = 0; i < this.localDeclarations.size(); i++)
        {
            this.localDeclarations.get(i).printMe(spaces);
        }
        for(int i = 0; i < this.statementList.size(); i++) {
            if(this.statementList.get(i)!= null) {
                this.statementList.get(i).printMe(spaces);
            }
            
        }
        System.out.println(spaces + "}");
    }
    
    @Override
    public Operation genCode()
    {
        for (Declaration localDecl : localDeclarations)
        {
            
        }
        for (Statement statement : statementList)
        {
            
        }
        return null;
    }
}
