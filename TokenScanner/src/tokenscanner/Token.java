/**
 * 
 * @author Wesley Kelly, James Von Eiff
 * @version 1.0
 *
 * File: Token.java 
 * Created: 7 February 2016
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: This class defines a Token object for the C- compiler.
 */

package tokenscanner;

public class Token {
    
    public Token(TokenType type, Object data)
    {
        this.type = type;
        this.data = data;
    }
    
    public enum TokenType {
        ELSE_TOKEN, IF_TOKEN, 
        INT_TOKEN, VOID_TOKEN, 
        RETURN_TOKEN, 
        WHILE_TOKEN,
        PLUS_TOKEN, 
        MINUS_TOKEN, 
        MULTIPLY_TOKEN, DIVIDE_TOKEN, 
        LESSTHAN_TOKEN, LESSTHANEQUAL_TOKEN, 
        GREATERTHAN_TOKEN, GREATERTHANEQUAL_TOKEN, 
        EQUAL_TOKEN, NOTEQUAL_TOKEN, 
        ASSIGN_TOKEN, 
        SEMICOLON_TOKEN, 
        COMMA_TOKEN, 
        OPENPAREN_TOKEN, CLOSEPAREN_TOKEN, 
        OPENBRACKET_TOKEN, CLOSEBRACKET_TOKEN, 
        OPENCURLYBRACE_TOKEN, CLOSECURLYBRACE_TOKEN, 
        ERROR_TOKEN, EOF_TOKEN
    }
    
    private final TokenType type;
    private final Object data;
    
    public TokenType getType()
    {
        return this.type;
    }
    
    public Object getData()
    {
        return this.data;
    }
}
