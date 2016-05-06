/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Call.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: This class defines a function Call.
 */

package parser;

import java.util.ArrayList;
import lowlevel.Attribute;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public class Call
    extends Expression
{
    public Call(String id, ArrayList<Expression> args)
    {
        this.id = id;
        this.args = args;
    }

    private final ArrayList<Expression> args;
    private final String id;

    public boolean hasArgs()
    {
        return args != null;
    }
    
    public String getId()
    {
        return id;
    }
    
    public ArrayList<Expression> getArgs()
    {
        return args;
    }
    
    @Override
    public ExpressionType getExpressionType()
    {
        return ExpressionType.CALL;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (Expression e : args)
        {
            sb.append(e.toString());
        }

        return sb.toString();
    }

    public void printMe(String spaces)
    {
        System.out.println(spaces + "Call");
        spaces += "    ";
        System.out.println(spaces + this.id);
        System.out.println(spaces + "(");
        if (this.hasArgs())
        {
            for (int i = 0; i < this.args.size(); i++)
            {
                if (i > 0)
                {
                    System.out.println(spaces + ",");
                }
                this.args.get(i).printMe(spaces);

            }
        }
        System.out.println(spaces + ")");

    }
    
    //TODO
    @Override
    public void genCode(Function function, ArrayList<String> globals) 
        throws CodeGenerationException
    {
        BasicBlock currBlock = function.getCurrBlock();
        // for each param
        // codeGen param
        int i = 0;
        if (args != null)
        {
            Operation pass = null;
            for (Expression e : args)
            {
                e.genCode(function, globals);
                // pass param reg - attribute on pass with PARAMNUM
                pass = new Operation(Operation.OperationType.PASS, currBlock);
                pass.addAttribute(new Attribute("PARAM_NUM", Integer.toString(i++)));
                pass.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, e.getRegNum()));
                currBlock.appendOper(pass);
            }
        }
        // make CALL oper  src0 name
        Operation call = new Operation(Operation.OperationType.CALL, currBlock);
        call.setSrcOperand(0, new Operand(Operand.OperandType.STRING, id));
        call.addAttribute(new Attribute("numParams", Integer.toString(i)));
        currBlock.appendOper(call);
        
        Operation assign = new Operation(Operation.OperationType.ASSIGN, currBlock);
        
        int newReg = function.getNewRegNum();
        
        assign.setSrcOperand(0, 
            new Operand(Operand.OperandType.REGISTER, 
            newReg
            )
        );
        
        // Regnew = RETREG  (ASSIGN OPER)
        // annotate yourself with Regnew
        assign.setDestOperand(0, new Operand(Operand.OperandType.MACRO, "RetReg"));
        assign.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, newReg));
        currBlock.appendOper(assign);
        setRegNum(newReg);        
    }
}
