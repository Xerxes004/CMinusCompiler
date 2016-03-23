
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

public class CompoundStatement
    extends Statement
{
    public CompoundStatement(ArrayList<Declaration> localDeclarations,
                             ArrayList<Statement> statementList)
    {
        this.localDeclarations = localDeclarations;
        this.statementList = statementList;
    }

    private ArrayList<Declaration> localDeclarations;
    private ArrayList<Statement> statementList;

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

    public void printMe(String spaces)
    {
        System.out.println(spaces + "CompoundStatement");
        spaces += "    ";
        System.out.println(spaces + "{");
        for (int i = 0; i < this.localDeclarations.size(); i++)
        {
            this.localDeclarations.get(i).printMe(spaces);
        }
        for (int i = 0; i < this.statementList.size(); i++)
        {
            this.statementList.get(i).printMe(spaces);
        }
        System.out.println(spaces + "}");
    }
}
