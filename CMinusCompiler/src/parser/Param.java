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
    
    public void printMe(String spaces) {
        System.out.println(spaces + "Param");
        spaces += "   ";
        System.out.println(spaces + "int");
        System.out.println(spaces + "ID: " +this.ID);
        if(this.isArray()) {
            System.out.println(spaces + "[");
            System.out.println(spaces + "]");
        }
    }
}
