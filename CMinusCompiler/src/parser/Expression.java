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
    protected Expression()
    {
        this.isDest = false;
        this.isLeftSide = false;
    }
    public enum ExpressionType {
        NUM, VAR, CALL, BINARY, ASSIGN
    }
    
    private boolean isDest;
    private boolean isLeftSide;
    
    public abstract void printMe(String spaces);
    public abstract void genCode(Function function, ArrayList<String> globals)
        throws CodeGenerationException;
    public abstract ExpressionType getExpressionType();
    
    public boolean isDest()
    {
        return isDest;
    }
    
    public void setIsDest(boolean isDest)
    {
        this.isDest = isDest;
    }
    
    public boolean isLeftSide()
    {
        return this.isLeftSide;
    }
    
    public void setIsLeftSide(boolean isLeftSide)
    {
        this.isLeftSide = isLeftSide;
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
            
            if (isDest)
            {
                lastOp.setType(Operation.OperationType.STORE_I);
                //lastOp.setSrcOperand(0, globalLoadVar);
                lastOp.setSrcOperand(1, globalLoadVar);
            }
            else
            {
                globalOp.setDestOperand(0, globalLoadDest);
                globalOp.setSrcOperand(0, globalLoadVar);
                currBlock.insertOperBefore(lastOp, globalOp);
            }
            
            return globalLoadDest;
        }
        else
        {
            throw new CodeGenerationException("Symbol not found: " + id);
        }
    }
}
