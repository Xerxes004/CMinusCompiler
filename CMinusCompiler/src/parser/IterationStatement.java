/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: IterationStatement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines an Iteration Statement
 */


package parser;

import lowlevel.Function;
import lowlevel.Operation;

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
    public void printMe(String spaces) {
        System.out.println(spaces + "IterationStatement");
        spaces += "    ";
        System.out.println(spaces + "while");
        System.out.println(spaces + "(");
        this.expr.printMe(spaces);
        System.out.println(spaces + ")");
        this.stmt.printMe(spaces);
    }
    
    @Override
    public void genCode(Function function)
    {
    }
    
    @Override
    public StatementType getStatementType()
    {
        return StatementType.ITERATION;
    }
}
