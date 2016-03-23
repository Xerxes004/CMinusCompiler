package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        
        mulop = new ArrayList<>();
        mulop.add(TokenType.MULTIPLY);
        mulop.add(TokenType.DIVIDE);
        
        addop = new ArrayList<>();
        addop.add(TokenType.PLUS);
        addop.add(TokenType.MINUS);
        
        relop = new ArrayList<>();
        relop.add(TokenType.GTHAN);
        relop.add(TokenType.GTHAN_EQUAL);
        relop.add(TokenType.LTHAN);
        relop.add(TokenType.LTHAN_EQUAL);
        relop.add(TokenType.EQUAL);
        relop.add(TokenType.NOT_EQUAL);
        
        op = new ArrayList<>();
        op.addAll(mulop);
        op.addAll(addop);
        op.addAll(relop);
        
        firstStatement = new ArrayList<>();
        firstStatement.add(TokenType.NUM);
        firstStatement.add(TokenType.LPAREN);
        firstStatement.add(TokenType.ID);
        firstStatement.add(TokenType.SEMICOLON);
        firstStatement.add(TokenType.LCURLYBRACE);
        firstStatement.add(TokenType.IF);
        firstStatement.add(TokenType.WHILE);
        firstStatement.add(TokenType.RETURN);
        
        followStatement = new ArrayList<>();
        followStatement.add(TokenType.RCURLYBRACE);
        followStatement.add(TokenType.NUM);
        followStatement.add(TokenType.LPAREN);
        followStatement.add(TokenType.ID);
        followStatement.add(TokenType.SEMICOLON);
        followStatement.add(TokenType.LCURLYBRACE);
        followStatement.add(TokenType.IF);
        followStatement.add(TokenType.WHILE);
        followStatement.add(TokenType.RETURN);
        followStatement.add(TokenType.ELSE);
        
        followFactor = new ArrayList<>();
        followFactor.addAll(mulop);
        followFactor.addAll(relop);
        followFactor.addAll(addop);
        followFactor.add(TokenType.SEMICOLON);
        followFactor.add(TokenType.RPAREN);
        followFactor.add(TokenType.COMMA);
        followFactor.add(TokenType.ELSE);
        followFactor.add(TokenType.RBRACKET);
        
        followTerm = new ArrayList<>();
        followTerm.addAll(mulop);
        followTerm.addAll(relop);
        
        followExpression = new ArrayList<>();
        followExpression.add(TokenType.SEMICOLON);
        followExpression.add(TokenType.RPAREN);
        followExpression.add(TokenType.COMMA);
        followExpression.add(TokenType.ELSE);
        followExpression.add(TokenType.RPAREN);
    }
    
    private final ArrayList<Token> tokens;
    private int tokenPointer;
    private final scanner.CMinusScanner scanner;
    
    private final ArrayList<TokenType> op;
    private final ArrayList<TokenType> mulop;
    private final ArrayList<TokenType> addop;
    private final ArrayList<TokenType> relop;
    
    private final ArrayList<TokenType> firstStatement;
    
    private final ArrayList<TokenType> followStatement;
    private final ArrayList<TokenType> followExpression;
    private final ArrayList<TokenType> followTerm;
    private final ArrayList<TokenType> followFactor;
    
    // FINISHED
    // CHECKED
    public Program parseProgram() 
        throws CMinusParserError, IOException
    {
        ArrayList<Declaration> declarations = new ArrayList<>();
        TokenType token = advanceTokenPointer().getType();
        
        while (token != TokenType.EOF)
        {
            token = getTokenType();
            
            switch(token)
            {
            case INT:        
            case VOID:
                declarations.add(parseDeclaration());
                break;
                
            // should never happen
            case EOF:
                break;

            default:
                String msg = "Unexpected token in parseProgram: " 
                           + tokenString(getTokenType());
                throw new CMinusParserError(msg);
            }
        }
        
        return new Program(declarations);
    }
    
    // FINISHED
    // CHECKED
    private Declaration parseDeclaration() 
        throws CMinusParserError
    {
        String id = "WRONG";
        
        switch(getTokenType())
        {
            case INT:
                Token intToken = matchToken(TokenType.INT);
                id  = matchToken(TokenType.ID).getData();
                
                return parseDeclarationPrime(intToken, id);
                
            case VOID:
                Token voidToken = matchToken(TokenType.VOID);
                id = matchToken(TokenType.ID).getData();
                
                return parseFunDeclarationPrime(voidToken, id);
                
            default:
                throw new CMinusParserError("Died in parseDeclaration");
        }
    }
    
    // FINISHED
    // CHECKED
    private Declaration parseDeclarationPrime(Token typeToken, String id) 
        throws CMinusParserError
    {
        Declaration declaration = null;
        
        switch(getTokenType())
        {
            case LBRACKET:
                matchToken(TokenType.LBRACKET);
                
                Integer num = Integer.parseInt(
                    matchToken(TokenType.NUM).getData()
                );
                
                matchToken(TokenType.RBRACKET);
                matchToken(TokenType.SEMICOLON);
                
                declaration = new VarDeclaration(id, num);
                break;
                
            case SEMICOLON:
                matchToken(TokenType.SEMICOLON);
                
                declaration = new VarDeclaration(id);
                break;
                
            case LPAREN:
                declaration = parseFunDeclarationPrime(typeToken, id);
                break;
                
            default:
                throw new CMinusParserError(
                    "Invalid token in ParseDeclarationPrime: " +
                    tokenString(getToken())
                );
        }
        
        return declaration;
    }
    
    // FINISHED
    // CHECKED
    private Declaration parseFunDeclarationPrime(Token typeToken, String id) 
        throws CMinusParserError
    {
        ArrayList<Param> params = null;
        
        matchToken(TokenType.LPAREN);
        
        switch(getTokenType())
        {
            case VOID:
                matchToken(TokenType.VOID);
                break;

            case INT:
                params = parseParams();
                break;
                
            default:
                throw new CMinusParserError(
                    "Invalid token in ParseFunDeclarationPrime: " +
                    tokenString(getToken())
                );
        }

        matchToken(TokenType.RPAREN);

        return new FunDeclaration(
            typeToken.getData(), 
            typeToken.getType(),
            id,
            params, 
            parseCompoundStatement());
    }
    
    // FINISHED
    // CHECKED
    private ArrayList<Param> parseParams() 
        throws CMinusParserError
    {
        ArrayList<Param> params = new ArrayList<>();
        
        boolean hasComma = true;
        
        while(hasComma)
        {
            hasComma = false;
            
            matchToken(TokenType.INT);
            String id = matchToken(TokenType.ID).getData();
            
            boolean isArray = false;

            if (getTokenType() == TokenType.LBRACKET)
            {
                matchToken(TokenType.LBRACKET);
                matchToken(TokenType.RBRACKET);
                
                isArray = true;
            }
            
            if (getTokenType() == TokenType.COMMA)
            {
                matchToken(TokenType.COMMA);
                hasComma = true;
            }
            else if (getTokenType() != TokenType.RPAREN)
            {
                throw new CMinusParserError(
                    "Invalid token in ParseParams: " +
                    tokenString(getToken())
                );
            }

            params.add(new Param(id, isArray));
        }
        
        return params;
    }
    
    // FINISHED
    // CHECKED
    private Statement parseStatement() 
        throws CMinusParserError
    {
        switch (getTokenType())
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
                throw new CMinusParserError(
                    "Invalid token in parseStatement: " +
                    tokenString(getToken())
                );
        }
    }
    
    // FINISHED
    // CHECKED
    private Statement parseCompoundStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.LCURLYBRACE);
        
        ArrayList<Declaration> localDeclarations = new ArrayList<>(); 
        
        // FIND LOCAL DECLARATIONS
        while (getTokenType() == TokenType.INT)
        {
            matchToken(TokenType.INT);
            
            String id = matchToken(TokenType.ID).getData();

            Integer num = null;
            
            if (getTokenType() == TokenType.LBRACKET)
            {
                matchToken(TokenType.LBRACKET);
                
                num = Integer.parseInt(
                    matchToken(TokenType.NUM).getData()
                );
                
                matchToken(TokenType.RBRACKET);
            }

            matchToken(TokenType.SEMICOLON);
            
            if (num != null)
            {
                localDeclarations.add(new VarDeclaration(id, num));
            }
            else
            {
                localDeclarations.add(new VarDeclaration(id));
            }
        }
        
        ArrayList<Statement> statements = new ArrayList<>();
        
        // FIND ALL STATEMENTS
        while (firstStatement.contains(getTokenType()))
        {
            statements.add(parseStatement());
        }
        
        matchToken(TokenType.RCURLYBRACE);
        
        return new CompoundStatement(localDeclarations, statements);
    }
    
    // FINISHED
    // CHECKED
    private Statement parseExpressionStatement() 
        throws CMinusParserError
    {
        Statement expressionStatement = null;
        
        switch (getTokenType())
        {
            case NUM:
            case LPAREN:
            case ID:
                expressionStatement = new ExpressionStatement(
                    parseExpression()
                );
                break;

            // token might be a semicolon, so we rely on matchToken(SEMICOLON)
            // for error checking
            default:
                break;
        }
        
        matchToken(TokenType.SEMICOLON);
        
        return expressionStatement;
    }
    
    // FINISHED
    // CHECKED
    private Statement parseSelectionStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.IF);
        matchToken(TokenType.LPAREN);
        
        Expression expressionInParens = parseExpression();
        
        matchToken(TokenType.RPAREN);
        
        Statement ifStatement = parseStatement();
        
        Statement elseStatement = null;
        
        if (getTokenType() == TokenType.ELSE)
        {
            matchToken(TokenType.ELSE);
            elseStatement = parseStatement();
        }
        else if (!followStatement.contains(getTokenType()))
        {   
            throw new CMinusParserError(
                "Invalid token found in parseSelectionStatement" +
                tokenString(getToken())
            );
        }
        
        return new SelectionStatement(
            expressionInParens, 
            ifStatement, 
            elseStatement
        );
    }
    
    // FINISHED
    // CHECKED
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
    
    // FINISHED
    // CHECKED
    private Statement parseReturnStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.RETURN);
        return new ReturnStatement(parseExpressionStatement());
    }
    
    // FINISHED
    // CHECKED
    private Expression parseExpression() 
        throws CMinusParserError
    {
        switch (getTokenType())
        {
            case NUM:
                Integer num = Integer.parseInt(
                    matchToken(TokenType.NUM).getData()
                );
                
                return parseSimpleExpressionPrime(new Num(num));

            case LPAREN:
                matchToken(TokenType.LPAREN);
                Expression lSide = parseExpression();
                matchToken(TokenType.RPAREN);
                
                return parseSimpleExpressionPrime(lSide);

            case ID:
                String id = matchToken(TokenType.ID).getData();
                return parseExpressionPrime(id);

            default:
                throw new CMinusParserError("Failed in parseExpression");
        }
    }
    
    // FINISHED
    // CHECKED
    private Expression parseSimpleExpressionPrime(Expression lSide) 
        throws CMinusParserError
    {
        Expression simpleExpressionPrime = parseAdditiveExpressionPrime(lSide);
        
        if (relop.contains(getTokenType()))
        {
            matchToken(getTokenType());
            
            simpleExpressionPrime = new BinaryExpression(
                simpleExpressionPrime, 
                getOperator(getTokenType()), 
                parseAdditiveExpression()
            );
        }
        else if (!followExpression.contains(getTokenType()))
        {
            throw new CMinusParserError(
                "Invalid Token in parseSimpleExpressionPrime: " +
                tokenString(getToken())
            );
        }
        
        return simpleExpressionPrime;
    }
    
    // FINISHED
    // CHECKED
    private Expression parseAdditiveExpressionPrime(Expression lSide) 
        throws CMinusParserError
    {
        Expression termPrime = parseTermPrime(lSide);
        
        while (addop.contains(getTokenType()))
        {
            TokenType operator = getTokenType();
            
            switch (operator)
            {
                case PLUS:
                case MINUS:
                    matchToken(operator);
                    
                    termPrime = new BinaryExpression(
                        termPrime, 
                        getOperator(operator), 
                        parseTerm()
                    );
                    break;
                    
                // should never happen
                default:
                    throw new CMinusParserError("Died in parseAdditiveExpressionPrime");
            }
        }
        
        if (!followTerm.contains(getTokenType()))
        {
            throw new CMinusParserError(
                "Invalid Token in parseAdditiveExpressionPrime: " +
                tokenString(getToken())
            );
        }
        
        return termPrime;
    }
    
    
    // FINISHED
    // CHECKED
    private Expression parseTerm() 
        throws CMinusParserError
    {
        Expression term = parseFactor();
        
        while (mulop.contains(getTokenType()))
        {
            term = parseTermPrime(term);
        }
        
        if (!followTerm.contains(getTokenType()))
        {
            throw new CMinusParserError(
                "Invalid Token in parseTerm: " +
                tokenString(getToken())
            );
        }
        
        return term;
    }
    
    // FINISHED
    // CHECKED
    private Expression parseTermPrime(Expression lSide) 
        throws CMinusParserError
    {
        Expression termPrime = lSide;
        
        while (mulop.contains(getTokenType()))
        {
            TokenType operator = getTokenType();
            
            if (mulop.contains(operator))
            {
                matchToken(operator);

                termPrime = new BinaryExpression(
                        termPrime, 
                        getOperator(operator),
                        parseFactor()
                    );
            }
        }
        
        if (!followTerm.contains(getTokenType()))
        {
            throw new CMinusParserError(
                "Invalid Token in parseTerm: " +
                tokenString(getToken())
            );
        }
        
        return termPrime;
    }
    
    // FINISHED
    // CHECKED
    private Expression parseFactor() 
        throws CMinusParserError
    {
        Expression factor = null;
            
        switch (getTokenType())
        {
            case LPAREN:
                matchToken(TokenType.LPAREN);
                
                factor = parseExpression();
                
                matchToken(TokenType.RPAREN);
                break;
                
            case ID:
                String id = matchToken(TokenType.ID).getData();
                
                // this expression will be a var or call, 
                // decided in parseVarCallPrime
                factor = parseVarCallPrime(id);
                break;
            
            case NUM:
                Integer num = Integer.parseInt(
                    matchToken(TokenType.NUM).getData()
                );
                
                factor = new Num(num);
                break;
                
            default:
                throw new CMinusParserError(
                    "Unexpected token in parseFactor: " 
                    + tokenString(getToken())
                );
        }
        
        return factor;
    }
    
    // FINISHED
    // CHECKED
    private Expression parseVarCallPrime(String id) 
        throws CMinusParserError
    {
        Expression varCall = null;
        
        TokenType token = getTokenType();
        
        switch (token)
        {
            case LBRACKET:
                matchToken(TokenType.LBRACKET);
                Expression dereferenceExpression = parseExpression();
                matchToken(TokenType.RBRACKET);

                varCall = new Var(id, dereferenceExpression);
                break;

            case LPAREN:
                matchToken(TokenType.LPAREN);

                ArrayList<Expression> args = parseArgs();

                matchToken(TokenType.RPAREN);

                varCall = new Call(id, args);
                break;
                
            default:
                if (!followFactor.contains(getTokenType()))
                {
                    throw new CMinusParserError(
                        "Unexpected token in parseVarCallPrime: " 
                        + tokenString(getToken())
                    );
                }
        }
        
        return varCall;
    }
    
    // FINISHED
    // CHECKED
    private ArrayList<Expression> parseArgs() 
        throws CMinusParserError
    {
        switch (getTokenType())
        {
            case NUM:
            case LPAREN:
            case ID:
                ArrayList<Expression> args = null;
        
                args = new ArrayList<>();
                Expression arg = parseExpression();

                boolean hasComma = true;

                while (hasComma)
                {
                    matchToken(TokenType.COMMA);
                    args.add(parseExpression());

                    hasComma = (getTokenType() == TokenType.COMMA);
                }
                
                return args;
                
            default:
                throw new CMinusParserError(
                    "Invalid token in ParseArgs: " + 
                    tokenString(getToken())
                );
        }
    }
    
    // FINISHED
    // CHECKED
    private Expression parseExpressionPrime(String id) 
        throws CMinusParserError
    {
        Expression expressionPrime = null;
        
        switch (getTokenType())
        {
            case ASSIGN:
                matchToken(TokenType.ASSIGN);
                
                expressionPrime = new AssignExpression(
                    new Var(id),
                    parseExpression()
                );
                break;

            case LBRACKET:
                matchToken(TokenType.LBRACKET);
                
                Expression inBrackets = parseExpression();
                
                matchToken(TokenType.RBRACKET);
                
                expressionPrime = parseExpressionDoublePrime(id, inBrackets);
                break;
                
            case LPAREN:
                matchToken(TokenType.LPAREN);
                
                ArrayList<Expression> args = parseArgs();
                
                matchToken(TokenType.RPAREN);
                
                expressionPrime = parseSimpleExpressionPrime(new Call(id, args));
                break;
                
            default:
                if (!followExpression.contains(getTokenType()))
                {
                    throw new CMinusParserError(
                        "Invalid token in ParseExpressionPrime: " + 
                        tokenString(getToken())
                    );
                }
        }
        
        return expressionPrime;
    }
    
    // FINISHED
    private Expression parseExpressionDoublePrime(String id, Expression inBrackets) 
        throws CMinusParserError
    {
        Expression expressionDoublePrime = null;
        
        if (getTokenType() == TokenType.ASSIGN)
        {
            matchToken(TokenType.ASSIGN);
            
            expressionDoublePrime = new AssignExpression(
                new Var(id, inBrackets), 
                parseExpression()
            );
        }
        else if (op.contains(getTokenType()))
        {
            expressionDoublePrime = parseSimpleExpressionPrime(
                new Var(id, inBrackets)
            );
        }
        else
        {
            if (!followExpression.contains(getTokenType()))
            {
                throw new CMinusParserError(
                    "Found invalid token in parseExpressionDoublePrime: " + 
                        tokenString(getTokenType()));
            }
        }
        
        return expressionDoublePrime;
    }
    
    private Expression parseAdditiveExpression() 
        throws CMinusParserError
    {
        Expression additiveExpression = null;
        
        TokenType token = getTokenType();
        
        switch (token)
        {
            case LPAREN: 
            case ID:
            case NUM:
                additiveExpression = parseTerm();
                break;
                
            default:
                throw new CMinusParserError("Died in parseAdditiveExpression. Token is not in First(Term)");
        }

        boolean hasAddop = true;
        
        while (hasAddop)
        {
            if (addop.contains(getTokenType()))
            {
                Token operator = matchToken(getTokenType());
                
                additiveExpression = new BinaryExpression(
                    additiveExpression, 
                    getOperator(operator), 
                    parseTerm()
                );
            }
            else
            {
                if (!followTerm.contains(getTokenType()))
                {
                    throw new CMinusParserError("Died in parseAdditiveExpression. Token is not in Follow(Term)");
                }
            }
            
            hasAddop = (addop.contains(getTokenType()));
        }
        
        return additiveExpression;
    }
    
    
    private BinaryExpression.Operator getOperator(Token token) 
        throws CMinusParserError
    {
        return getOperator(token.getType());
    }
  
    private BinaryExpression.Operator getOperator(TokenType token) 
        throws CMinusParserError
    {
        switch (token)
        {
        case LTHAN:
            return Operator.LTHAN;
        case LTHAN_EQUAL:
            return Operator.LTHAN_EQUAL;
        case GTHAN:
            return Operator.GTHAN;
        case GTHAN_EQUAL:
            return Operator.GTHAN_EQUAL;
        case NOT_EQUAL:
            return Operator.NOT_EQUAL;
        case EQUAL:
            return Operator.EQUAL;
        case PLUS:
            return Operator.PLUS;
        case MINUS:
            return Operator.MINUS;
        case MULTIPLY:
            return Operator.MULTIPLY;
        case DIVIDE:
            return Operator.DIVIDE;
        
        default:
            throw new CMinusParserError("Operator not found: " + tokenString(token));
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
        if (getTokenType() == type)
        {
            return advanceTokenPointer();
        }
        else
        {
            String msg = "Token didn't match: " + tokenString(type);
                   msg+= " != " + tokenString(getTokenType());
            throw new CMinusParserError(msg);
        }
    }
    
    private String tokenString(Token token)
    {
        return tokenString(token.getType());
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
    
    private TokenType getTokenType()
    {
        return getToken().getType();
    }
    
    public static void main (String[] args)
    {
        
        try {
            CMinusParser parser = new CMinusParser("./test/inputs/test.txt");
            try {
                Program parsed = parser.parseProgram();
                parsed.printMe();
            } catch (CMinusParserError ex2) {
                System.out.println(ex2);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
