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
    public void genCode(Function function)
    {
        Operator typeOf = this.getOperator();
        if((typeOf == Operator.LTHAN)||(typeOf == Operator.LTHAN_EQUAL)
                ||(typeOf == Operator.GTHAN)||(typeOf == Operator.GTHAN_EQUAL)
                ||(typeOf == Operator.EQUAL)||(typeOf == Operator.NOT_EQUAL))
        {
            this.leftSide.genCode(function);
            Operand destination = new Operand(Operand.OperandType.REGISTER, function.getNewRegNum());
            Operation bin = null;
            BasicBlock current = function.getCurrBlock();
            switch(typeOf) {
                case LTHAN: bin = new Operation(Operation.OperationType.LT, current);
                    break;
                case LTHAN_EQUAL: bin = new Operation(Operation.OperationType.LTE, current);
                    break;
                case GTHAN: bin = new Operation(Operation.OperationType.GT, current);
                    break;
                case GTHAN_EQUAL: bin = new Operation(Operation.OperationType.GTE, current);
                    break;
                case EQUAL: bin = new Operation(Operation.OperationType.EQUAL, current);
                    break;
                case NOT_EQUAL: bin = new Operation(Operation.OperationType.NOT_EQUAL, current);
                    break;
            }
            
            bin.setDestOperand(0, destination);
            Operation temp = current.getLastOper();
            current.setLastOper(bin);
            temp.setNextOper(bin);
            temp.setSrcOperand(0, destination);
            
            this.rightSide.genCode(function);
        }
        
        
    }
}
