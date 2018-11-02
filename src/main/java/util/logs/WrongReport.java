package util.logs;


import java.io.*;
import static java.io.File.separator;

/**
 *description:当原始测试数据与衍生测试数据的执行结果违反了蜕变关系时，
 * 记录执行的信息，以便后期查看。
 *
 * @author phantom
 * @date 20181025
 */
public class WrongReport {

    /**
     * 记录违反蜕变关系的原始测试数据的输出和衍生测试数据的输出
     *
     * @param index           测试场景
     * @param loop            循环的次数
     * @param seed            随机数的种子
     * @param numberOfThreads 开启的线程数目
     * @param objectName      测试的对象名字
     * @param mutantID        变异体的ID
     * @param sourceTopArray   原始测试数据的执行结果
     * @param followTopArray  衍生测试数据的执行性结果
     */
    public void writeLog(int index, int loop, int seed, int numberOfThreads,
                         String objectName, String mutantID,
                         int[] sourceTopArray, int[] followTopArray) {
        String fileName = objectName + "index@" + String.valueOf(index) + "loop@" + String.valueOf(loop)
                + "numOfThreads@" + String.valueOf(numberOfThreads);

        String filePath = System.getProperty("user.dir") + separator + "log" +
                separator + objectName + separator + fileName;

        //获得文件的对象
        File file = creatNewFile(filePath);

        //获得要写入的信息
        String info = getInfo(sourceTopArray,followTopArray,seed,mutantID);

        //向文件中写入数据
        writeInfo(file, info);
    }


    /**
     * 向文件中写入数据
     * @param file 要写入的文件的对象
     * @param info  要写入的信息
     */
    private void writeInfo(File file, String info) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file,true));
            pw.write(info + "\r");
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件的路径创建文件
     * @param filePath 文件的路径
     * @return 要操作的文件的对象
     */
    private File creatNewFile(String filePath){
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 整合所有的执行信息
     * @param sourceTopArray 原始数据的执行结果
     * @param followTopArray 衍生数据的执行结果
     * @param seed 随机数种子
     * @param mutnatID 变异体的ID
     * @return 返回记录的信息
     */
    private String getInfo(int[] sourceTopArray, int[] followTopArray, int seed, String mutnatID){
        String info = mutnatID + "在随机数种子为：" + String.valueOf(seed) +
                "的情况下，" + "sourceTopArray:";
        for (int i = 0; i < sourceTopArray.length; i++) {
            info += String.valueOf(sourceTopArray[i] + ", ");
        }
        info += "    ;followTopArray:";
        for (int i = 0; i < followTopArray.length; i++) {
            info += String.valueOf(followTopArray[i] + ", ");
        }
        return info;
    }
}
