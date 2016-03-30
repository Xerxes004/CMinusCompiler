/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Parser.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This is the Parser interface for a code parser.
 */

package parser;

public interface Parser
{
    public abstract Program parseProgram() throws CMinusParserError;
}
