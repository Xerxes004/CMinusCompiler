package tokenscanner;

import java.io.*;

/**
 * Compiler Theory & Practice
 * Dr. Gallagher
 * Project 1
 * Test.java
 * @author Johnathan Coraccio
 * Date: February 12, 2016
 */
public class Test {
    
    public static void main(String [] args){
        String fileName = "C:\\Users\\Racci\\Source\\Repos\\Compiler\\TokenScanner\\src\\tokenscanner\\exceptionTest.txt";
        String saveName = "C:\\Users\\Racci\\Source\\Repos\\Compiler\\TokenScanner\\src\\tokenscanner\\JFLEXoutput.txt";
        
        scan(fileName, saveName);
    }
    
    public static void scan(String fileName, String saveName){
        BufferedReader br = null;
        BufferedWriter bw = null;
        try{
            br = new BufferedReader(new FileReader(fileName));
            bw = new BufferedWriter(new FileWriter(saveName));
            JFlex_CMinusScanner scanner = new JFlex_CMinusScanner(br);
            while(scanner.peekNextToken().getType() != Token.TokenType.EOF){
                Token tok = scanner.getNextToken();
                bw.write(String.format("%s\t\t\t%s\n", tok.getType(), tok.getData()));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found " + ex);
        } catch (IOException ex){
            System.out.println("Error while opening file " + ex);
        } finally {
            try{
                if(bw != null){
                    bw.close();
                }
            }
            catch(IOException ex){
                System.out.println("Error while closing file: " + ex);
            }
        }
    }
}