/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: CMinusParser.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class uses a CMinusScanner to read tokens from an input
 * file and parse them into a tree.
 */


package parser;

public class CMinusParserError extends Exception 
{
    public CMinusParserError(String msg) {
        super(msg);
    }
    
    public CMinusParserError(String expected, String found, String func)
    {
        message = "parser.CMinusParserError: expected "+
            expected+" but found "+
            found+ " in function "+func;
    }
    
    private String message;
    
    @Override
    public String getMessage()
    {
        return this.message != null ? this.message : super.getMessage();
    }
}
