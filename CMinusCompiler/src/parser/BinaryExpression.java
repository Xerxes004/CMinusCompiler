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
        if(this.firstExpr != null) {
            this.firstExpr.printMe(spaces);
        }
        System.out.println(spaces + op.toString());
        if(this.secondExpr != null) {
            this.secondExpr.printMe(spaces);
        }
        
    }    

}
