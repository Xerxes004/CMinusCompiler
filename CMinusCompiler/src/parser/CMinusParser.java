/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: CMinusParser.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class uses a CMinusScanner to read tokens from an input
 * file and parse them into a tree.
 */

package parser;

import java.io.IOException;
import java.util.ArrayList;
import parser.BinaryExpression.Operator;
import scanner.CMinusScanner;
import scanner.Token;
import scanner.Token.TokenType;

public class CMinusParser 
{
    /**
     * Constructor for CMinusParser.
     * @param fileName name of the input file
     * @throws IOException Throws an exception when errors occur with the input
     * file.
     */
    public CMinusParser(String fileName) 
        throws IOException
    {
        this.tokens = new ArrayList<>();
        CMinusScanner scanner = new CMinusScanner(fileName);
        tokenPointer = 0;
     
        tokens.add(scanner.peekNextToken());
        
        while (scanner.peekNextToken().getType() != TokenType.EOF)
        {
            tokens.add(scanner.getNextToken());
        }
        
        tokens.add(scanner.peekNextToken());
        
        // Relevant First and Follow sets
        
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
        
        operators = new ArrayList<>();
        operators.addAll(mulop);
        operators.addAll(addop);
        operators.addAll(relop);
        
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
        
        followExpression = new ArrayList<>();
        followExpression.add(TokenType.SEMICOLON);
        followExpression.add(TokenType.RPAREN);
        followExpression.add(TokenType.COMMA);
        followExpression.add(TokenType.ELSE);
        followExpression.add(TokenType.RBRACKET);
        
        followTerm = new ArrayList<>();
        followTerm.addAll(addop);
        followTerm.addAll(relop);
        followTerm.addAll(followExpression);
    }
    
    // tokens stored from input file
    private final ArrayList<Token> tokens;
    // the token pointer to keep track of the current token
    private int tokenPointer;
    
    // Operators, First and Follow sets
    private final ArrayList<TokenType> operators;
    private final ArrayList<TokenType> mulop;
    private final ArrayList<TokenType> addop;
    private final ArrayList<TokenType> relop;
    
    private final ArrayList<TokenType> firstStatement;
    
    private final ArrayList<TokenType> followStatement;
    private final ArrayList<TokenType> followExpression;
    private final ArrayList<TokenType> followTerm;
    private final ArrayList<TokenType> followFactor;
    
    /**
     * Parse the program input to the parser.
     * @return a Program tree
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax.
     */
    public Program parseProgram() 
        throws CMinusParserError
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
    
    /**
     * Parses a declaration.
     * @return a declaration
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parses a declaration prime.
     * @param typeToken the type of declaration
     * @param id the id of the declaration
     * @return a Declaration
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parses a FunDeclaration prime.
     * @param typeToken the type of declaration
     * @param id the id of the declaration
     * @return a FunDeclaration
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
    private FunDeclaration parseFunDeclarationPrime(Token typeToken, String id) 
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
    
    /**
     * Parses params.
     * @return an ArrayList of params
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parses a statement.
     * @return a Statement
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parses a compound statement.
     * @return a CompoundStatement
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
    private CompoundStatement parseCompoundStatement() 
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
    
    /**
     * Parses an expression statement.
     * @return an ExpressionStatement
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
    private ExpressionStatement parseExpressionStatement() 
        throws CMinusParserError
    {
        ExpressionStatement expressionStatement = null;
        
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
    
    /**
     * Parses a selection statement.
     * @return a SelectionStatement
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
    private SelectionStatement parseSelectionStatement() 
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
    
    /**
     * Parses an iteration statement.
     * @return an IterationStatement
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
    private IterationStatement parseIterationStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.WHILE);
        matchToken(TokenType.LPAREN);
        
        Expression expression = parseExpression();
        
        matchToken(TokenType.RPAREN);
        
        Statement statement = parseStatement();
        
        return new IterationStatement(expression, statement);
    }
    
    /**
     * Parses a return statement.
     * @return a ReturnStatement
     * @throws CMinusParserError 
     */
    private ReturnStatement parseReturnStatement() 
        throws CMinusParserError
    {
        matchToken(TokenType.RETURN);
        return new ReturnStatement(parseExpressionStatement());
    }
    
    /**
     * Parses an Expression.
     * @return an Expression.
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parses a simple expression.
     * @param lSide the lefthand expression of a simple expression
     * @return an Expression
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
    private Expression parseSimpleExpressionPrime(Expression lSide) 
        throws CMinusParserError
    {
        Expression simpleExpressionPrime = parseAdditiveExpressionPrime(lSide);
        
        if (relop.contains(getTokenType()))
        {
            Token operator = matchToken(getTokenType());
            
            simpleExpressionPrime = new BinaryExpression(
                simpleExpressionPrime, 
                getOperator(operator), 
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
    
    /**
     * Parse a term.
     * @return an Expression
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parse a term prime.
     * @param lSide the lefthand side of a term prime.
     * @return an Expression
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parse a factor.
     * @return an Expression
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parse a varcall prime
     * @param id the id of the var or call
     * @return an Expression
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
    
    /**
     * Parse the args.
     * @return an ArrayList of Expression args
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
    private ArrayList<Expression> parseArgs() 
        throws CMinusParserError
    {
        switch (getTokenType())
        {
            case LPAREN:
            case NUM:
            case ID:
                ArrayList<Expression> args = new ArrayList<>();
                
                args.add(parseExpression());
                
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
    
    /**
     * Parse an expression prime.
     * @param id the id of the expression
     * @return an Expression
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
                expressionPrime = parseSimpleExpressionPrime(new Var(id));
                
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
    
    /**
     * Parse an expression double prime.
     * @param id the id of the expression
     * @param inBrackets the value in the brackets of the expression
     * @return an Expression
     * @throws CMinusParserError throws errors when the file does not have
     * correct syntax. 
     */
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
        else if (operators.contains(getTokenType()))
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
    
    /**
     * Parse an additive expression.
     * @return
     * @throws CMinusParserError 
     */
    private Expression parseAdditiveExpression() 
        throws CMinusParserError
    {
        Expression additiveExpression = null;
        
        switch (getTokenType())
        {
            case LPAREN: 
            case ID:
            case NUM:
                additiveExpression = parseTerm();
                break;
                
            default:
                throw new CMinusParserError(
                    "Died in parseAdditiveExpression. "+
                    "Token is not in First(Term)"
                );
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
    
    // FINISHED
    // CHECKED
    private Expression parseAdditiveExpressionPrime(Expression lSide) 
        throws CMinusParserError
    {
        Expression additiveExpressionPrime = parseTermPrime(lSide);
        
        while (addop.contains(getTokenType()))
        {
            TokenType operator = getTokenType();
            
            matchToken(getTokenType());
            
            additiveExpressionPrime = new BinaryExpression(
                    additiveExpressionPrime, 
                    getOperator(operator), 
                    parseTerm()
                );
        }
        
        if (!followTerm.contains(getTokenType()))
        {
            throw new CMinusParserError(
                "Invalid Token in parseAdditiveExpressionPrime: " +
                tokenString(getToken())
            );
        }
        
        return additiveExpressionPrime;
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
            CMinusParser parser = new CMinusParser("./test/inputs/gcd.c");
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
