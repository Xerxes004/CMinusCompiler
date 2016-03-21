/*
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: GameBoard.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: 
 */

package parser;

import scanner.Token.TokenType;

public abstract class Declaration {
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
}
