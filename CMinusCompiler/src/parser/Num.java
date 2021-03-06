/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Num.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Num
 */


package parser;

import java.util.ArrayList;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public class Num
    extends Expression
{
    public Num(int value)
    {
        this.value = value;
    }
    
    private final int value;
    
    public int getValue()
    {
        return value;
    }
    
    @Override
    public ExpressionType getExpressionType()
    {
        return ExpressionType.NUM;
    }
    
    @Override
    public String toString()
    {
        return Integer.toString(value);
    }
    
    @Override
    public void printMe(String spaces) {
        System.out.println(spaces + "NUM: " + this.value);
    }
    
    @Override
    public void genCode(Function function, ArrayList<String> globals)
    {
        BasicBlock curr = function.getCurrBlock();
        Operation moveOper = new Operation(Operation.OperationType.ASSIGN, curr);
        Operand operand = new Operand(Operand.OperandType.INTEGER, value);
        moveOper.setSrcOperand(0, operand);
        int newReg = function.getNewRegNum();
        moveOper.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, newReg));
        setRegNum(newReg);
        
        curr.appendOper(moveOper);
    }
}
