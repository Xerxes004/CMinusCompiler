package parser;

import scanner.Token.TokenType;

public abstract class Declaration 
{
    public Declaration(String typeSpecifier, TokenType typeSpecifierType, String ID)
    {
        this.typeSpecifier = typeSpecifier;
        this.typeSpecifierType = typeSpecifierType;
        this.ID = ID;
    }
    
    private final String typeSpecifier;
    private final TokenType typeSpecifierType;
    
    private final String ID;
    private final TokenType IDType = TokenType.ID;
    
    public String typeSpecifier()
    {
        return this.typeSpecifier;
    }
    
    public TokenType typeSpecifierType()
    {
        return this.typeSpecifierType;
    }
    
    public String ID()
    {
        return this.ID;
    }
    
    public TokenType IDType()
    {
        return this.IDType;
    }
    
    public abstract void printDeclaration(String spaces);
}
