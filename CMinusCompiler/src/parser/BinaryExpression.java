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
import lowlevel.Operation;

public class BinaryExpression
    extends Expression
{
    public BinaryExpression(
        Expression inputFirst, Operator inputOp, Expression inputSecond)
    {
        this.firstExpr = inputFirst;
        this.op = inputOp;
        this.secondExpr = inputSecond;
    }

    private final Expression firstExpr;
    private final Expression secondExpr;
    private Operator op;
    
    @Override
    public ExpressionType getExpressionType()
    {
        return ExpressionType.BINARY;
    }

    public enum Operator
    {
        LTHAN, LTHAN_EQUAL, GTHAN, GTHAN_EQUAL, EQUAL, NOT_EQUAL, PLUS, MINUS,
        MULTIPLY, DIVIDE
    };

    public String toString()
    {
        return (this.firstExpr.toString() + " "
            + this.op.toString() + " "
            + this.secondExpr.toString());
    }

    @Override
    public void printMe(String spaces)
    {
        System.out.println(spaces + "BinaryExpression");
        spaces += "    ";
        this.firstExpr.printMe(spaces);
        System.out.println(spaces + op.toString());
        this.secondExpr.printMe(spaces);
        
    }    

    @Override
    public Operation genCode(Function function)
    {
        return null;
    }
}
