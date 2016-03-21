
/**
 * @author Jimmy Von Eiff
 * @version 1
 * 
 * File: Statement.java
 * Created: March 21 2016
 * 
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: 
 */
package parser;

import java.util.*;


public abstract class Statement{
    
    public Statement(Expression input) {
        this.expr = input;
    }
    private Expression expr;
    
    public void printStatement() {
        
    }
    
    public String toString() {
        return this.expr.toString();
    }
    
    
}
