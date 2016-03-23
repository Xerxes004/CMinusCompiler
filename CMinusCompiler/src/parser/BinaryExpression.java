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
    public enum Operator{RELOP, ADDOP, MULOP, PARENTHESIZED};
    
    public String toString() {
        return(this.firstExpr.toString() + " "
               + this.op.toString() + " " 
               + this.secondExpr.toString());
    }
    
    
}
