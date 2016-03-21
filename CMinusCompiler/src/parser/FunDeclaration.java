package parser;

import java.util.ArrayList;
import scanner.Token.TokenType;

public class FunDeclaration extends Declaration
{
    public FunDeclaration (String typeSpecifier, 
                           TokenType typeSpecifierType, 
                           String ID, 
                           ArrayList<Param> params, 
                           CompoundStatement compoundStmt)
    {
        super(typeSpecifier, typeSpecifierType, ID);
        this.params = params;
        this.compoundStmt = compoundStmt;
    }
    
    private final ArrayList<Param> params;
    private final CompoundStatement compoundStmt;
    
    @Override
    public String toString()
    {
        return typeSpecifier() + " " + ID() 
             + " ( " + paramsString() + " ) " 
             + compoundStmt.toString();
    }
    
    private String paramsString()
    {
        String string = "";
        
        if (params.size() > 0)
        {
            ArrayList<Param> temp = params;
            
            string += temp.get(0);
            temp.remove(0);
            
            for (Param p : temp)
            {
                string += ", " + p.toString();
            }
        }
        
        return string;
    }
}
