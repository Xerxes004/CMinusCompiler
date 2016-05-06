/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: ReturnStatement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Return statement
 */

package parser;

import java.util.ArrayList;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operation;

public class ReturnStatement extends Statement
{
    public ReturnStatement(Statement expressionStatement) 
    {
        this.expressionStatement = expressionStatement;
    }
    
    private final Statement expressionStatement;
    
    public boolean hasExpression()
    {
        return expressionStatement != null;
    }
    
    public String toString() {
        return this.expressionStatement.toString();
    }

    @Override
    public void printMe(String spaces) {
        System.out.println(spaces + "ReturnStatement");
        spaces += "    ";
        System.out.println(spaces + "return");
        if(this.hasExpression()) {
            this.expressionStatement.printMe(spaces);
        }
        System.out.println(spaces + ";");
    }
    
    @Override
    public void genCode(Function function, ArrayList<String> globals) 
        throws CodeGenerationException
    {
        BasicBlock returnBlock = function.getReturnBlock();
        function.appendBlock(returnBlock);
            
        if (expressionStatement != null)
        {
            //function.setCurrBlock(returnBlock);
            // NEED TO ADD CODE TO MAKE SURE THIS GENCODE STORES ITS
            // RESULT IN THE RetReg BEFORE RETURN
            //Operation temp = function.getCurrBlock().getPrevBlock().getLastOper();
            expressionStatement.genCode(function, globals);
            //Operation
        }
    }
    
    @Override
    public StatementType getStatementType()
    {
        return StatementType.RETURN;
    }
}
