int y;

void main(void)
{
    int x;
    x = 0;

    if (x == 1)
    {   
        if (x != 2)
        {
            x = 2;
        }
        else
        {
            x = 3;
        }
    }
    else
    {
       if (x != 2)
        {
            x = 2;
        }
        else
        {
            if (x != 2)
            {
                x = 2;
            }
            else
            {
                x = 3;
            }
        }
    }

    return x;
}