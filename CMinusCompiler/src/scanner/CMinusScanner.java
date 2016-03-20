/**
 * @author Wesley Kelly, Jimmy Von Eiff
 * @version 1.0
 *
 * File: CMinusScanner.java 
 * Created: 6 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class implements the Scanner interface, and scans for C-
 * language tokens.
 */

package scanner;

import scanner.Token.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CMinusScanner implements Scanner
{
    /**
     * Initializes the class using the given filename.
     * @param fileName the name of the input file to be scanned for tokens
     * @throws java.io.FileNotFoundException if the input file is not found
     * @throws java.io.IOException if the BufferedReader fails
     */
    public CMinusScanner (String fileName)
        throws FileNotFoundException, IOException
    {
        inFile = new BufferedReader(new FileReader(fileName));
            
        BufferedReader tempFile = 
            new BufferedReader(new FileReader(fileName));

        int temp = 0;

        while ((tempFile.read() > -1) && tempFile.ready())
        {
            temp++;
        }

        fileLength = temp;

        tempFile.close();

        // load nextToken with the first token in the file. Note that this 
        // results in an EOF Token if the file is empty.
        try
        {
            nextToken = scanToken();
        }
        catch (CMinusScannerException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
    
    private enum TokenState 
    {
        START, DONE, EOF, 
        IN_ID, IN_NUM, IN_EQUIVALENT, IN_ERR,
        IN_NOTEQUAL, IN_LESSEQUAL, IN_GREATEREQUAL,
        BEGIN_COMMENT, IN_COMMENT, END_COMMENT
    }
    
    private static final Token[] RESERVED_TOKENS = 
    {
        new Token(TokenType.ELSE, "else"),
        new Token(TokenType.IF, "if"), 
        new Token(TokenType.INT, "int"), 
        new Token(TokenType.RETURN, "return"),
        new Token(TokenType.VOID, "void"), 
        new Token(TokenType.WHILE, "while")
    };
    
    private final BufferedReader inFile;
    // used to mark the entire BufferedReader
    private final int fileLength;
    private Token nextToken;
    
    /**
     * Gets the next token in the file, consuming it.
     * @return the next token
     */
    @Override
    public Token getNextToken()
    {
        Token returnToken = nextToken;
        
        if (nextToken.getType() != TokenType.EOF)
        {
            try
            {
                nextToken = scanToken();
            }
            catch (CMinusScannerException ex)
            {
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        }
        
        return returnToken;
    }
    
    /**
     * Peeks at the next token without consuming it.
     * @return the next token
     */
    @Override
    public Token peekNextToken()
    {
        return this.nextToken;
    }
    
    /**
     * Returns the reserved Token that corresponds to the given ID.
     * @param id the ID of the reserved Token to be returned
     * @return the reserved Token, null if not found
     */
    private Token getReservedTokenWithID(String id)
    {
        Token reservedToken = null;
        
        for (Token reserved : RESERVED_TOKENS)
        {
            if (id.equals(reserved.getData()))
            {
                reservedToken = reserved;
            }
        }
        
        return reservedToken;
    }
    
    /**
     * Detects if a String equates to a reserved word in the C Minus language.
     * @param id the identifier being checked
     * @return whether or not the value is reserved
     */
    public static boolean isReserved(String id)
    {
        boolean isReserved = false;
        
        for (Token reserved : RESERVED_TOKENS)
        {
            if (id.equals(reserved.getData()))
            {
                isReserved = true;
            }
        }
        
        return isReserved;
    }
    
    /**
     * Scans the input file for the next Token and returns it, consuming the 
     * string used to build it.
     * @return the next token found
     * @throws CMinusScannerException 
     */
    private Token scanToken()
        throws CMinusScannerException
    {
        Token scannedToken = null;
        
        // BufferedReader read() method returns -1 as a signal that the EOF has
        // been reached. This constant is used to check for that.
        final char EOFChar = (char) -1;
        
        try
        {
            TokenState state = TokenState.START;
            TokenType tokenType = TokenType.ERROR;
            String tokenString = "";
            
            while (state != TokenState.DONE)
            {
                // whether or not we should append the current scanned character
                boolean appendChar = true;
                
                // mark this location in the stream so we can repent later if
                // needed
                inFile.mark(fileLength);
                char currChar = (char)inFile.read();
                
                switch (state)
                {
                case START:
                    if (Character.isDigit(currChar))
                    {
                        state = TokenState.IN_NUM;
                    } 
                    else if (Character.isAlphabetic(currChar))
                    {
                        state = TokenState.IN_ID;
                    }
                    else if (currChar == EOFChar)
                    {
                        state = TokenState.DONE;
                        tokenType = TokenType.EOF;
                    }
                    else if (Character.isWhitespace(currChar))
                    {
                        // don't store white space
                        appendChar = false;
                    }
                    else
                    {
                        state = TokenState.DONE;
                        
                        switch (currChar)
                        {
                        case '+':
                            tokenType = TokenType.PLUS;
                            break;
                            
                        case '-':
                            tokenType = TokenType.MINUS;
                            break;
                            
                        case '*':
                            tokenType = TokenType.MULTIPLY;
                            break;
                            
                        case '/':
                            state = TokenState.BEGIN_COMMENT;
                            // don't want to append until we're sure it's not
                            // a comment
                            appendChar = false;
                            break;
                            
                        case '<':
                            state = TokenState.IN_LESSEQUAL;
                            break;
                            
                        case '>':
                            state = TokenState.IN_GREATEREQUAL;
                            break;
                            
                        case '=':
                            state = TokenState.IN_EQUIVALENT;
                            break;
                            
                        case '!':
                            state = TokenState.IN_NOTEQUAL;
                            break;
                            
                        case ';':
                            tokenType = TokenType.SEMICOLON;
                            break;
                        
                        case ',':
                            tokenType = TokenType.COMMA;
                            break;
                        
                        case '(':
                            tokenType = TokenType.LPAREN;
                            break;
                        
                        case ')':
                            tokenType = TokenType.RPAREN;
                            break;
                            
                        case '{':
                            tokenType = TokenType.LCURLYBRACE;
                            break;
                            
                        case '}':
                            tokenType = TokenType.RCURLYBRACE;
                            break;
                            
                        case '[':
                            tokenType = TokenType.LBRACKET;
                            break;
                            
                        case ']':
                            tokenType = TokenType.RBRACKET;
                            break;
                            
                        default:
                            String msg = "Char not recognized: " + currChar;
                            throw new CMinusScannerException(msg);
                        }
                    }
                    break;
                // end of START state    
                    
                case IN_NUM:
                    if (!Character.isDigit(currChar))
                    {
                        // if we find an alphabetical character, it's a lexical
                        // error
                        if (Character.isAlphabetic(currChar))
                        {
                            state = TokenState.IN_ERR;
                            tokenType = TokenType.ERROR;
                        }
                        // if it's not an alphabetical character, we're done
                        // building the NUM and need to back up the input and
                        // start the next token
                        else
                        {
                            state = TokenState.DONE;
                            tokenType = TokenType.NUM;
                            appendChar = false;
                            inFile.reset();
                        }
                    }
                    // else, it's a digit so we loop back to IN_NUM
                    break;
                    
                case IN_ID:
                    if (!Character.isAlphabetic(currChar))
                    {
                        // if we find a numeric character, it's a lexical error
                        if (Character.isDigit(currChar))
                        {
                            state = TokenState.IN_ERR;
                            tokenType = TokenType.ERROR;
                        }
                        // if it's not a numeric character, we're done
                        // building the NUM and need to back up the input and
                        // start the next token
                        else
                        {
                            state = TokenState.DONE;
                            tokenType = TokenType.ID;
                            appendChar = false;
                            inFile.reset();
                        }
                    }
                    // else, it's an alphabetical character so we loop back to
                    // IN_ID
                    break;
                    
                case IN_ERR:
                    // this error case is the same for both ID and NUM lexical
                    // errors, so both are directed here if an error is found
                    
                    // We stop looping back to IN_ERR once we find a non-alpha,
                    // non-numeric character, and then back up the input to 
                    // start finding the next token
                    if (!Character.isDigit(currChar) && 
                        !Character.isAlphabetic(currChar))
                    {
                        state = TokenState.DONE;
                        appendChar = false;
                        inFile.reset();
                    }
                    break;
                    
                case IN_LESSEQUAL:
                    state = TokenState.DONE;
                    
                    if (currChar == '=')
                    {
                        tokenType = TokenType.LTHAN_EQUAL;
                    }
                    else
                    {
                        tokenType = TokenType.LTHAN;
                        appendChar = false;
                        inFile.reset();
                    }
                    break;
                    
                case IN_GREATEREQUAL:
                    state = TokenState.DONE;
                    
                    if (currChar == '=')
                    {
                        tokenType = TokenType.GTHAN_EQUAL;
                    }
                    else
                    {
                        tokenType = TokenType.GTHAN;
                        appendChar = false;
                        inFile.reset();
                    }
                    break;
                    
                case IN_EQUIVALENT:
                    state = TokenState.DONE;
                    
                    if (currChar == '=')
                    {
                        tokenType = TokenType.EQUAL;
                    }
                    else
                    {
                        tokenType = TokenType.ASSIGN;
                        appendChar = false;
                        inFile.reset();
                    }
                    break;
                    
                case IN_NOTEQUAL:
                    state = TokenState.DONE;
                    
                    if (currChar == '=')
                    {
                        tokenType = TokenType.NOT_EQUAL;
                    }
                    else
                    {
                        tokenType = TokenType.ERROR;
                        appendChar = false;
                        inFile.reset();
                    }
                    break;
                    
                case BEGIN_COMMENT:
                    if (currChar == '*')
                    {
                        state = TokenState.IN_COMMENT;
                        appendChar = false;
                    } 
                    else
                    {
                        state = TokenState.DONE;
                        // have to force this character to be taken, because
                        // we opted not to take it in the START state to avoid
                        // having to un-get it if we enter IN_COMMENT state
                        currChar = '/';
                        tokenType = TokenType.DIVIDE;
                        inFile.reset();
                    }
                    break;
                    
                case IN_COMMENT:
                    
                    // if we're in a comment, we don't want to collect any data
                    appendChar = false;
                    
                    if (currChar == '*')
                    {
                        state = TokenState.END_COMMENT;
                    }
                    // if we reach EOF while in a comment, we need to generate
                    // an EOF token and quit, otherwise the EOF token won't be
                    // generated. We chose to do this rather than generate an
                    // error token because it doesn't hurt anything in the
                    // Parse step.  
                    else if (currChar == EOFChar)
                    {
                        state = TokenState.START;
                        inFile.reset();
                    }
                    break;
                
                case END_COMMENT:
                    // if we're in a comment, we don't want to collect any data
                    appendChar = false;
                    
                    // consume the last slash and start finding tokens again
                    if (currChar == '/')
                    {
                        state = TokenState.START;
                    }
                    else if (currChar != '*')
                    {
                        state = TokenState.IN_COMMENT;
                    }
                    // if we reach EOF while in a comment, we need to generate
                    // an EOF token and quit
                    else if (currChar == EOFChar)
                    {
                        state = TokenState.START;
                        inFile.reset();
                    }
                    // else stay in END_COMMENT state
                    break;
                    
                // this should never happen
                default:
                    String msg = "ERROR, default reached in while loop!\n";
                           msg+= "A token isn't being recognized.\n";
                           msg+= ":" + tokenString + "\n";
                    throw new CMinusScannerException(msg);
                }
                
                if (appendChar)
                {
                    tokenString += currChar;
                }
                
                // if we're done finding this token, we need to package it up
                // for return.
                if (state == TokenState.DONE)
                {
                    // ID's have the potential to be a reserved word
                    if ((tokenType == TokenType.ID) && isReserved(tokenString))
                    {
                        // generate a reserved token, discarding the wrongly-
                        // labeled ID token with data equal to a reserved word
                        scannedToken = getReservedTokenWithID(tokenString);
                    } 
                    else
                    {
                        scannedToken = new Token(tokenType, tokenString);
                    }
                }
            }
        }
        catch (IOException ex)
        {
            String msg = "An error occured when generating a Token. ";
                   msg+= ex.getMessage();
            System.out.println(msg);
        }
        
        if (scannedToken != null)
        {
            // This exception is caught in the getNextToken() function.
            if (scannedToken.getType() == TokenType.ERROR)
            {
                String msg  = "Error token found: ";
                       msg += scannedToken.getData();
                       msg += " caused a lexical error.";
                throw new CMinusScannerException(msg);
            }
        } 
        else
        // this should never happen, but we check just in case.
        {
            String msg = "No token was found in input file.";
            throw new CMinusScannerException(msg);
        }
        
        return scannedToken;
    }
    
    /**
     * Prints the token type and value on its own line.
     * This function is helpful for visually checking Scanner operation.
     * @param token the token to be printed
     */
    public void printFullTokenInfo(Token token)
    {
        TokenType type = token.getType();
        String typeString = "";
        String data = (String)token.getData();
        
        switch (type)
        {
            case ASSIGN:
                typeString = "ASSIGN";
                break;
            case COMMA:
                typeString = "COMMA";
                break;
            case DIVIDE:
                typeString = "DIVIDE";
                break;
            case ELSE:
                typeString = "ELSE";
                break;
            case EOF:
                typeString = "EOF";
                data = "EOF";
                break;
            case EQUAL:
                typeString = "EQUAL";
                break;
            case ERROR:
                typeString = "ERROR";
                break;
            case GTHAN:
                typeString = "GTHAN";
                break;
            case GTHAN_EQUAL:
                typeString = "GTHANEQ";
                break;
            case ID:
                typeString = "ID";
                break;
            case IF:
                typeString = "IF";
                break;
            case INT:
                typeString = "INT";
                break;
            case LBRACKET:
                typeString = "LBRACKET";
                break;
            case LCURLYBRACE:
                typeString = "LCURLY";
                break;
            case LTHAN:
                typeString = "LTHAN";
                break;
            case LTHAN_EQUAL:
                typeString = "LTHANEQ";
                break;
            case LPAREN:
                typeString = "LPAREN";
                break;
            case MINUS:
                typeString = "MINUS";
                break;
            case MULTIPLY:
                typeString = "MULT";
                break;
            case NOT_EQUAL:
                typeString = "NOTEQUAL";
                break;
            case NUM:
                typeString = "NUM";
                break;
            case PLUS:
                typeString = "PLUS";
                break;
            case RBRACKET:
                typeString = "RBRACKET";
                break;
            case RCURLYBRACE:
                typeString = "RCURLY";
                break;
            case RETURN:
                typeString = "RETURN";
                break;
            case RPAREN:
                typeString = "RPAREN";
                break;
            case SEMICOLON:
                typeString = "SEMICOLON";
                break;
            case VOID:
                typeString = "VOID";
                break;
            case WHILE:
                typeString = "WHILE";
                break;
            default:
                typeString = "INVALID TYPE FOUND";
                break;
        }
        
        System.out.println("[" + typeString + ":" + data + "]");
    }
    
    /**
     * Prints the data of a token to the screen, not on its own line.
     * This function is useful for seeing which token strings exist in the
     * input file.
     * @param token the token whose data is printed to the screen
     */
    public void printTokenData(Token token)
    {
        String data = (String)token.getData();
        
        if (token.getType() == TokenType.EOF)
        {
            data = "<<EOF>>";
        }
        
        System.out.print(data);
    }
    
    public static void main(String[] args)
    {
        try
        {
            CMinusScanner scanner = new CMinusScanner("gcd.c");
            
            Token token = scanner.peekNextToken();
            
            if (token.getType() == TokenType.EOF)
            {
                scanner.printFullTokenInfo(token);
                System.out.print(" ");
            }
            
            while (token.getType() != TokenType.EOF)
            {
                token = scanner.getNextToken();
                
                scanner.printFullTokenInfo(token);
                System.out.print(" ");
            }
            System.out.println("");
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
    
}