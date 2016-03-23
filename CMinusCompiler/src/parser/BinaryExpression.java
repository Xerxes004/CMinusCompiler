/*
 * @author Jimmy Von Eiff
 * @version 1
 * 
 * File: BinaryExpression.java
 * Created: March 21 2016
 * 
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description:
 */

package parser;



public class BinaryExpression extends Expression{
    public BinaryExpression(
            Expression inputFirst, Operator inputOp, Expression inputSecond) {
        this.firstExpr = inputFirst;
        this.op = inputOp;
        this.secondExpr = inputSecond;
    }
            
    private Expression firstExpr;
    private Expression secondExpr;
    private Operator op;
    public enum Operator
    {
        LTHAN, LTHAN_EQUAL, GTHAN, GTHAN_EQUAL, EQUAL, NOT_EQUAL, PLUS, MINUS, 
        MULTIPLY, DIVIDE
    };
    
    public String toString() {
        return(this.firstExpr.toString() + " "
               + this.op.toString() + " " 
               + this.secondExpr.toString());
    }
    
    public void printMe(String spaces) {
        System.out.println(spaces + "BinaryExpression");
        spaces += "    ";
        this.firstExpr.printMe(spaces);
        System.out.println(spaces + op.toString());
        this.secondExpr.printMe(spaces);
    }    
}
