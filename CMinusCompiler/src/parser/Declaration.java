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
    public Declaration (String ID)
    {
        this.returnType = -1;
        this.ID = ID;
    }
    
    public Declaration(int returnType, String ID)
    {
        this.returnType = returnType;
        this.ID = ID;
    }
    
    public static final int DECL_TYPE_VAR = 0;
    public static final int DECL_TYPE_FUN = 1;
    public static final int TYPE_VOID = 0;
    public static final int TYPE_INT = 1;
    
    private final int returnType;
    
    private final String ID;
    

    
    public String getId()
    {
        return this.ID;
    }
    
    public abstract void printMe(String spaces);
    public abstract int getDeclType();
}
