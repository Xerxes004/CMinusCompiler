/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: AssignExpression.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class is used to define an assignment expression.
 */


package parser;

import java.util.ArrayList;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operation;

public class AssignExpression extends Expression
{
    public AssignExpression(Expression var, Expression expression)
    {
        this.var = var;
        this.expression = expression;
    }

    Expression var;
    Expression expression;
    
    @Override
    public ExpressionType getExpressionType()
    {
        return ExpressionType.ASSIGN;
    }
    
    public Expression getLeftSide()
    {
        return var;
    }
    
    public Expression getRightSide()
    {
        return expression;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(var.toString()).append(" = ").append(expression.toString());
        return sb.toString();
    }
    
    @Override
    public void printMe(String spaces) {
        System.out.println(spaces + "AssignExpression");
        spaces += "    ";
        this.var.printMe(spaces);
        System.out.println(spaces + "ASSIGN");
        this.expression.printMe(spaces);
        System.out.println(spaces + ";");
    }
    
    @Override
    public void genCode(Function function, ArrayList<String> globals) 
        throws CodeGenerationException
    {
        BasicBlock currBlock = function.getCurrBlock();
        
        //var.genCode(function, globals);
        expression.genCode(function, globals);
        // peek var
        // if in local table, get reg
        // make ASSIGN oper with var reg as dest, expresssion reg as src
        // annocate assign with varreg
        
        // else if in global
        // make STORE oper, src0 is expression reg, src1 is var name
        // annotate with expression reg
    }
}
