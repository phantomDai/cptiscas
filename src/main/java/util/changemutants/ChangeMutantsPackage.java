package util.changemutants;

import java.io.*;

import static java.io.File.separator;

/**
 * @author phantom
 * @Description 更改了变异体的名字，为了使程序能够正常运行，
 * 该类自动根据变异体所在的包改变背部的package信息。
 * @date 2018/11/1/001
 */
public class ChangeMutantsPackage {
    /**
     * 源程序的名字
     */
    private static final String[] OBJECTNAMES = {"SimpleLinear", "SimpleTree", "SequentialHeap",
            "FineGrainedHeap", "SkipQueue"};


    /**
     * 改变变异体内部package信息的主要方法
     */
    public void changePackage(){
        for (int i = 0; i < OBJECTNAMES.length; i++) {
            String dirpath = System.getProperty("user.dir") + separator + "src" + separator +
                    "mutants" + separator + OBJECTNAMES[i] ;
            File dirFile = new File(dirpath);
            String[] mutantNames = dirFile.list();
            for (int j = 0; j < mutantNames.length; j++) {
                String packageInfo = "package mutants." + OBJECTNAMES[i] + "." + mutantNames[j] + ";";
                String filePath = dirpath + separator + mutantNames[j] ;

                File fileMutants = new File(filePath);
                String[] mutants = fileMutants.list();
                for (int k = 0; k < mutants.length; k++) {
                    String subFilePath = filePath + separator + mutants[k] ;
                    File subFile = new File(subFilePath);
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(subFile));
                        StringBuffer stringBuffer = new StringBuffer();
                        String tempStr = "";
                        while((tempStr = bufferedReader.readLine()) != null){
                            if (tempStr.contains("package")){
                                stringBuffer.append(packageInfo);
                            }else {
                                stringBuffer.append(tempStr);
                            }
                        }
                        bufferedReader.close();
                        PrintWriter printWriter = new PrintWriter(new FileWriter(subFile));
                        printWriter.write(stringBuffer.toString());
                        printWriter.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ChangeMutantsPackage changeMutantsPackage = new ChangeMutantsPackage();
        changeMutantsPackage.changePackage();
    }





}
