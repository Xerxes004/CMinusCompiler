/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: IterationStatement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines an Iteration Statement
 */


package parser;

import java.util.ArrayList;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public class IterationStatement extends Statement{
    public IterationStatement(Expression inputExpr, Statement inputStmt) {
        this.expr = inputExpr;
        this.stmt = inputStmt;
    }
    
    private Expression expr;
    private Statement stmt;
    
    public String toString() {
        return (this.expr.toString() + " " + this.stmt.toString());
    }

    @Override
    public void printMe(String spaces) {
        System.out.println(spaces + "IterationStatement");
        spaces += "    ";
        System.out.println(spaces + "while");
        System.out.println(spaces + "(");
        this.expr.printMe(spaces);
        System.out.println(spaces + ")");
        this.stmt.printMe(spaces);
    }
    
    @Override
    public void genCode(Function function, ArrayList<String> globals)
        throws CodeGenerationException
    {
        BasicBlock currentBlock = function.getCurrBlock();

        BasicBlock thenBlock = new BasicBlock(function);
        BasicBlock postBlock = new BasicBlock(function);
        
        expr.genCode(function, globals);
        
        Operation lastOp = currentBlock.getLastOper();

        Operation branch = new Operation(
                Operation.OperationType.BEQ,
                currentBlock
        );
        
        Operand postTarget = new Operand(
                Operand.OperandType.BLOCK,
                postBlock.getBlockNum()
        );
        
        branch.setSrcOperand(0, lastOp.getDestOperand(0));
        branch.setSrcOperand(1, new Operand(Operand.OperandType.INTEGER, 0));
        branch.setSrcOperand(2, postTarget);
        
        currentBlock.appendOper(branch);
        
        function.appendToCurrentBlock(thenBlock);
        function.setCurrBlock(thenBlock);
        currentBlock = function.getCurrBlock();
        
        stmt.genCode(function, globals);
        
        Operation branchLoop = new Operation(
                Operation.OperationType.BEQ,
                currentBlock
        );
        
        Operand loopTarget = new Operand(
                Operand.OperandType.BLOCK,
                thenBlock.getBlockNum()
        );
        
        expr.genCode(function, globals);
        
        lastOp = currentBlock.getLastOper();
        
        branchLoop.setSrcOperand(0, lastOp.getDestOperand(0));
        branchLoop.setSrcOperand(1, new Operand(Operand.OperandType.INTEGER, 1));
        branchLoop.setSrcOperand(2, loopTarget);
        
        currentBlock.appendOper(branchLoop);
        
        currentBlock = function.getCurrBlock();
        function.appendToCurrentBlock(currentBlock);
        
    }
    
    @Override
    public StatementType getStatementType()
    {
        return StatementType.ITERATION;
    }
}