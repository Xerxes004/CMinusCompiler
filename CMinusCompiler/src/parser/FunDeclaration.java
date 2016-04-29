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
import scanner.Token.TokenType;

public class FunDeclaration extends Declaration
{
    public FunDeclaration (String typeSpecifier, 
                           TokenType typeSpecifierType, 
                           String ID, 
                           ArrayList<Param> params, 
                           Statement compoundStmt)
    {
        super(typeSpecifier, ID);
        this.params = params;
        this.compoundStmt = compoundStmt;
    }
    
    private final ArrayList<Param> params;
    private final Statement compoundStmt;
    
    public boolean hasParams()
    {
        return params != null;
    }
    
    public int getType()
    {
        return TYPE_FUN;
    }
    
    @Override
    public String toString()
    {
        return returnType() + " " + getId() 
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
    
    public void printMe(String spaces){
        System.out.println(spaces + "FunDeclaration");
        spaces += "    ";
        System.out.println(spaces + this.returnType());
        System.out.println(spaces + "ID: " + this.getId());
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
}
