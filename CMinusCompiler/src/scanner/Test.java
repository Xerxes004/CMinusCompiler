/**
 * @author Johnathan Coraccio, Jimmy Von Eiff, Wesley Kelly
 * @version 1.0
 *
 * File: Test.java 
 * Created: 12 February, 2016
 *
 * Copyright 2016 Cedarville University, its Computer Science faculty, and the 
 * authors. All rights reserved.
 *
 * Description: This class is used to test the JFlex_CMinusScanner class
 */


package scanner;

import java.io.*;

public class Test
{
    public static void scan(String fileName, String saveName)
    {
        BufferedReader br = null;
        BufferedWriter bw = null;
        
        try
        {
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(saveName));
            
            JFlex_CMinusScanner scanner = new JFlex_CMinusScanner(br);
            
            while (scanner.peekNextToken().getType() != Token.TokenType.EOF)
            {
                Token tok = scanner.getNextToken();
                bw.write(String.format("%20.20s\t%s\n", tok.getType(), tok.getData()));
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found " + ex.getMessage());
        }
        catch (IOException ex)
        {
            System.out.println("Error while opening file " + ex.getMessage());
        }
        finally
        {
            try
            {
                if (bw != null)
                {
                    bw.close();
                }
            }
            catch (IOException ex)
            {
                System.out.println("Error while closing file: " + ex);
            }
        }
    }

    public static void main(String[] args)
    {
        String fileName = "./test/inputs/test.txt";
        String saveName = "./test/outputs/test.out";

        scan(fileName, saveName);
    }
}
