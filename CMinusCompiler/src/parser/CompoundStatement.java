/*
 * @author Jimmy Von Eiff
 * @version 1
 * 
 * File: CompoundStatement.java
 * Created: March 21 2016
 * 
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description:
 */

package parser;

import java.util.*;

public class CompoundStatement extends Statement {
    public CompoundStatement(ArrayList localInput, ArrayList inputStmt) {
       this.localDecl = localInput;
       this.stmtList = inputStmt;
    }
    
    private ArrayList localDecl;
    private ArrayList stmtList;
    
    public String toString() {
        String toOutput = null;
        for(int i = 0; i < this.localDecl.size(); i++) {
            toOutput += this.localDecl.get(i).toString();
            toOutput += " ";
        }
        for(int i = 0; i < this.stmtList.size(); i++) {
            toOutput += this.stmtList.get(i).toString();
            toOutput += " ";
        }
        return toOutput;
    }
}
