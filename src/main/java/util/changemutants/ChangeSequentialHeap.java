package util.changemutants;

import java.io.*;

import static java.io.File.separator;

/**
 * @author phantom
 * @Description 该类主要是为了改变sequentialHeap类，将add、removeMain、swap方法
 * 加上synchronized关键字
 * @date 2018/11/6/006
 */
public class ChangeSequentialHeap {

    public void changeMethod(){
        String Dirpath = System.getProperty("user.dir") + separator + "src" + separator + "main" +
                separator + "java" + separator + "mutants" + separator + "SequentialHeap" ;

        File dirFile = new File(Dirpath);
        String[] mutantsName = dirFile.list();
        for (int i = 0; i < mutantsName.length; i++) {
            String tempStr = Dirpath + separator + mutantsName[i] + separator + "SequentialHeap.java" ;
            File file = new File(tempStr);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuffer stringBuffer = new StringBuffer();
                String temp = "";
                while((temp = br.readLine()) != null){
                    if (temp.contains("add")){
                        stringBuffer.append("  public synchronized void add(T item, int priority) {" + "\n");
                    }else if (temp.contains("removeMin")){
                        stringBuffer.append("  public synchronized T removeMin() {" + "\n");
                    }else if (temp.contains("void swap")){
                        stringBuffer.append("  private synchronized void swap(int i, int j) {" + "\n");
                    }else {
                        stringBuffer.append(temp + "\n");
                    }
                }
                br.close();
                PrintWriter pw = new PrintWriter(new FileWriter(file));
                pw.write(stringBuffer.toString());
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ChangeSequentialHeap change = new ChangeSequentialHeap();
        change.changeMethod();
    }
}
