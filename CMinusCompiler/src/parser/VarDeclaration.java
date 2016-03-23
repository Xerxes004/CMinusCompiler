package parser;

import scanner.Token.TokenType;

public class VarDeclaration extends Declaration 
{
    public VarDeclaration(String ID)
    {
        super("int", TokenType.INT, ID);
        this.arraySize = -1;
        this.isArray = false;
    }
        
    public VarDeclaration(String ID, int arraySize) 
        throws CMinusParserError
    {
        super("int", TokenType.INT, ID);
        this.arraySize = arraySize;
        this.isArray = true;
    }

    private final int arraySize;
    private final boolean isArray;
    
    public int arraySize()
    {
        return arraySize;
    }
    
    public boolean isArray()
    {
        return isArray;
    }
    
    @Override
    public String toString()
    {
        String string = typeSpecifier() + " " + ID();
        
        if (isArray)
        {
            string += " [" + arraySize + "]"; 
        }
        
        string += " ;";
        
        return string;
    }
    
    public void printMe(String spaces) {
        System.out.println(spaces + "VarDeclaration");
        spaces += "    ";
        System.out.println(spaces + "int");
        System.out.println(spaces + "ID: " + this.ID());
        if(this.isArray()) {
            System.out.println(spaces + "[");
            System.out.println(spaces + "NUM: " + this.arraySize());
            System.out.println(spaces + "]");
        }
        System.out.println(spaces + ";");
    }
    
}
