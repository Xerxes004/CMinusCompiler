import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import scanner.CMinusScanner;
import scanner.Token.*;
import scanner.Token;

/**
 *
 * @author jimmyvoneiff
 */
public class JUnitTestForScanner {
    
    public JUnitTestForScanner() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testTokens(){
        CMinusScanner testing;
        try {
            testing = new CMinusScanner("testfile.txt");
            assertEquals(TokenType.ELSE, testing.getNextToken().getType() );
            assertEquals(TokenType.IF, testing.getNextToken().getType() );
            assertEquals(TokenType.INT, testing.getNextToken().getType() );
            assertEquals(TokenType.RETURN, testing.getNextToken().getType() );
            assertEquals(TokenType.VOID, testing.getNextToken().getType() );
            assertEquals(TokenType.WHILE, testing.getNextToken().getType() );
            assertEquals(TokenType.PLUS, testing.getNextToken().getType() );
            assertEquals(TokenType.MINUS, testing.getNextToken().getType() );
            assertEquals(TokenType.MULTIPLY, testing.getNextToken().getType() );
            assertEquals(TokenType.DIVIDE, testing.getNextToken().getType() );
            assertEquals(TokenType.LTHAN, testing.getNextToken().getType() );
            assertEquals(TokenType.LTHAN_EQUAL, testing.getNextToken().getType() );
            assertEquals(TokenType.GTHAN, testing.getNextToken().getType() );
            assertEquals(TokenType.GTHAN_EQUAL, testing.getNextToken().getType() );
            assertEquals(TokenType.EQUAL, testing.getNextToken().getType() );
            assertEquals(TokenType.NOT_EQUAL, testing.getNextToken().getType() );
            assertEquals(TokenType.ASSIGN, testing.getNextToken().getType() );
            assertEquals(TokenType.SEMICOLON, testing.getNextToken().getType() );
            assertEquals(TokenType.COMMA, testing.getNextToken().getType() );
            assertEquals(TokenType.LPAREN, testing.getNextToken().getType() );
            assertEquals(TokenType.RPAREN, testing.getNextToken().getType() );
            assertEquals(TokenType.LBRACKET, testing.getNextToken().getType() );
            assertEquals(TokenType.RBRACKET, testing.getNextToken().getType() );
            assertEquals(TokenType.LCURLYBRACE, testing.getNextToken().getType() );
            assertEquals(TokenType.RCURLYBRACE, testing.getNextToken().getType() );
            assertEquals(TokenType.ID, testing.getNextToken().getType() );
            assertEquals(TokenType.NUM, testing.getNextToken().getType() );
            assertEquals(TokenType.DIVIDE, testing.getNextToken().getType() );
            assertEquals(TokenType.EOF, testing.getNextToken().getType() );
        } catch (IOException ex) {
            Logger.getLogger(JUnitTestForScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            testing = new CMinusScanner("empty.txt");
            assertEquals(TokenType.EOF, testing.peekNextToken().getType());
        } catch (IOException ex) {
            Logger.getLogger(JUnitTestForScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
