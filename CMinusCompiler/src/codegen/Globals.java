/*
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: GameBoard.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: 
 */

package codegen;

import java.util.HashMap;

public class Globals {
    public Globals()
    {
        this.globals = new HashMap<>();
    }
    
    private final HashMap<String, Integer> globals;
    
    public HashMap<String, Integer> getGlobals()
    {
        return globals;
    }
    
    public void addGlobal(String id){}
}
