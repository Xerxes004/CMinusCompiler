/**
 * @author Wesley Kelly, Jimmy Von Eiff, Johnathan Coraccio
 * @version 1.0
 *
 * File: Expression.java 
 * Created: 21 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and
 * the authors. All rights reserved.
 *
 * Description: This class uses a CMinusScanner to read tokens from an input
 * file and parse them into a tree.
 */

package parser;

import java.util.ArrayList;
import lowlevel.Function;
import lowlevel.Operand;

public abstract class Expression 
{
    protected Expression()
    {
        this.isDest = false;
        this.isLeftSide = false;
    }
    public enum ExpressionType {
        NUM, VAR, CALL, BINARY, ASSIGN
    }
    
    private boolean isDest;
    private boolean isLeftSide;
    
    public abstract void printMe(String spaces);
    public abstract void genCode(Function function, ArrayList<String> globals);
    public abstract ExpressionType getExpressionType();
    
    public boolean isDest()
    {
        return isDest;
    }
    
    public void setIsDest(boolean isDest)
    {
        this.isDest = isDest;
    }
    
    public boolean isLeftSide()
    {
        return this.isLeftSide;
    }
    
    public void setIsLeftSide(boolean isLeftSide)
    {
        this.isLeftSide = isLeftSide;
    }
}
