/*
 * @author Jimmy Von Eiff
 * @version 1
 * 
 * File: IterationStatement.java
 * Created: March 21 2016
 * 
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description:
 */

package parser;

public class IterationStatement extends Statement{
    public IterationStatement(Expression inputExpr, Statement inputStmt) {
        this.expr = inputExpr;
        this.stmt = inputStmt;
    }
    
    private Expression expr;
    private Statement stmt;
    
    public String toString() {
        return (this.expr.toString() + " " + this.stmt.toString());
    }

    @Override
    public void printStatement(String spaces) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
