/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Expression.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class uses a CMinusScanner to read tokens from an input
 * file and parse them into a tree.
 */

package parser;

import java.util.ArrayList;
import java.util.HashMap;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public abstract class Expression 
{
    public enum ExpressionType {
        NUM, VAR, CALL, BINARY, ASSIGN
    }
    
    public abstract void printMe(String spaces);
    public abstract void genCode(Function function, ArrayList<String> globals)
        throws CodeGenerationException;
    public abstract ExpressionType getExpressionType();
    
    private int regNum;
    
    public int getRegNum()
    {
        return regNum;
    }
    
    public void setRegNum(int newNum)
    {
        regNum = newNum;
    }
        
    public Operand getVariable(
        Function function, 
        ArrayList<String> globals,
        String id
    ) 
        throws CodeGenerationException
    {
        HashMap table = function.getTable();
        if (table.containsKey(id))
        {
            return new Operand(
                Operand.OperandType.REGISTER,
                table.get(id)
            );
        }
        else if (globals.contains(id))
        {
            BasicBlock currBlock = function.getCurrBlock();
            Operation lastOp = currBlock.getLastOper();
            
            Operation globalOp = new Operation(
                Operation.OperationType.LOAD_I,
                currBlock
            );
            
            Operand globalLoadDest = new Operand(
                Operand.OperandType.REGISTER,
                function.getNewRegNum()
            );
            
            Operand globalLoadVar = new Operand(
                Operand.OperandType.STRING,
                id
            );
            
            return globalLoadDest;
        }
        else
        {
            throw new CodeGenerationException("Symbol not found: " + id);
        }
    }
}
