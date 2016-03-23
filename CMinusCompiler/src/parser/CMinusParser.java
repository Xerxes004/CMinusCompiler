package parser;

import java.io.IOException;
import java.util.ArrayList;
import scanner.CMinusScanner;
import scanner.Token;
import scanner.Token.TokenType;

public class CMinusParser 
{
    public CMinusParser(String fileName) 
        throws IOException
    {
        tokens = new ArrayList<>();
        scanner = new CMinusScanner(fileName);
        tokenPointer = 0;
     
        tokens.add(scanner.peekNextToken());
        
        while (scanner.peekNextToken().getType() != TokenType.EOF)
        {
            tokens.add(scanner.getNextToken());
        }
        
        tokens.add(scanner.peekNextToken());
    }
    
    private final ArrayList<Token> tokens;
    private int tokenPointer;
    private final scanner.CMinusScanner scanner;
    
    public Program parseProgram() throws CMinusParserError, IOException
    {
        Program program = new Program();
        TokenType token = advanceTokenPointer().getType();
        
        while (getToken().getType() != TokenType.EOF)
        {
            token = getToken().getType();
            
            switch(token)
            {
            case INT:        
            case VOID:
                program.addDeclaration(parseDeclaration());
                break;
                
            case EOF:
                break;

            default:
                String msg = "Unexpected token in parseProgram: " 
                           + tokenString(getToken().getType());
                throw new CMinusParserError(msg);
            }
        }
        
        return program;
    }
    
    private Declaration parseDeclaration() throws CMinusParserError
    {
        Declaration declaration = null;
        
        switch(getToken().getType())
        {
            case INT:
                Token typeToken = matchToken(TokenType.INT);
                Token idToken   = matchToken(TokenType.ID);
                
                declaration = parseDeclarationPrime(typeToken, idToken);
                break;
                
            case VOID:
                matchToken(TokenType.VOID);
        }
        
        return declaration;
    }
    
    // FINISHED
    private Declaration parseDeclarationPrime(Token typeToken, Token idToken) 
        throws CMinusParserError
    {
        Declaration declaration = null;
        
        switch(getToken().getType())
        {
            case LBRACKET:
                matchToken(TokenType.LBRACKET);
                Token numToken = matchToken(TokenType.NUM);
                matchToken(TokenType.RBRACKET);
                matchToken(TokenType.SEMICOLON);
                
                declaration = new VarDeclaration((String)idToken.getData(), 
                                  Integer.parseInt((String)numToken.getData()));
                break;
                
            case SEMICOLON:
                matchToken(TokenType.SEMICOLON);
                declaration = new VarDeclaration((String)idToken.getData());
                break;
                
            case LPAREN:
                declaration = parseFunDeclarationPrime(typeToken, idToken);
                break;
        }
        
        return declaration;
    }
    
    // FINISHED
    private Declaration parseFunDeclarationPrime(Token typeToken, Token idToken) 
        throws CMinusParserError
    {
        ArrayList<Param> params = null;
        
        matchToken(TokenType.LPAREN);
        
        switch(getToken().getType())
        {
        case VOID:
            break;
            
        case INT:
            params = parseParams();
            break;
        }
        
        matchToken(TokenType.RPAREN);
        
        return new FunDeclaration(
            (String)typeToken.getData(), 
            typeToken.getType(),
            (String)idToken.getData(),
            params, 
            parseCompoundStatement());
    }
    
    // FINISHED
    private ArrayList<Param> parseParams() 
        throws CMinusParserError
    {
        ArrayList<Param> params = new ArrayList<>();
        
        boolean hasComma = true;
        
        while(hasComma)
        {
            hasComma = false;
            
            matchToken(TokenType.INT);
            Token idToken = matchToken(TokenType.ID);

            if (getToken().getType() == TokenType.LBRACKET)
            {
                matchToken(TokenType.LBRACKET);
                matchToken(TokenType.RBRACKET);
            }
            
            if (getToken().getType() == TokenType.COMMA)
            {
                matchToken(TokenType.COMMA);
                hasComma = true;
            }

            params.add(new Param((String)idToken.getData()));
        }
        
        return params;
    }
    
