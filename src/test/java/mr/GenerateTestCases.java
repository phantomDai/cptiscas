package mr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

/**
 * describe: the object that is responsible to automatically
 * generate all test cases
 *
 * @author phantom
 * @date 2019/03/08
 */
public class GenerateTestCases {

    /**the home of test cases*/
    private String home;

    private static final int MAXINDEX = 4;

    private static final int MAXLOOP = 5;

    private static final int[] NUMBEROFTHREADS = {2, 5, 10};

    private static final String[] MRNAMES = {"MR1","MR2","MR3","MR4","MR5","MR6","MR7","MR8","MR9",
            "MR10_1","MR10_2","MR10_3","MR11_1","MR11_2","MR11_3","MR12","MR13",
            "MR14","MR15","MR16_1","MR16_2","MR17","MR18_1","MR18_2","MR19_1","MR19_2"};

    private List<String> testcasenames;

    public GenerateTestCases(){
        home = System.getProperty("user.dir") + separator + "testsuite";
        testcasenames = new ArrayList<>();
    }


    public void generateTestCases(){
        for (int i = 0; i < MRNAMES.length; i++) {
            for (int j = 0; j < MAXINDEX; j++) {
                for (int k = 0; k < MAXLOOP; k++) {
                    for (int l = 0; l < NUMBEROFTHREADS.length; l++) {
                        if (j == 0 && l > 0){
                            break;
                        }
                        String content = contentOfTestCase(MRNAMES[i],
                                j, k, NUMBEROFTHREADS[l]);
                        String MRName = MRNAMES[i] + "Test" + "Index" + String.valueOf(j) +
                                "Loop" + String.valueOf(k) + "NumOfThreads"
                                + String.valueOf(NUMBEROFTHREADS[l]);
                        testcasenames.add(MRName + ".class");
                        writeTestCase(MRName, content);
                    }
                }
            }
        }

    }

    public String getHome() {
        return home;
    }

    /**
     * the content of test cases
     * @return the content
     */
    private String contentOfTestCase(String MR, int index,
                                     int loop, int numofThread){
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append("package mr;" + "\n");
        stringBuffer.append("import junit.framework.TestCase;" + "\n");
        stringBuffer.append("import org.junit.After;" + "\n");
        stringBuffer.append("import org.junit.Before;" + "\n");
        stringBuffer.append("import org.junit.Test;" + "\n");
        String MRName = MR + "Test" + "Index" + String.valueOf(index) +
                "Loop" + String.valueOf(loop) + "NumOfThreads"
                + String.valueOf(numofThread);
        stringBuffer.append("public class " + MRName + " extends"+ " TestCase{"
                + "\n");
        stringBuffer.append("\t" + MR + " mr;" + "\n");
        stringBuffer.append("\t" + "@Before" + "\n");
        stringBuffer.append("\t" + "public void setUp(){" + "\n");
        stringBuffer.append("\t\t" + "mr = new " + MR + "();" + "\n");
        stringBuffer.append("\t" + "}" + "\n");

        stringBuffer.append("\t" + "@After" + "\n");
        stringBuffer.append("\t" + "public void tearDown(){" + "\n");
        stringBuffer.append("\t\t" + "mr = null;" + "\n");
        stringBuffer.append("\t" + "}" + "\n");

        stringBuffer.append("\t" + "@Test" + "\n");
        stringBuffer.append("\t" + "public void testMR(){" + "\n");


        stringBuffer.append("\t\t" + "mr.executeService(" + String.valueOf(index)
                +","+ String.valueOf(loop)+ "," + String.valueOf(numofThread)
                + "," + "\"FineGrainedHeap\");" + "\n");
        stringBuffer.append("\t" + "}" + "\n");
        stringBuffer.append("}");
        return stringBuffer.toString();
    }


    private void writeTestCase(String name, String content){
        String filePath = this.home + separator + name + ".java";
        File file = new File(filePath);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void generateTestAll(){
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append("package mr;" + "\n");
        stringBuffer.append("import junit.framework.Test;" + "\n");
        stringBuffer.append("import junit.framework.TestSuite;" + "\n");
        stringBuffer.append("public class TestAll {" + "\n");
        stringBuffer.append("\t" + "public static Test suite(){" + "\n");
        stringBuffer.append("\t\t" + "TestSuite testSuite = new TestSuite(\"test all\");" + "\n");
        for (int i = 0; i < testcasenames.size(); i++) {
            stringBuffer.append("\t\t" + "testSuite.addTestSuite(" + testcasenames.get(i) + ");" +"\n");
        }
        stringBuffer.append("\t\t" + "return testSuite;" + "\n");
        stringBuffer.append("\t" + "}" + "\n");
        stringBuffer.append("}");

        writeTestCase("TestAll", stringBuffer.toString());
    }



    public static void main(String[] args) {
        GenerateTestCases gtc = new GenerateTestCases();
        gtc.generateTestCases();
        gtc.generateTestAll();
    }
}
