package mr;


import set.mutants.MutantSet;
import testdata.TestData;
import testprograms.TestProgram;
import util.logs.LogRecorder;
import util.logs.WrongReport;

import java.util.*;

/**
 *
 * 为了顺利地在衍生测试数据中插入值，生成10000个偶数原始测试数据。
 * 不管最后的输出的结果是什么，该方式保证总能找到一个合适的y值
 * @author phantom
 * @date 20181026
 */
public class MR7 implements MetamorphicRelations {
    private int target_index;
    private int target ;

    @Override
    public int[] sourceList(int[] RandomArray) { return RandomArray; }



    public int[] followUpList(int[] sourceArray, int[] sourceTopArray) {
        int[] followArray = new int[sourceArray.length + 1] ;
        Random random = new Random();

        //产生1-8之间的数值
        this.target_index = random.nextInt(8) + 1;

        //产生y值
        this.target = sourceTopArray[target_index + 1] - 1;

        //将原始测试数据复制给衍生测试数据并添加上y
        System.arraycopy(sourceArray,0,followArray,0,sourceArray.length);
        followArray[sourceArray.length] = target;
        return followArray;
    }


    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist 衍生最优序列
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        boolean flag = true ;
        for (int i = 0; i < sourceToplist.length; i++) {
            if (i < target_index){
                if(sourceToplist[i] != followToplist[i]){
                    flag = false;
                    break;
                }
            }else if (i == target_index ){
                if (followToplist[i] != target){
                    flag = false;
                    break;
                }
            }else{
                if (followToplist[i] != sourceToplist[i-1]){
                    flag = true;
                    break;
                }
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
            List<Long> times = new ArrayList<>();
            for (int i = 0; i < mutantSet.size(); i++) {
//            for (int i = 0; i < 1; i++) {
                System.out.println("开始测试" + objectName + "的" + mutantSet.getMutantID(i));

                //开始记录时间
                long startTime = System.currentTimeMillis();
                //随机产生1W个数据
                TestData testData = new TestData();
                int[] randomArray = testData.generateTestDataForMR7(j);

                //获取原始测试数据
                int[] sourceArray = sourceList(randomArray);

                //执行原始测试数据并获取测试结果
                TestProgram testProgramForSource = new TestProgram();
                int[] sourceTopArray = testProgramForSource.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),sourceArray);

                //获取衍生测试数据
                int[] followUpArray = followUpList(sourceArray,sourceTopArray);

                //执行原始测试数据并获取测试结果
                TestProgram testProgramForFollowUp = new TestProgram();
                int[] followTopArray = testProgramForFollowUp.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),followUpArray);

                //验证原始数据和衍生数据的执行结果是否符合蜕变关系
                boolean flag = isConformToMR(sourceTopArray,followTopArray);

                //执行测试用例需要的时间：包括：生成测试数据，执行测试，验证结果
                long endtime = System.currentTimeMillis();
                times.add(endtime - startTime);
                //如果违反了蜕变关系，则添加到列表中，并记录执行结果
                if (!flag){
                    killedMutants.add(mutantSet.getMutantID(i));
                    wrongReport.writeLog(index,loop,j,numberOfThreads,objectName,
                            mutantSet.getMutantID(i),sourceTopArray,followTopArray);
                }
            }//i-遍历所有的变异体
            long time = 0;
            for (int i = 0; i < times.size(); i++) {
                time += times.get(i);
            }
            //清空记录时间的列表
            times.clear();
            //将本次执行的结果记录到XLS文件中
            logRecorder.write(index,loop,j,numberOfThreads,objectName,"MR7 ",
                    killedMutants,mutantSet.size(),time);
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
