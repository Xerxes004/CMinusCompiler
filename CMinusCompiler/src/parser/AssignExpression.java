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

import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
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
    public void genCode(Function function)
    {
        BasicBlock currBlock = function.getCurrBlock();
        Operation currOp = currBlock.getLastOper();
        
        Operation nextOper = new Operation(
            Operation.OperationType.ASSIGN, 
            currBlock
        );
        
        currBlock.insertOperAfter(currOp, nextOper);
        
        var.setIsDest(true);
        var.genCode(function);
        expression.setIsLeftSide(true);
        expression.genCode(function);
    }
}
