/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: SelectionStatement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Selection Statement
 */


package parser;

import java.util.ArrayList;
import lowlevel.Function;

public class SelectionStatement 
    extends Statement {

    public SelectionStatement(
            Expression input, Statement inputIf, Statement inputElse) {
        this.expr = input;
        this.ifPart = inputIf;
        this.elsePart = inputElse;
    }
    
    private final Expression expr;
    private final Statement ifPart;
    private final Statement elsePart;
    
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
    
    @Override
    public void genCode(Function function, ArrayList<String> globals)
    {
    }
    
    @Override
    public StatementType getStatementType()
    {
        return StatementType.SELECTION;
    }
}
