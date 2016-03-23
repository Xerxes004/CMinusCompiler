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
        System.out.println(spaces + "SelectionStatement");
        spaces += "    ";
        System.out.println(spaces + "if");
        System.out.println(spaces + "(");
        this.expr.printMe(spaces);
        System.out.println(spaces + ")");
        this.ifPart.printMe(spaces);
        if(this.elsePart != null) {
            System.out.println(spaces + "else");
            this.elsePart.printMe(spaces);
        }
        
    }
    
}
