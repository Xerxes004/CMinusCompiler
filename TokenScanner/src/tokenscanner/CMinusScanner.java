
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CMinusScanner implements Scanner
{
    public CMinusScanner (String fileName)
    {
        try
        {
            inFile = new BufferedReader(new FileReader(fileName));
            nextToken = scanToken();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Input file not found, aborting.");
        }
    }
    
    private enum TokenState 
    {
        START, DONE, EOF, 
        IN_ID, IN_NUM, IN_LTHAN, IN_GTHAN, IN_EQ, IN_NOTEQ, IN_COMMENT
    }
    
    private BufferedReader inFile;
    private Token nextToken;
    
    @Override
    public Token getNextToken()
    {
        Token returnToken = nextToken;
        
        if (nextToken.getType() != Token.TokenType. EOF_TOKEN)
        {
            nextToken = scanToken();
        }
        
        return returnToken;
    }
    
    private Token scanToken()
    {
        try
        {
            Token scannedToken = null;
        
            TokenState state = TokenState.START;

            String tokenString = "";
            
            while (state != TokenState.DONE)
            {
                char nextChar = (char)inFile.read();
                
                switch (state)
                {
                case START:
                    if (nextChar == '\0')
                    {
                        state = TokenState.DONE;
                        
                    }
                }

            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(CMinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return scannedToken;
    }
    
    @Override
    public Token peekNextToken()
    {
        return this.nextToken;
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
    }
    
}