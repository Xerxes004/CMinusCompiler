package parser;

import scanner.Token.TokenType;

public class VarDeclaration extends Declaration 
{
    public VarDeclaration(String typeSpecifier, TokenType typeSpecifierType, String ID)
    {
        super(typeSpecifier, typeSpecifierType, ID);
        this.arraySize = -1;
        this.isArray = false;
    }
        
    public VarDeclaration(String typeSpecifier, TokenType typeSpecifierType, String ID, int arraySize) 
        throws CMinusParserError
    {
        super(typeSpecifier, typeSpecifierType, ID);
        
        if (typeSpecifierType != TokenType.INT)
        {
            throw new CMinusParserError("Type specifier of " + ID + " is not int!");
        }
        
        this.arraySize = arraySize;
        this.isArray = true;
    }

    private final int arraySize;
    private final boolean isArray;
    
    public int arraySize()
    {
        return arraySize;
    }
    
    public boolean isArray()
    {
        return isArray;
    }
    
    @Override
    public String toString()
    {
        String string = typeSpecifier() + " " + ID();
        
        if (isArray)
        {
            string += " [" + arraySize + "]"; 
        }
        
        string += " ;";
        
        return string;
    }
}
