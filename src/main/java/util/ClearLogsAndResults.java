package util;

import java.io.File;

import static java.io.File.separator;

/**
 * describe:
 *删除实验结果以及相应的测试报告
 * @author phantom
 * @date 2019/02/17
 */
public class ClearLogsAndResults {
    private String logPath;

    private String resultPath;

    private static final String[] NAMES = {"FineGrainedHeap", "SequentialHeap", "SimpleLinear",
            "SimpleTree", "SkipQueue"};


    public ClearLogsAndResults(){
        logPath = System.getProperty("user.dir") + separator + "log";
        resultPath = System.getProperty("user.dir") + separator + "result";
    }

    public void clear(){
        for (int i = 0; i < NAMES.length; i++) {
            String lpath = logPath + separator + NAMES[i];
            String rpath = resultPath + separator + NAMES[i];

            File lfile = new File(lpath);
            File rfile = new File(rpath);

            System.out.println("删除日志文件");
            if (lfile.listFiles().length != 0){
                for (int j = 0; j < lfile.listFiles().length; j++) {
                    lfile.listFiles()[j].delete();
                }
            }

            System.out.println("删除测试结果");
            if (rfile.listFiles().length != 0){
                for (int j = 0; j < rfile.listFiles().length; j++) {
                    rfile.listFiles()[j].delete();
                }
            }

        }
    }

    public static void main(String[] args) {
        ClearLogsAndResults clear = new ClearLogsAndResults();
        clear.clear();
    }





}
