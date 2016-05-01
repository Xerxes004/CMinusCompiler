/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Statement.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class defines a Statement
 */

package parser;

import lowlevel.Operation;

public abstract class Statement{
    
    public Statement() {
        
    }
    
    @Override
    public abstract String toString();
    
    public abstract void printMe(String spaces);
    public abstract Operation genCode();
}
