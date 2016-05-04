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
    public void genCode(Function function){}
}
