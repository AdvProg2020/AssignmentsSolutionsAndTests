package Test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;

@RunWith(Parameterized.class)
public class ArshiATest {

    private static String inputFilePathPrefix = "src/test/java/Test/in/input";
    private static String outputFilePathPrefix = "src/test/java/Test/out/output";
    private static String resultFilePathPrefix = "src/test/java/Test/res/result";

    private static String commandsFilePathPrefix ="src/test/java/Test/";
    private static String javaFilePath ="src/main/java/";
    private static String classFilePath =javaFilePath+"/out";


    private static final int numberOfTests = 8;
    private static int currentTest = 0;



    @Parameterized.Parameter(0)
    public String inputFilePath;
    @Parameterized.Parameter(1)
    public String outputFilePath;
    @Parameterized.Parameter(2)
    public String resultFilePath;


    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        Object[][] retVal = new Object[numberOfTests][3];
        for(int i=1;i<=numberOfTests;i++) {
            retVal[i-1][0]=inputFilePathPrefix+i+".txt";
            retVal[i-1][1]=outputFilePathPrefix+i+".txt";
            retVal[i-1][2]=resultFilePathPrefix+i+".txt";
        }
        return Arrays.asList(retVal);
    }


    @BeforeClass
    @AfterClass
    public static void clearTrace() {
        System.err.println("Working Directory = " +
                System.getProperty("user.dir"));

        try {
            Process p = Runtime.getRuntime().exec("ls -R src");
            Scanner scanner=new Scanner(p.getInputStream());
            while (scanner.hasNextLine()) System.err.println(scanner.nextLine());
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        /* i used it both after and before the test just to make sure :D */
        try {
            Process p = Runtime.getRuntime().exec("sh "
                    + commandsFilePathPrefix +"clear.sh " + resultFilePathPrefix + " " + classFilePath);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadTest() {
        System.out.println("-----------------------");
        System.out.println(this.inputFilePath);
        System.out.println(this.outputFilePath);
        System.out.println(this.resultFilePath);
        System.out.println("########################");


        System.err.println();
        currentTest++;
        /* runs the submitted code and redirect its output to <resultFilePathPrefix>[i].txt */
        generateResults();
        /* check if the result are correct */
        assertTrue(checkOutputResults());
    }

    public void generateResults() {
        /* compiling the java files */
        try {
            Process p = Runtime.getRuntime().exec("sh "+commandsFilePathPrefix+"compile.sh "+ javaFilePath);
            Scanner scanner=new Scanner(p.getErrorStream());
            System.err.println("compile error:");
            while (scanner.hasNextLine()) System.err.println(scanner.nextLine());
            System.err.println("######################################");
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        /* running the compiled .class files to generate output from it*/
        try {
            Process p = Runtime.getRuntime().exec("sh "+
                    commandsFilePathPrefix +"run.sh " + inputFilePath + " " + classFilePath + " " + resultFilePath);
            Scanner scanner=new Scanner(p.getErrorStream());
            while (scanner.hasNextLine()) System.err.println(scanner.nextLine());

            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkOutputResults() {
        try {
            Scanner outputScanner = new Scanner(new FileInputStream(outputFilePath));
            Scanner resultScanner = new Scanner(new FileInputStream(resultFilePath));
            while (outputScanner.hasNextLine() && resultScanner.hasNextLine())
                if (!(outputScanner.nextLine().trim().equals(resultScanner.nextLine().trim())))
                    return false;
            System.err.println();
            if (outputScanner.hasNextLine() || resultScanner.hasNextLine()) return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

