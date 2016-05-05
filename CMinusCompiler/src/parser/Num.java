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
        // GET CURR BLOCK
        // GET LAST OPERATION
        // IF I'M A DESTINATION, SET MYSELF AS A DEST OPERAND
        // IF I'M NOT, SET MYSELF AS A SRC OPERAND
        // ADD MYSELF TO THE LAST OPERATION
        Operation lastOp = function.getCurrBlock().getLastOper();
        
        Operand operand = new Operand(Operand.OperandType.INTEGER, value);
        
        for (int i = 0; i < Operation.MAX_SRC_OPERANDS; i++)
        {
            if (lastOp.getSrcOperand(i) == null)
            {
                lastOp.setSrcOperand(i, operand);
                break;
            }
        }
    }
}
