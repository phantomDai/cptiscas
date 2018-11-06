package util.changemutants;

import java.io.*;

import static java.io.File.separator;

/**
 * @author phantom
 * @Description skipQueue类的包名换了，import语句需要重新插入
 * @date 2018/11/6/006
 */
public class ChangeSkipQueue {

    public void change(){
        String Dirpath = System.getProperty("user.dir") + separator + "src" + separator + "main" +
                separator + "java" + separator + "mutants" + separator + "SkipQueue" ;
        File dirFile = new File(Dirpath);
        String[] mutantsName = dirFile.list();
        for (int i = 0; i < mutantsName.length; i++) {
            String tempStr = Dirpath + separator + mutantsName[i] + separator + "SkipQueue.java";

            String importStr = "import mutants.SkipQueue." + mutantsName[i] + ".PrioritySkipList.Node;";
            File file = new File(tempStr);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuffer stringBuffer = new StringBuffer();
                String temp = "";
                while((temp = br.readLine()) != null){
                    if (temp.contains("import")){
                        stringBuffer.append(importStr + "\n");
                    }else {
                        stringBuffer.append(temp + "\n");
                    }
                }
                br.close();
                PrintWriter pw = new PrintWriter(new FileWriter(file));
                pw.write(stringBuffer.toString());
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ChangeSkipQueue change = new ChangeSkipQueue();
        change.change();
    }


}
