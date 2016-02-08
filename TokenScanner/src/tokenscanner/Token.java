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
        ELSE, 
        IF, 
        WHILE,
        INT, VOID, 
        RETURN, 
        PLUS, MINUS, 
        MULTIPLY, DIVIDE, 
        LESSTHAN, LESSTHAN_EQUAL, 
        GREATERTHAN, GREATERTHAN_EQUAL, 
        EQUAL, NOT_EQUAL, 
        ASSIGN, 
        SEMICOLON, 
        COMMA,
        ID, NUM,
        LPAREN, RPAREN, 
        LBRACKET, RBRACKET, 
        LCURLYBRACE, RCURLYBRACE, 
        ERROR, EOF
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
