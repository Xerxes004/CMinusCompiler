package parser;

public class Num 
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
}
