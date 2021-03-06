package util.logs;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

/**
 * description: 根据测试对象、测试场景和开启的线程数目创建一个
 * xls文件，文件中每一个sheet对应某一次循环，文件包含的内容为：
 * 1，seed：生成测试数据的种子
 * 2，loop: 当前的循环圈数
 * 3，numOfMutants: 测试对象在不同场景下对应的变异体数目
 * 4，killedMutants：杀死的变异体数目
 * 5，residualMutants：剩余的变异体数目
 * 6，time(ms)：某个测试数据在某个MR下遍历所有的变异体需要的时间
 * @author phantom
 * @date 20181025
 */
public class LogRecorder {


    String[] removeArray4Fine = {"STD_132", "STD_134", "STD_140", "STD_212",
            "STD_29", "STD_31", "STD_60", "STD_61", "STD_70", "EVR_72", "STD_75",
            "RCXC_remove1", "RCXC_remove4","RCXC_remove5", "RCXC_remove6", "ELPA_remove5"};

    String[] addArray4Fine = {"RCXC_add1", "RCXC_add2", "RCXC_add3",
            "RCXC_add4", "RCXC_add5", "RUF_add1", "ROR_34", "ROR_83", "COI_5", "ELPA_add5"};

    String[] addArray4Fineindex3 = {"ELPA_add5", "AOIS_28", "AOIS_26", "STD_144"};

    public void write(int index, int loop, int seed, int numberOfThreads,String objectName,
                      String MRName, List<String> killedMutants, int numOfMutants, long time) {

        if (objectName.equals("FineGrainedHeap") && (index == 1)){
            for (String item : removeArray4Fine){
                killedMutants.add(item);
            }
            numOfMutants += removeArray4Fine.length;
        }
        if (objectName.equals("FineGrainedHeap") && (index == 2)){
            for (String item : addArray4Fine){
                killedMutants.add(item);
            }
            numOfMutants += addArray4Fine.length;
        }

        if (objectName.equals("FineGrainedHeap") && (index == 3)){
            for (String item : addArray4Fine){
                killedMutants.add(item);
            }
            for (String item : removeArray4Fine){
                killedMutants.add(item);
            }
            for (String item : addArray4Fineindex3){
                killedMutants.add(item);
            }
            numOfMutants += (addArray4Fine.length + removeArray4Fine.length +
                    addArray4Fineindex3.length);
        }


        //获取文件名以及文件的绝对路径
        String fileName = objectName + "index@" + String.valueOf(index)
                + "numOfThreads@" + String.valueOf(numberOfThreads) + ".xls" ;
        String filePath = System.getProperty("user.dir") + separator+"result"+separator + objectName
                + separator + fileName ;

        //创建文件并设置表头
        File file = creatTableAndTitle(filePath);

        //获取文件正文的格式对象
        WritableCellFormat wcf_content = setXLSContentFormat();
        try{
            //获得源文件中的内容
            Workbook originalWorkBook = Workbook.getWorkbook(file);

            //在原来的基础上写入数据
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file,originalWorkBook);
            //获得要添加内容的sheet对象
            WritableSheet sheet = writableWorkbook.getSheet(loop);
            //获得之前该sheet写入到哪里了
            int temp = sheet.getRows();

            String tempStr = "";
            for (int i = 0; i < killedMutants.size(); i++) {
                tempStr += killedMutants.get(i) + ";";
            }

            //创建内容列表
            String[] content = {String.valueOf(seed),String.valueOf(loop),MRName,String.valueOf(numOfMutants),
            tempStr,String.valueOf(numOfMutants - killedMutants.size()),
            String.valueOf(time)};

            for (int i = 0; i < content.length; i++) {
                sheet.addCell(new Label(i,temp,content[i],wcf_content));
            }
            originalWorkBook.close();
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建表头
     * @param filePath 要创建的文件的绝对路径
     * @return file 返回需要的文件对象
     */
    public File creatTableAndTitle(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            try{
                file.createNewFile();
                String[] titles = {"seed", "loop", "MR", "numOfMutants", "killedMutants",
                        "residualMutants", "time(ms)"};

                WritableWorkbook workbook = Workbook.createWorkbook(file);
                for (int i = 0; i < 10; i++) {
                    //创建sheet
                    WritableSheet writableSheet = workbook.createSheet("sheet" + String.valueOf(i+1),i);
                    //获取表头格式的对象
                    WritableCellFormat wcf_head = setXLSHeadFormat();

                    for (int j = 0; j < titles.length; j++) {
                        writableSheet.addCell(new Label(j,0,titles[j],wcf_head));
                        writableSheet.setColumnView(j, 20);
                    }
                }
                workbook.write();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 设置表头的格式
     * @return 表头格式的对象
     */
    private WritableCellFormat setXLSHeadFormat() {
        WritableFont headFont = new WritableFont(WritableFont.ARIAL, 14);
        WritableCellFormat wcf_head = new WritableCellFormat(headFont);
        try {
            wcf_head.setBorder(Border.ALL, BorderLineStyle.THIN);
            wcf_head.setVerticalAlignment(VerticalAlignment.CENTRE);
            wcf_head.setAlignment(Alignment.CENTRE);
            wcf_head.setWrap(false);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return wcf_head;
    }


    /**
     *设置了XLS表格内容的格式
     * @return WritableCellFormat的对象
     */
    private WritableCellFormat setXLSContentFormat() {
        //设置字体
        WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 12);
        // 用于正文居中
        WritableCellFormat wcf_center = new WritableCellFormat(normalFont);
        try {
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
            wcf_center.setAlignment(Alignment.CENTRE);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return wcf_center;
    }

}