    // FINISHED
    private Statement parseCompoundStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.LCURLYBRACE);
        
        ArrayList<Declaration> localDeclarations = new ArrayList<>(); 
        
        while (getToken().getType() == TokenType.INT)
        {
            matchToken(TokenType.INT);
            
            Token idToken = matchToken(TokenType.ID);

            Token numToken = null;
            
            if (getToken().getType() == TokenType.LBRACKET)
            {
                matchToken(TokenType.LBRACKET);
                numToken = matchToken(TokenType.NUM);
                matchToken(TokenType.RBRACKET);
            }

            matchToken(TokenType.SEMICOLON);
            
            if (numToken != null)
            {
                localDeclarations.add(
                    new VarDeclaration(
                        (String)idToken.getData(), 
                        (int)numToken.getData()));
            }
            else
            {
                localDeclarations.add(
                    new VarDeclaration((String)idToken.getData()));
            }
        }
        
        ArrayList<Statement> statements = new ArrayList<>();
        
        while (isInFirstSetOfStatement(getToken().getType()))
        {
            statements.add(parseStatement());
        }
        
        matchToken(TokenType.RCURLYBRACE);
        
        return new CompoundStatement(localDeclarations, statements);
    }
    
    private boolean isInFirstSetOfStatement(TokenType token)
    {
        switch (token)
        {
        case NUM:
        case LPAREN:
        case ID:
        case SEMICOLON:
        case LCURLYBRACE:
        case IF:
        case WHILE:
        case RETURN:
            return true;
            
        default:
            return false;
        }
    }
    
    private Statement parseStatement() 
        throws CMinusParserError
    {
        switch (getToken().getType())
        {
        case NUM:
        case LPAREN:
        case ID:
        case SEMICOLON:
            return parseExpressionStatement();
            
        case LCURLYBRACE:
            return parseCompoundStatement();
            
        case IF:
            return parseSelectionStatement();
            
        case WHILE:
            return parseIterationStatement();
            
        case RETURN:
            return parseReturnStatement();
            
        default:
            throw new CMinusParserError("Failed in parseStatement");
        }
    }
    
    // FINISHED
    private Statement parseExpressionStatement() 
        throws CMinusParserError
    {
        Statement expressionStatement = null;
        
        switch (getToken().getType())
        {
        case NUM:
        case LPAREN:
        case ID:
            expressionStatement = parseStatement();
            break;
        
        default:
            break;
        }
        
        matchToken(TokenType.SEMICOLON);
        
        return expressionStatement;
    }
    
    private Statement parseSelectionStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.IF);
        matchToken(TokenType.LPAREN);
        
        Expression expression = parseExpression();
        
        matchToken(TokenType.RPAREN);
        
        Statement ifStatement = parseStatement();
        
        Statement elseStatement = null;
        
        if (getToken().getType() == TokenType.ELSE)
        {
            matchToken(TokenType.ELSE);
            elseStatement = parseStatement();
        }
        else
        {
            switch (getToken().getType())
            {
                case RCURLYBRACE:
                case NUM:
                case LPAREN:
                case ID:
                case SEMICOLON:
                case LCURLYBRACE:
                case IF:
                case WHILE:
                case RETURN:
                    break;
                    
                default:
                    throw new CMinusParserError("Failed in parseSelectionStatement");
            }
        }
        
        return new SelectionStatement(expression, ifStatement, elseStatement);
    }
    
    private Statement parseIterationStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.WHILE);
        matchToken(TokenType.LPAREN);
        
        Expression expression = parseExpression();
        
        matchToken(TokenType.RPAREN);
        
        Statement statement = parseStatement();
        
        return new IterationStatement(expression, statement);
    }
    
    private Statement parseReturnStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.RETURN);
        return parseExpressionStatement();
    }
    
    private Expression parseExpression() 
        throws CMinusParserError
    {
        switch (getToken().getType())
        {
        case NUM:
            return parseSimpleExpressionPrime();
            
        case LPAREN:
            matchToken(TokenType.LPAREN);
            Expression lSide = parseExpression();
            matchToken(TokenType.RPAREN);
            Expression rSide = parseSimpleExpressionPrime();
            
            return new BinaryExpression(lSide, BinaryExpression.Operator.PARENTHESIZED , rSide);
            
            
        case ID:
            
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private Token getToken()
    {
        return tokens.get(tokenPointer);
    }
    
    private Token advanceTokenPointer()
    {
        Token token = getToken();
        tokenPointer++;
        
        return token;
    }
    
    private Token matchToken(Token.TokenType type)
        throws CMinusParserError
    {
        if (getToken().getType() == type)
        {
            return advanceTokenPointer();
        }
        else
        {
            String msg = "Token didn't match: " + tokenString(type);
                   msg+= " != " + tokenString(getToken().getType());
            throw new CMinusParserError(msg);
        }
    }
    
    private String tokenString(Token.TokenType type)
    {
        String typeString = "";
        
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
        
        return typeString;
    }
    
    public static int main (String[] args)
    {
        
        
        return 0;
    }
}
