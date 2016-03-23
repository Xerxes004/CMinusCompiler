package parser;

import java.io.IOException;
import java.util.ArrayList;
import parser.BinaryExpression.Operator;
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
    
    public Program parseProgram() 
        throws CMinusParserError, IOException
    {
        ArrayList<Declaration> declarations = new ArrayList<>();
        TokenType token = advanceTokenPointer().getType();
        
        while (token != TokenType.EOF)
        {
            token = getToken().getType();
            
            switch(token)
            {
            case INT:        
            case VOID:
                declarations.add(parseDeclaration());
                break;
                
            case EOF:
                break;

            default:
                String msg = "Unexpected token in parseProgram: " 
                           + tokenString(getToken().getType());
                throw new CMinusParserError(msg);
            }
        }
        
        return new Program(declarations);
    }
    
    // FINISHED
    private Declaration parseDeclaration() 
        throws CMinusParserError
    {
        Declaration declaration = null;
        
        switch(getToken().getType())
        {
            case INT:
                Token intToken = matchToken(TokenType.INT);
                Token varIdToken  = matchToken(TokenType.ID);
                
                declaration = parseDeclarationPrime(intToken, varIdToken);
                break;
                
            case VOID:
                Token voidToken = matchToken(TokenType.VOID);
                Token funIdToken = matchToken(TokenType.ID);
                
                declaration = parseFunDeclarationPrime(voidToken, funIdToken);
                break;
                
            default:
                throw new CMinusParserError("Died in parseDeclaration");
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
                matchToken(TokenType.VOID);
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
                        Integer.parseInt((String)numToken.getData())));
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
                expressionStatement = new ExpressionStatement(parseExpression());
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
                Num num = new Num(Integer.parseInt((String)matchToken(TokenType.NUM).getData()));
                return parseSimpleExpressionPrime(num);

            case LPAREN:
                matchToken(TokenType.LPAREN);
                Expression lSide = parseExpression();
                matchToken(TokenType.RPAREN);
                Expression rSide = parseSimpleExpressionPrime(lSide);

                return new BinaryExpression(lSide, BinaryExpression.Operator.PARENTHESIZED, rSide);

            case ID:
                Token idToken = matchToken(TokenType.ID);
                return parseExpressionPrime(idToken);

            default:
                throw new CMinusParserError("Failed in parseExpression");
        }
    }
    
    private Expression parseSimpleExpressionPrime(Expression lSide) 
        throws CMinusParserError
    {
        Expression expression = null;
        
        BinaryExpression additiveExpression = parseAdditiveExpressionPrime();
        
        if (isInFollowSetOfAdditiveExpressionPrime(getToken().getType()))
        {
            BinaryExpression.Operator operator = isRelop(getToken().getType());

            if (operator == BinaryExpression.Operator.SIMPLE_EXPRESSION)
            {
                expression = new BinaryExpression(additiveExpression, operator, null);
            }
            else
            {
                advanceTokenPointer();
                expression = new BinaryExpression(
                    additiveExpression, operator, parseAdditiveExpression());
            }
        }
        else
        {
            throw new CMinusParserError("Token is not in follow set of Additive Expression Prime");
        }
        
        return expression;
    }
    
    private Expression parseExpressionPrime(Token idToken) 
        throws CMinusParserError
    {
        switch (getToken().getType())
        {
            case ASSIGN:
                matchToken(TokenType.ASSIGN);
                return parseExpression();

            case LBRACKET:
                matchToken(TokenType.LBRACKET);
                Expression expression = parseExpression();
                matchToken(TokenType.RBRACKET);

                if (getToken().getType() == TokenType.ASSIGN)
                {
                    matchToken(TokenType.ASSIGN);
                    return new AssignExpression(expression, parseExpression());
                }
                else
                {

                }

            case LPAREN:
            case MULTIPLY:
            case DIVIDE:
            case PLUS:
            case MINUS:
            case LTHAN:
            case LTHAN_EQUAL:
            case GTHAN:
            case GTHAN_EQUAL:
            case EQUAL:
            case NOT_EQUAL:
            case SEMICOLON:
            case RPAREN:
            case COMMA:
            case ELSE:
            case RBRACKET:
            
        }
    }
    
    private Expression parseAdditiveExpression()
    {
        Expression lSide = null;
        
        switch (getToken().getType())
        {
            case LPAREN: 
            case ID:
            case NUM:
                lSide = parseTerm();
                break;
            }

            switch (getToken().getType())
            {
            case PLUS:
                operator = Operator.ADD;
                return new BinaryExpression(lSide, operator, parseTerm());

            case MINUS:
                operator = Operator.SUB;
                return new BinaryExpression(lSide, operator, parseTerm());
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    private BinaryExpression.Operator isRelop(TokenType token)
    {
        switch (token)
        {
        case LTHAN:
            return BinaryExpression.Operator.LTHAN;
        case LTHAN_EQUAL:
            return BinaryExpression.Operator.LTHAN_EQUAL;
        case GTHAN:
            return BinaryExpression.Operator.GTHAN;
        case GTHAN_EQUAL:
            return BinaryExpression.Operator.GTHAN_EQUAL;
        case NOT_EQUAL:
            return BinaryExpression.Operator.NOT_EQUAL;
        case EQUAL:
            return BinaryExpression.Operator.EQUAL;
        
        default:
            return BinaryExpression.Operator.SIMPLE_EXPRESSION;
        }
    }
    
    private boolean isInFollowSetOfAdditiveExpressionPrime(TokenType token) 
        throws CMinusParserError
    {
        switch (token)
        {
        case LTHAN:
        case LTHAN_EQUAL:
        case GTHAN:
        case GTHAN_EQUAL:
        case NOT_EQUAL:
        case EQUAL:
        case SEMICOLON:
        case RPAREN:
        case COMMA:
        case ELSE:
        case RBRACKET:
            return true;
            
        default:
            return false;
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
