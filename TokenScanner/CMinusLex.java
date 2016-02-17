/**
 * @author Wesley Kelly, James Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Token.java 
 * Created: 7 February 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: This class defines a Token object for the C- compiler.
 */

%{
package tokenscanner;
import Token.*;
}%

%%

%class JFlex_CMinusScanner
%unicode

%{
	StringBuffer string = new StringBuffer();	
}%

<YYINITIAL> { 
	"else"			{ return new Token(TokenType.ELSE, 	"else"); }
	"if"            { return new Token(TokenType.IF, 	"if"); }
	"int"           { return new Token(TokenType.INT, 	"int"); }
	"return"        { return new Token(TokenType.RETURN,"return"); }
	"void"			{ return new Token(TokenType.VOID, 	"void"); }
	"while"			{ return new Token(TokenType.WHILE, "while"); }

	{ TraditionalComment }		{ }

	"+"				{ return new Token(TokenType.PLUS, "+"); }
}