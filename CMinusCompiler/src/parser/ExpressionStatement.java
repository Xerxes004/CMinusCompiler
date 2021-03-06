/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: ExpressionStatement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines an expression statement.
 */

package parser;

import java.util.ArrayList;
import lowlevel.Function;

public class ExpressionStatement extends Statement{
    
    public ExpressionStatement(Expression input) {
        this.expr = input;
    }
    private Expression expr;
    
    public String toString() {
        return this.expr.toString();
    }
    
    public Expression getExpr()
    {
        return expr;
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
    
    @Override
    public void genCode(Function function, ArrayList<String> globals) 
        throws CodeGenerationException
    {
        expr.genCode(function, globals);
    }
    
    @Override
    public StatementType getStatementType()
    {
        return StatementType.EXPRESSION;
    }
}
