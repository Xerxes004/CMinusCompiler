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
    public Declaration(String type, String ID)
    {
        this.typeSpecifier = type;
        this.ID = ID;
    }
    
    public static final int TYPE_VAR = 0;
    public static final int TYPE_FUN = 1;
    
    private final String typeSpecifier;
    
    private final String ID;
    
    public String returnType()
    {
        return this.typeSpecifier;
    }
    
    public String getId()
    {
        return this.ID;
    }
    
    public abstract void printMe(String spaces);
    public abstract int getType();
}
