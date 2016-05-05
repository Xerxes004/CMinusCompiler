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

import java.util.ArrayList;
import lowlevel.CodeItem;

public abstract class Declaration 
{
    public Declaration (String ID)
    {
        this.returnType = -1;
        this.id = ID;
    }
    
    public Declaration(int returnType, String ID)
    {
        this.returnType = returnType;
        this.id = ID;
    }
    
    public static final int DECL_TYPE_VAR = 0;
    public static final int DECL_TYPE_FUN = 1;
    public static final int TYPE_VOID = 0;
    public static final int TYPE_INT = 1;
    
    private final int returnType;
    
    private final String id;
    

    
    public String getId()
    {
        return this.id;
    }
    
    public abstract void printMe(String spaces);
    public abstract int getDeclType();
    public abstract CodeItem genCode(ArrayList<String> globals)
        throws CodeGenerationException;
}
