package mr;

import set.mutants.MutantSet;
import testdata.TestData;
import testprograms.TestProgram;
import util.log.LogRecorder;
import util.log.WrongReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR19_1 implements MetamorphicRelations {

    @Override
    public int[] sourceList(int[] mylist) {
        return mylist;
    }

    public int[] followUpList(int[] mylist,int[] sourcetoplist){
        return mylist;
    }



    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        boolean flag = true ;
        for (int i = 0; i < followToplist.length; i++) {
            if(followToplist[i] != sourceToplist[i]){
                flag = false;
                break;
            }
        }

        if (flag){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 控制执行service的类
     * @param index 测试的场景：0，顺顺；1顺并；2，并顺；3并并
     * @param numberOfThreads 开启的线程数目
     * @param objectName 要测试的待测程序的名字
     */
    public void executeService(int index, int loop, int numberOfThreads, String objectName){
        String serviceName = PREFIXPATHOFSERVICE + "Test" + objectName;

        //获取变异体集合
        MutantSet mutantSet = new MutantSet(objectName,MUTANTSNAME[index]);

        //获取写入违反蜕变关系的结果
        WrongReport wrongReport = new WrongReport();

        //获取写入每次执行结果的对象
        LogRecorder logRecorder = new LogRecorder();

        for (int j = 0; j < SEED; j++) {
            //记录杀死的变异体的ID
            List<String> killedMutants = new ArrayList<>();
            //开始记录时间
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < mutantSet.size(); i++) {
//            for (int i = 0; i < 1; i++) {
                System.out.println("开始测试" + objectName + "的" + mutantSet.getMutantID(i));

                //随机产生1W个数据
                TestData testData = new TestData();
                int[] randomArray = testData.generateTestData(j);

                //获取原始测试数据
                int[] sourceArray = sourceList(randomArray);



                //执行原始测试数据并获取测试结果
                TestProgram testProgramForSource = new TestProgram();
                int[] sourceTopArray = testProgramForSource.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),sourceArray);

                //获取衍生测试数据
                int[] followUpArray = followUpList(sourceArray,sourceTopArray);

                //获取m的值
                int m = 9;


                //执行衍生测试数据并获取测试结果
                TestProgram testProgramForFollowUp = new TestProgram();
                testProgramForFollowUp.setDefaultNumber(m,serviceName);
                int[] followTopArray = testProgramForFollowUp.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),followUpArray);

                //验证原始数据和衍生数据的执行结果是否符合蜕变关系
                boolean flag = isConformToMR(sourceTopArray,followTopArray);

                //如果违反了蜕变关系，则添加到列表中，并记录执行结果
                if (!flag){
                    killedMutants.add(mutantSet.getMutantID(i));
                    wrongReport.writeLog(index,loop,j,numberOfThreads,objectName,
                            mutantSet.getMutantID(i),sourceTopArray,followTopArray);
                }
            }//i-遍历所有的变异体
            long endtime = System.currentTimeMillis();
            //将本次执行的结果记录到XLS文件中
            logRecorder.write(index,loop,j,numberOfThreads,objectName,"MR19_1",
                    killedMutants,mutantSet.size(),endtime - startTime);
        }//j-循环次数
    }

    /**
     * 默认的循环次数
     */
    private static final int SEED = 10;


    /**
     * 服务类的前缀
     */
    private static final String PREFIXPATHOFSERVICE = "service." ;


    /**
     * 每一个实验对象需要测试的变异体名字
     */
    private static final String[] MUTANTSNAME = {"sequentialAndsequential", "sequentialAndconcurrent",
            "concurrentAndsequential", "concurrentAndconcurrent"} ;


}
