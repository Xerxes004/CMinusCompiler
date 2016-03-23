/*
 * @author Jimmy Von Eiff
 * @version 1
 * 
 * File: ReturnStatement.java
 * Created: March 21 2016
 * 
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description:
 */
package parser;

public class ReturnStatement extends Statement
{
    public ReturnStatement(Statement expressionStatement) 
    {
        this.expressionStatement = expressionStatement;
    }
    
    private final Statement expressionStatement;
    
    public boolean hasExpression()
    {
        return expressionStatement != null;
    }
    
    public String toString() {
        return this.expressionStatement.toString();
    }

    @Override
    public void printMe(String spaces) {
        System.out.println(spaces + "ReturnStatement");
        spaces += "    ";
        System.out.println(spaces + "return");
        if(this.hasExpression()) {
            this.expressionStatement.printMe(spaces);
        }
        System.out.println(spaces + ";");
    }
}
