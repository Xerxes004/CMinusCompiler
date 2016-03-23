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
    public ReturnStatement(Statement input) {
        this.expressionStatement = input;
    }
    
    private Statement expressionStatement;
    
    public boolean hasExpression()
    {
        return expressionStatement != null;
    }
    
    public String toString() {
        return this.expressionStatement.toString();
    }
}
