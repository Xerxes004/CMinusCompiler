package parser;

public class Param 
{
    public Param(String ID)
    {
        this.ID = ID;
        this.arraySize = -1;
        this.isArray = false;
    }
    public Param(String ID, int arraySize)
    {
        this.ID = ID;
        this.arraySize = arraySize;
        this.isArray = true;
    }
    
    private final String ID;
    private final boolean isArray;
    private final int arraySize;
    
    public String ID()
    {
        return this.ID;
    }
    
    public boolean isArray()
    {
        return this.isArray;
    }
    
    public int arraySize()
    {
        return this.arraySize;
    }
    
    @Override
    public String toString()
    {
        return "int " + ID + (isArray ? " [ " + arraySize + " ] " : "");
    }
}
