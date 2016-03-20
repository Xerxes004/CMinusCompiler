/**
 * @author Wesley Kelly, Jimmy Von Eiff
 * @version 1.0
 *
 * File: Scanner.java 
 * Created: 6 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines the Scanner interface.
 */

package scanner;

public interface Scanner
{
    // get the next Token, consuming it
    public Token getNextToken();
    // peek at the next Token, not consuming it
    public Token peekNextToken();
}
