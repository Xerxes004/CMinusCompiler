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
%}

%%

%class JFlex_CMinusScanner
%unicode

%{
	StringBuffer string = new StringBuffer();	
%}

digit = [0-9]
letter = [a-zA-Z]
ID = {letter}+
NUM = {digit}+
whitespace = [ \t\n\r]+
comment = "/*" [^*] ~"*/" | "/*" "*"+ "/"


<YYINITIAL> { 
	/* keywords */
	"else"			{ return new Token(TokenType.ELSE, 	"else"); }
	"if"            { return new Token(TokenType.IF, 	"if"); }
	"int"           { return new Token(TokenType.INT, 	"int"); }
	"return"        { return new Token(TokenType.RETURN,"return"); }
	"void"			{ return new Token(TokenType.VOID, 	"void"); }
	"while"			{ return new Token(TokenType.WHILE, "while"); }

	/* whitespace and comment */
	{ whitespace }	{ /* ignore */ }
	{ comment }		{ /* ignore */ }
	
	/* operators */
	"+"				{ return new Token(TokenType.PLUS, 			"+"); }
	"-"				{ return new Token(TokenType.MINUS, 		"-"); }
	"*"				{ return new Token(TokenType.MULTIPLY, 		"*"); }
	"/"				{ return new Token(TokenType.DIVIDE, 		"/"); }
	"<"				{ return new Token(TokenType.LTHAN, 		"<"); }
	"<="			{ return new Token(TokenType.LTHAN_EQUAL, 	"<="); }
	">"				{ return new Token(TokenType.GTHAN, 		">"); }
	">="			{ return new Token(TokenType.GTHAN_EQUAL, 	">="); }
	"=="			{ return new Token(TokenType.EQUAL, 		"=="); }
	"!="			{ return new Token(TokenType.NOT_EQUAL, 	"!="); }
	"="				{ return new Token(TokenType.ASSIGN, 		"="); }
	";"				{ return new Token(TokenType.SEMICOLON, 	";"); }
	","				{ return new Token(TokenType.COMMA, 		","); }
	"("				{ return new Token(TokenType.LPAREN, 		"("); }
	")"				{ return new Token(TokenType.RPAREN, 		")"); }
	"["				{ return new Token(TokenType.LBRACKET, 		"["); }
	"]"				{ return new Token(TokenType.RBRACKET, 		"]"); }
	"{"				{ return new Token(TokenType.LCURLYBRACE, 	"{"); }
	"}"				{ return new Token(TokenType.RCULRYBRACE, 	"}"); }
	
	{ ID }			{ return new Token(TokenType.ID, 			yytext()); }
	{ NUM }			{ return new Token(TokenType.NUM, 			yytext()); }
	
	/* error fallback */
	[^]				{throw new Error("Char not recognized: " + yytext()); }
}