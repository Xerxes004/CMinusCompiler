
/**
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: __NAME__.java 
 * Created: __DATE__, __TIME__
 *
 * Copyright {YEAR!!!} Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description:
 */

package tokenscanner;

import tokenscanner.Token.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CMinusScanner implements Scanner
{
    public CMinusScanner (String fileName)
    {
        try
        {
            inFile = new BufferedReader(new FileReader(fileName));
            
            BufferedReader tempFile = 
                new BufferedReader(new FileReader(fileName));
            
            fileLength = 0;
            
            while ((tempFile.read() > -1) && tempFile.ready())
            {
                fileLength++;
            }
            
            tempFile.close();
            
            nextToken = scanToken();
            
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Input file not found, aborting.");
        }
        catch (IOException ex)
        {
            System.out.println("Could not mark BufferedReader index to 0.");
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
    
    private BufferedReader inFile;
    private int fileLength;
    private Token nextToken;
    
    @Override
    public Token getNextToken()
    {
        Token returnToken = nextToken;
        
        if (nextToken.getType() != TokenType.EOF)
        {
            nextToken = scanToken();
        }
        
        return returnToken;
    }
    
    @Override
    public Token peekNextToken()
    {
        return this.nextToken;
    }
    
    private Token getReservedWithID(String id)
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
    
    public boolean isReserved(String id)
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
    
    private Token scanToken()
    {
        Token scannedToken = null;
        
        // BufferedReader read() method returns -1 as a signal that the EOF has
        // been reached.
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
                            System.out.println(msg);
                            System.exit(1);
                            break;
                        }
                    }
                    break;
                    
                case IN_NUM:
                    if (!Character.isDigit(currChar))
                    {
                        if (Character.isAlphabetic(currChar))
                        {
                            state = TokenState.IN_ERR;
                            tokenType = TokenType.ERROR;
                        }
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
                        if (Character.isDigit(currChar))
                        {
                            state = TokenState.IN_ERR;
                            tokenType = TokenType.ERROR;
                        }
                        else
                        {
                            state = TokenState.DONE;
                            tokenType = TokenType.ID;
                            appendChar = false;
                            inFile.reset();
                        }
                    }
                    break;
                    
                case IN_ERR:
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
                    appendChar = false;
                    if (currChar == '*')
                    {
                        state = TokenState.END_COMMENT;
                    }
                    // if we reach EOF while in a comment, we need to generate
                    // an EOF token and quit
                    else if (currChar == EOFChar)
                    {
                        state = TokenState.DONE;
                        tokenType = TokenType.EOF;
                    }
                    break;
                
                case END_COMMENT:
                    appendChar = false;
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
                        state = TokenState.DONE;
                        tokenType = TokenType.EOF;
                    }
                    // else stay in END_COMMENT state
                    break;
                    
                default:
                    System.out.println("");
                    System.out.println("ERROR, default reached in while loop!");
                    System.out.println("A token isn't being recognized.");
                    System.exit(1);
                    break;
                }
                
                if (appendChar)
                {
                    tokenString += currChar;
                }
                
                if (state == TokenState.DONE)
                {
                    scannedToken = new Token(tokenType, tokenString);
                    
                    if ((tokenType == TokenType.ID) && isReserved(tokenString))
                    {
                        scannedToken = getReservedWithID(tokenString);
                    }
                }
                
            }
        }
        catch (IOException ex)
        {
            System.out.println("An error occured when reading a character.");
        }
        
        return scannedToken;
    }
    
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
    
    public void printTokenData(Token token)
    {
        String data = (String)token.getData();
        if (token.getType() == TokenType.EOF)
        {
            data = "EOF";
        }
        System.out.print(data);
    }
    
    public static boolean staticIsReserved(String id)
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
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        CMinusScanner scanner = new CMinusScanner("testfile.txt");
        
        Token token = scanner.peekNextToken();
        
        while (token.getType() != TokenType.EOF)
        {
            token = scanner.getNextToken();
            
            System.out.print("");
            
            scanner.printFullTokenInfo(token);
            
            System.out.print("");
        }
    }
    
}