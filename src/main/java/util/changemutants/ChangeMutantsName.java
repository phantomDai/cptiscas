package util.changemutants;


import java.io.*;

import static java.io.File.separator;

/**
 * @author phantom
 * @Description 该类参照mutantslogs中的文档将变异体的名字改为operator_index
 * 的形式，方便以后对实验结果进行统计分析
 * @date 2018/11/1/001
 */
public class ChangeMutantsName {

    /**
     * mutantslogs的目录
     */
    private static final String MUTANTSLOGSPACKAGE = "mutantslogs" ;

    /**
     * 所有变异体的存放位置
     */
    private static final String MUTANTSPACKAGE = "src" + separator + "main" + separator + "java" + separator + "mutants";


    /**
     * 源程序的名字
     */
    private static final String[] OBJECTNAMES = {"SimpleLinear", "SimpleTree", "SequentialHeap",
            "FineGrainedHeap", "SkipQueue"};


    private static final String[] scenarios = {"sequentialAndsequential", "sequentialAndconcurrent",
            "concurrentAndsequential", "concurrentAndconcurrent"};


    /**
     * 改变变异体的名字
     */
    public void changeMutantsName(){

        for (int i = 0; i < OBJECTNAMES.length; i++) {
            String filePath = System.getProperty("user.dir") + separator +
                    MUTANTSLOGSPACKAGE + separator + OBJECTNAMES[i] + separator + "mutants.log";
            File file = new File(filePath);
            if (!file.exists()){
                System.out.println("mutnats.log不存在，请检查文件");
            }
            //获取文件中的内容
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String temp = "";
                while((temp = br.readLine()) != null){
                    //解析一行到内容
                    String[] tempStrArray = temp.split(":");
                    String index = tempStrArray[0];
                    String operator = tempStrArray[1];
                    String newName = operator + "_" + index;
//                    changeName(OBJECTNAMES[i],index,newName);
                    changeMutantInfo(OBJECTNAMES[i],index,newName);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 改变变异体的名字
     * @param objectName 待测对象的名字
     * @param index 要改变名字的变异体的代号
     * @param newName 新的名字
     */
    private void changeName(String objectName, String index, String newName){
        String oldDirPath = System.getProperty("user.dir") + separator +
                MUTANTSPACKAGE + separator + objectName ;
        File parentDir = new File(oldDirPath);
        //所有变异体的名字
        String[] fileNameArray = parentDir.list();

        //要找的变异体的名字
        switch (objectName){
            case "SimpleLinear":
                objectName = "simpleLinear";
                break;
            case "SimpleTree":
                objectName = "simpleTree" ;
                break;
            case "SequentialHeap" :
                objectName = "sequentialHeap" ;
                break;
            case "FineGrainedHeap" :
                objectName = "fineGrainedHeap" ;
                break;
            case "SkipQueue":
                objectName = "skipQueue" ;
                break;
        }
        String target = objectName + index;

        for (int i = 0; i < fileNameArray.length; i++) {
            if (fileNameArray[i].equals(target)){
                String targetPath =  oldDirPath + separator + target;
                String newPath = oldDirPath + separator + newName;
                File oldFile = new File(targetPath);
                File newFile = new File(newPath);
                oldFile.renameTo(newFile);
            }
        }
    }


    /**
     * 改变mutantsinfo文件中的内容
     */
    private void changeMutantInfo(String objectName, String index, String newName){
        String path = System.getProperty("user.dir") + separator + "mutantsinfo" + separator + objectName;
        //要找的变异体的名字
        switch (objectName){
            case "SimpleLinear":
                objectName = "simpleLinear";
                break;
            case "SimpleTree":
                objectName = "simpleTree" ;
                break;
            case "SequentialHeap" :
                objectName = "sequentialHeap" ;
                break;
            case "FineGrainedHeap" :
                objectName = "fineGrainedHeap" ;
                break;
            case "SkipQueue":
                objectName = "skipQueue" ;
                break;
        }
        String target = objectName + index;

        for (int i = 0; i < scenarios.length; i++) {
            StringBuffer stringBuffer = new StringBuffer();

            String filePath = path + separator + scenarios[i] ;
            File file = new File(filePath);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String temp = "";

                while ((temp = br.readLine()) != null){
                    if (temp.equals(target)){
                        stringBuffer.append(newName + "\n");
                    }else {
                        stringBuffer.append(temp + "\n");
                    }
                }
                br.close();

                PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.write(stringBuffer.toString());
                printWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    public static void main(String[] args) {
        ChangeMutantsName changeMutantsName = new ChangeMutantsName();
        changeMutantsName.changeMutantsName();
    }




}
