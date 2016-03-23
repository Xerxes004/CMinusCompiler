/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Declaration.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Declaration.
 */


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
    
    public abstract void printMe(String spaces);
}
