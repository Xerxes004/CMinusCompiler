/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Statement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Statement
 */

package parser;

import java.util.ArrayList;
import lowlevel.Function;

public abstract class Statement{
    
    public enum StatementType {
        SELECTION, RETURN, COMPOUND, EXPRESSION, ITERATION
    }
    
    @Override
    public abstract String toString();
    
    public abstract void printMe(String spaces);
    public abstract void genCode(Function function, ArrayList<String> globals)
        throws CodeGenerationException;
    public abstract StatementType getStatementType();
}
