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
        super(typeSpecifier, typeSpecifierType, ID);
        this.params = params;
        this.compoundStmt = compoundStmt;
    }
    
    private final ArrayList<Param> params;
    private final Statement compoundStmt;
    
    public boolean hasParams()
    {
        return params != null;
    }
    
    @Override
    public String toString()
    {
        return typeSpecifier() + " " + ID() 
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
}
