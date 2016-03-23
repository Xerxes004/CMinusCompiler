package parser;

public class Num extends Expression
{
    public Num(int value)
    {
        this.value = value;
    }
    
    private final int value;
    
    @Override
    public String toString()
    {
        return Integer.toString(value);
    }
    
    public void printMe(String spaces) {
        System.out.println(spaces + "NUM: " + this.value);
    }
}
