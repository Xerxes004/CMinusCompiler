
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
        IN_ID, IN_NUM, IN_EQUIVALENT, IN_NOTEQUAL, 
        IN_LESSEQUAL, IN_GREATEREQUAL,
        BEGIN_COMMENT, IN_COMMENT, END_COMMENT
    }
    
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
                // whether or not we should append the current scanned char 
                boolean appendChar = true;
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
                    else if (Character.isWhitespace(currChar))
                    {
                        appendChar = false;
                        state = TokenState.START;
                    }
                    else
                    {
                        state = TokenState.DONE;
                        
                        switch (currChar)
                        {
                        case EOFChar:
                            tokenType = TokenType.EOF;
                            break;
                            
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
                            String msg =
                                "Character not recognized: " + currChar;
                            System.out.println(msg);
                            break;
                        }
                    }
                    break;
                    
                case IN_NUM:
                    if (!Character.isDigit(currChar))
                    {
                        state = TokenState.DONE;
                        tokenType = TokenType.NUM;
                        appendChar = false;
                        inFile.reset();
                    } 
                    else if (currChar == EOFChar)
                    {
                        state = TokenState.DONE;
                        tokenType = TokenType.EOF;
                    }
                    break;
                    
                case IN_ID:
                    System.out.println("\nID not supported yet, aborting");
                    System.exit(1);
                    break;
                    
                case IN_LESSEQUAL:
                    state = TokenState.DONE;
                    
                    if (currChar == '=')
                    {
                        tokenType = TokenType.LESSTHAN_EQUAL;
                    }
                    else
                    {
                        tokenType = TokenType.LESSTHAN;
                        appendChar = false;
                        inFile.reset();
                    }
                    break;
                    
                case IN_GREATEREQUAL:
                    state = TokenState.DONE;
                    
                    if (currChar == '=')
                    {
                        tokenType = TokenType.GREATERTHAN_EQUAL;
                    }
                    else
                    {
                        tokenType = TokenType.GREATERTHAN;
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
                    
                case BEGIN_COMMENT:
                    if (currChar == '*')
                    {
                        state = TokenState.IN_COMMENT;
                        appendChar = false;
                    } 
                    else if (currChar == EOFChar)
                    {
                        state = TokenState.DONE;
                        tokenType = TokenType.EOF;
                    }
                    else
                    {
                        state = TokenState.DONE;
                        tokenType = TokenType.DIVIDE;
                        currChar = '/';
                        inFile.reset();
                    }
                    break;
                    
                case IN_COMMENT:
                    appendChar = false;
                    if (currChar == '*')
                    {
                        state = TokenState.END_COMMENT;
                    }
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
                    //TODO: check for reserved keywords
                    scannedToken = new Token(tokenType, tokenString);
                }
                
            }
        }
        catch (IOException ex)
        {
            System.out.println("An error occured when reading a character.");
        }
        
        return scannedToken;
    }
    
    public void printToken(Token token)
    {
        TokenType type = token.getType();
        String data = (String)token.getData();
        
        System.out.print(data);
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        CMinusScanner scanner = new CMinusScanner("testfile.txt");
        
        Token token = scanner.getNextToken();
        
        while (token.getType() != TokenType.EOF)
        {
            scanner.printToken(token);
            System.out.print(", ");
            token = scanner.getNextToken();
        }
    }
    
}