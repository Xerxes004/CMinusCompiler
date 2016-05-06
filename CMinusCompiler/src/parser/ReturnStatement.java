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
import lowlevel.Operand;
import lowlevel.Operation;

public class ReturnStatement extends Statement
{
    public ReturnStatement(Statement expressionStatement) 
    {
        this.exprStmt = expressionStatement;
    }
    
    private final Statement exprStmt;
    
    public boolean hasExpression()
    {
        return exprStmt != null;
    }
    
    public String toString() {
        return this.exprStmt.toString();
    }

    @Override
    public void printMe(String spaces) {
        System.out.println(spaces + "ReturnStatement");
        spaces += "    ";
        System.out.println(spaces + "return");
        if(this.hasExpression()) {
            this.exprStmt.printMe(spaces);
        }
        System.out.println(spaces + ";");
    }
    
    @Override
    public void genCode(Function function, ArrayList<String> globals) 
        throws CodeGenerationException
    {
        BasicBlock currBlock = function.getCurrBlock();
        if (exprStmt != null)
        {
            //function.setCurrBlock(returnBlock);
            // NEED TO ADD CODE TO MAKE SURE THIS GENCODE STORES ITS
            // RESULT IN THE RetReg BEFORE RETURN
            //Operation temp = function.getCurrBlock().getPrevBlock().getLastOper();
            exprStmt.genCode(function, globals);
            Operation assign = new Operation(
                    Operation.OperationType.ASSIGN, 
                    function.getCurrBlock()
            );
            assign.setDestOperand(
                    0, 
                    new Operand(Operand.OperandType.MACRO, "RetReg")
            );
            assign.setSrcOperand(
                    0, 
                    new Operand(
                            Operand.OperandType.REGISTER, 
                            ((ExpressionStatement)exprStmt).getExpr().getRegNum()
                    )
            );
            currBlock.appendOper(assign);
        }
        Operation jumpToReturn = new Operation(
                Operation.OperationType.JMP,
                currBlock
        );
        jumpToReturn.setSrcOperand(
                0, 
                new Operand(
                        Operand.OperandType.BLOCK, 
                        function.getReturnBlock().getBlockNum()
                )
        );
    }
    
    @Override
    public StatementType getStatementType()
    {
        return StatementType.RETURN;
    }
}
