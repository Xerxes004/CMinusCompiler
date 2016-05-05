/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: CMinusParser.java 
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
import java.util.logging.Level;
import java.util.logging.Logger;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.FuncParam;
import lowlevel.Function;
import lowlevel.Operation;

public class FunDeclaration extends Declaration
{
    public FunDeclaration (int returnType, 
                           String id, 
                           ArrayList<Param> params, 
                           Statement compoundStmt)
    {
        super(id);
        this.returnType = returnType;
        this.params = params;
        this.compoundStmt = compoundStmt;
    }
    
    private final ArrayList<Param> params;
    private final Statement compoundStmt;
    private final int returnType;
    
    public boolean hasParams()
    {
        return params != null;
    }
    
    public ArrayList<Param> getParams()
    {
        return this.params;
    }
    
    @Override
    public int getDeclType()
    {
        return DECL_TYPE_FUN;
    }
        
    public int getReturnType()
    {
        return this.returnType;
    }
    
    public String returnTypeString()
    {
        return returnType == TYPE_VOID ? "void" : "int";
    }
    
    @Override
    public String toString()
    {
        return returnTypeString() + " " + getId() 
             + " ( " + paramsString() + " ) " 
             + compoundStmt.toString();
    }
    
    private String paramsString()
    {
        StringBuilder sb = new StringBuilder();
        
        if (hasParams())
        {
            ArrayList<Param> temp = params;
            
            sb.append(temp.get(0));
            temp.remove(0);
            
            for (Param p : temp)
            {
                sb.append(", ").append(p.toString());
            }
        }
        else
        {
            sb.append("void");
        }
        
        return sb.toString();
    }
    
    @Override
    public void printMe(String spaces){
        System.out.println(spaces + "FunDeclaration");
        spaces += "    ";
        System.out.println(spaces + returnTypeString());
        System.out.println(spaces + "ID: " + getId());
        System.out.println(spaces + "(");
        if(this.hasParams()){
            for(int i = 0; i < this.params.size(); i ++) {
                if(i > 0) {
                    System.out.println(spaces + ",");
                }
                this.params.get(i).printMe(spaces);
                
            } 
        } else {
            System.out.println(spaces + "void");
        }
        System.out.println(spaces + ")");
        this.compoundStmt.printMe(spaces);
    }
    
    @Override
    public CodeItem genCode(ArrayList<String> globals)
        throws CodeGenerationException
    {
        Function function = null;
        
        String id = getId();
        if (!globals.contains(id))
        {
            globals.add(id);
        }
        else
        {
            try
            {
                throw new CodeGenerationException("Duplicate global found: " + id);
            }
            catch (CodeGenerationException ex)
            {
                Logger.getLogger(
                    FunDeclaration.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                System.exit(1);
            }
        }
        
        if (this.hasParams())
        {
            FuncParam firstParam = params.get(0).genCode();
            FuncParam lastParam = firstParam;
            
            // build a linked list of params
            for (Param p : params.subList(1, params.size()))
            {
                lastParam.setNextParam(p.genCode());
                lastParam = lastParam.getNextParam();
            }

            function = new Function(this.getDeclType(), this.getId(), firstParam);
        }
        else
        {
            function = new Function(this.getDeclType(), this.getId());
        }
        
        function.getTable().putAll(makeSymbolTable(compoundStmt, function));
        function.createBlock0();
        function.appendBlock(new BasicBlock(function));
        function.setCurrBlock(function.getLastBlock());
        function.getCurrBlock().insertFirst(new Operation(
                Operation.OperationType.UNKNOWN,
                function.getCurrBlock()
        ));
        compoundStmt.genCode(function, globals);
        
        return function;
    }
    
    // TODO: need to add globals
    private HashMap<String, Integer> makeSymbolTable(
        Statement compoundStmt, 
        Function function
    )
    {
        ArrayList<String> localVars = 
                ((CompoundStatement)compoundStmt).getLocalVars();
            
        HashMap<String, Integer> symbolTable = new HashMap<>();
        for (String id : localVars)
        {
            symbolTable.put(id, function.getNewRegNum());
        }
        // add function params to symbol table
        for (FuncParam p = function.getfirstParam(); p != null; p = p.getNextParam())
        {
            symbolTable.put(p.getName(), function.getNewRegNum());
        }
        
        return symbolTable;
    }
}
