/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: SelectionStatement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Selection Statement
 */


package parser;

import java.util.ArrayList;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public class SelectionStatement 
    extends Statement {

    public SelectionStatement(
            Expression input, Statement inputIf, Statement inputElse) {
        this.expr = input;
        this.thenPart = inputIf;
        this.elsePart = inputElse;
    }
    
    private final Expression expr;
    private final Statement thenPart;
    private final Statement elsePart;
    
    public String toString() {
        return (this.expr.toString() + " "
                +this.thenPart.toString() + " "
                +this.elsePart.toString());
    }

    @Override
    public void printMe(String spaces) {
        System.out.println(spaces + "SelectionStatement");
        spaces += "    ";
        System.out.println(spaces + "if");
        System.out.println(spaces + "(");
        this.expr.printMe(spaces);
        System.out.println(spaces + ")");
        this.thenPart.printMe(spaces);
        if(this.elsePart != null) {
            System.out.println(spaces + "else");
            this.elsePart.printMe(spaces);
        }
        
    }
    
    @Override
    public void genCode(Function function, ArrayList<String> globals) 
            throws CodeGenerationException
    {
        BasicBlock thenBlock = new BasicBlock(function);
        BasicBlock elseBlock = new BasicBlock(function);
        BasicBlock postBlock = new BasicBlock(function);
        
        BasicBlock currentBlock = function.getCurrBlock();
        
        expr.genCode(function, globals);
        
        Operation beq = new Operation(
                Operation.OperationType.BEQ,
                function.getCurrBlock()
            );
        
        beq.setSrcOperand(
                0, 
                new Operand(Operand.OperandType.REGISTER, expr.getRegNum())
        );
        beq.setSrcOperand(
                1, 
                new Operand(Operand.OperandType.INTEGER, 0)
        );

        Operand elseTarget = new Operand(
                Operand.OperandType.BLOCK,
                elseBlock.getBlockNum()
        );
        
        beq.setSrcOperand(2, elseTarget);
        
        function.getCurrBlock().appendOper(beq);
        
        expr.genCode(function, globals);
        
        function.appendToCurrentBlock(thenBlock);
        function.setCurrBlock(thenBlock);
        
        thenPart.genCode(function, globals);
        
        function.appendToCurrentBlock(postBlock);
        
        function.setCurrBlock(elseBlock);
        
        if (elsePart != null)
        {
            elsePart.genCode(function, globals);
            
            if (function.getCurrBlock().getLastOper().getType() == 
                    Operation.OperationType.RETURN)
            {
                // ADD JUMP POST TO ELSE
                Operation jumpPostToElse = new Operation(
                    Operation.OperationType.JMP,
                    function.getCurrBlock()
                );
                jumpPostToElse.setSrcOperand(0, 
                    new Operand(Operand.OperandType.BLOCK, elseBlock.getBlockNum()));
                function.getCurrBlock().appendOper(jumpPostToElse);
            }
            else
            {
                // ADD JUMP TO POST BLOCK
                Operation jumpToPost = new Operation(
                    Operation.OperationType.JMP,
                    function.getCurrBlock()
                );
                
                jumpToPost.setSrcOperand(
                    0, 
                    new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum()
                    )
                );
                function.getCurrBlock().appendOper(jumpToPost);
            }
            
            function.appendUnconnectedBlock(elseBlock);
        }
        
        function.setCurrBlock(postBlock);
    }
    
    @Override
    public StatementType getStatementType()
    {
        return StatementType.SELECTION;
    }
}
