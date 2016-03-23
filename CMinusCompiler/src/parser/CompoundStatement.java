/*
 * @author Jimmy Von Eiff
 * @version 1
 * 
 * File: CompoundStatement.java
 * Created: March 21 2016
 * 
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description:
 */

package parser;

import java.util.*;

public class CompoundStatement extends Statement 
{
    public CompoundStatement(ArrayList<Declaration> localDeclarations, 
                             ArrayList<Statement> statementList) 
    {
       this.localDeclarations = localDeclarations;
       this.statementList = statementList;
    }
    
    private ArrayList<Declaration> localDeclarations;
    private ArrayList<Statement> statementList;
    
    public String toString() {
        String toOutput = null;
        for(int i = 0; i < this.localDeclarations.size(); i++) {
            toOutput += this.localDeclarations.get(i).toString();
            toOutput += " ";
        }
        for(int i = 0; i < this.statementList.size(); i++) {
            toOutput += this.statementList.get(i).toString();
            toOutput += " ";
        }
        return toOutput;
    }
    
    public void printStatement(String spaces) {
        System.out.println(spaces + "{");
        for(int i = 0; i < this.localDeclarations.size(); i++) {
            this.localDeclarations.get(i).printDeclaration(spaces + "    ");
        }
        for(int i = 0; i < this.statementList.size(); i++) {
            this.statementList.get(i).printStatement(spaces + "    ");
        }
        System.out.println(spaces + "}");
    }
}
