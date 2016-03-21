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

public class FunDeclaration extends Declaration
{
    public FunDeclaration (String typeSpecifier, 
                           TokenType typeSpecifierType, 
                           String ID, 
                           Params params, 
                           CompoundStatement compoundStmt)
    {
        super(typeSpecifier, typeSpecifierType, ID);
        this.params = params;
        this.compoundStmt = compoundStmt;
    }
    
    private final Params params;
    private final CompoundStatement compoundStmt;
    
    @Override
    public String toString()
    {
        return typeSpecifier() + " " + ID() 
             + " (" + params.toString() + ") " 
             + compoundStmt.toString();
    }
}
