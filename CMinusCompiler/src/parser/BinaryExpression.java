/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: BinaryExpression.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: This class is used to define a BinaryExpression.
 */

package parser;

import java.util.ArrayList;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.BasicBlock;

public class BinaryExpression
    extends Expression
{
    public BinaryExpression(
        Expression leftSide, Operator inputOp, Expression rightSide)
    {
        this.leftSide = leftSide;
        this.op = inputOp;
        this.rightSide = rightSide;
    }

    private final Expression leftSide;
    private final Expression rightSide;
    private final Operator op;
    
    @Override
    public ExpressionType getExpressionType()
    {
        return ExpressionType.BINARY;
    }
    
    public Expression getLeftSide()
    {
        return leftSide;
    }
    
    public Expression getRightSide()
    {
        return rightSide;
    }
    
    public Operator getOperator()
    {
        return op;
    }

    public enum Operator
    {
        LTHAN, LTHAN_EQUAL, GTHAN, GTHAN_EQUAL, EQUAL, NOT_EQUAL, PLUS, MINUS,
        MULTIPLY, DIVIDE
    };

    public String toString()
    {
        return (this.leftSide.toString() + " "
            + this.op.toString() + " "
            + this.rightSide.toString());
    }

    @Override
    public void printMe(String spaces)
    {
        System.out.println(spaces + "BinaryExpression");
        spaces += "    ";
        this.leftSide.printMe(spaces);
        System.out.println(spaces + op.toString());
        this.rightSide.printMe(spaces);
        
    }    

    @Override
    public void genCode(Function function, ArrayList<String> globals) 
        throws CodeGenerationException
    {
        // MOVE TO HELPER FUNC
        Operation.OperationType opType;
        
        switch(this.op) {
            case LTHAN: 
                opType = Operation.OperationType.LT;
                break;
            case LTHAN_EQUAL: 
                opType = Operation.OperationType.LTE;
                break;
            case GTHAN: 
                opType = Operation.OperationType.GT;
                break;
            case GTHAN_EQUAL: 
                opType = Operation.OperationType.GTE;
                break;
            case EQUAL: 
                opType = Operation.OperationType.EQUAL;
                break;
            case NOT_EQUAL: 
                opType = Operation.OperationType.NOT_EQUAL;
                break;
            case PLUS: 
                opType = Operation.OperationType.ADD_I;
                break;
            case MULTIPLY:
                opType = Operation.OperationType.MUL_I;
                break;
            case DIVIDE:
                opType = Operation.OperationType.DIV_I;
                break;
            default:
                opType = Operation.OperationType.UNKNOWN;
        }
        
        Operand destination = new Operand(
            Operand.OperandType.REGISTER, 
            function.getNewRegNum()
        );
        
        BasicBlock currentBlock = function.getCurrBlock();
        Operation lastOp = currentBlock.getLastOper();
        
        if (isLeftSide())
        {
            lastOp.setSrcOperand(0, destination);
        }
        else
        {
            lastOp.setSrcOperand(1, destination);
        }
        
        Operation newOp = new Operation(opType, currentBlock);
        newOp.setDestOperand(0, destination);
        
        Operation temp = currentBlock.getLastOper();
        currentBlock.setLastOper(newOp);
        currentBlock.insertOperBefore(temp, newOp);
        
        this.leftSide.setIsLeftSide(true);
        this.leftSide.genCode(function, globals);
        this.rightSide.genCode(function, globals);
        
        currentBlock.setLastOper(temp);
    }
}
