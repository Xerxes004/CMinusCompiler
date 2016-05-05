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
        
        Operation brz = new Operation(
                Operation.OperationType.BNE,
                function.getCurrBlock()
            );
        
        Operand elseTarget = new Operand(
                Operand.OperandType.BLOCK,
                elseBlock.getBlockNum()
        );
        
        brz.setSrcOperand(
                0, 
                function.getCurrBlock().getLastOper().getDestOperand(0)
        );
        brz.setSrcOperand(
                1, 
                new Operand(Operand.OperandType.INTEGER, 1)
        );
        
        brz.setSrcOperand(2, elseTarget);
        
        //Operation lastOp = function.getCurrBlock().getLastOper();
        //function.getCurrBlock().removeOper(lastOp);
        function.getCurrBlock().appendOper(brz);
        expr.genCode(function, globals);
        
        function.appendToCurrentBlock(thenBlock);
        function.setCurrBlock(thenBlock);
        
        thenPart.genCode(function, globals);
        
        function.appendToCurrentBlock(postBlock);
        
        if (elsePart != null)
        {
            function.setCurrBlock(elseBlock);
            elsePart.genCode(function, globals);
            
            if (function.getCurrBlock().getLastOper().getType() == 
                    Operation.OperationType.RETURN)
            {
                // ADD JUMP POST TO ELSE
                System.out.println("RETURN DETECTED");
            }
            else
            {
                // ADD JUMP TO POST BLOCK
                System.out.println("RETURN NOT DETECTED");
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
