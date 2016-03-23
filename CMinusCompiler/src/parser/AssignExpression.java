/*
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: GameBoard.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: 
 */

package parser;

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
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(var.toString()).append(" = ").append(expression.toString());
        return sb.toString();
    }
    
    public void printMe(String spaces) {
        System.out.println(spaces + "AssignExpression");
        spaces += "    ";
        this.var.printMe(spaces);
        System.out.println(spaces + "=");
        this.expression.printMe(spaces);
        System.out.println(spaces + ";");
    }
}
