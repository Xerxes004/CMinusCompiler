/*
 * @author Jimmy Von Eiff
 * @version 1
 * 
 * File: SelectionStatement.java
 * Created: March 21 2016
 * 
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description:
 */

package parser;

public class SelectionStatement extends Statement {

    public SelectionStatement(
            Expression input, Statement inputIf, Statement inputElse) {
        this.expr = input;
        this.ifPart = inputIf;
        this.elsePart = inputElse;
    }
    
    private Expression expr;
    private Statement ifPart;
    private Statement elsePart;
    
    public String toString() {
        return (this.expr.toString() + " "
                +this.ifPart.toString() + " "
                +this.elsePart.toString());
    }

    @Override
    public void printMe(String spaces) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
