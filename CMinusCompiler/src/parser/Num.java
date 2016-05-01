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
    
    public void printMe(String spaces) {
        System.out.println(spaces + "NUM: " + this.value);
    }
    
    public Operation genCode(Function function){return null;}
}
