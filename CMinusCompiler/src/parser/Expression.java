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

import lowlevel.Function;
import lowlevel.Operand;

public abstract class Expression 
{
    public enum ExpressionType {
        NUM, VAR, CALL, BINARY, ASSIGN
    }
    public abstract void printMe(String spaces);
    public abstract void genCode(Function function);
    public abstract ExpressionType getExpressionType();
}
