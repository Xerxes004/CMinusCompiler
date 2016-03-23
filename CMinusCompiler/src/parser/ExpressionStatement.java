/*
 * @author Jimmy Von Eiff
 * @version 1
 * 
 * File: ExpressionStatement.java
 * Created: March 21 2016
 * 
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description:
 */
package parser;

public class ExpressionStatement extends Statement{
    
    public ExpressionStatement(Expression input) {
        this.expr = input;
    }
    private Expression expr;
    
    public String toString() {
        return this.expr.toString();
    }

    @Override
    public void printMe(String spaces) {
        System.out.println(spaces + "ExpressionStatement");
        spaces += "    ";
        if(this.expr != null) {
           this.expr.printMe(spaces);
           
        } else {
            System.out.println(spaces + ";");
        }
    }
    
}
