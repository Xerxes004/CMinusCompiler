package parser;

public class Param 
{
    public Param(String ID, boolean isArray)
    {
        this.ID = ID;
        this.isArray = isArray;
    }
    
    private final String ID;
    private final boolean isArray;
    
    public String ID()
    {
        return this.ID;
    }
    
    public boolean isArray()
    {
        return this.isArray;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("int ")
          .append(ID)
          .append(isArray ? " [ ] " : "");
        return sb.toString();
    }
}
