package mr;

import bin.BinList;
import set.mutants.MutantSet;
import testdata.TestData;
import testprograms.TestProgram;
import util.logs.LogRecorder;
import util.logs.WrongReport;

import java.util.*;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR14 implements MetamorphicRelations {


    @Override
    public int[] sourceList(int[] mylist) {
        return mylist;
    }



    public BinList[] followUpList(int[] mylist, int[] sourcetoplist) {
        Random random = new Random();
        BinList[] binList = new BinList[2];
        for (int i = 0; i < binList.length; i++) {
            binList[i] = new BinList();
        }
        int local = random.nextInt(9000) + 500;
        for (int i = 0; i < local; i++) {
            binList[0].put(mylist[i]);
        }
        for (int i = 0; i < mylist.length - local; i++) {
            binList[1].put(mylist[i+local]);
        }
        return binList;
    }





    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist1 衍生最优序列之一
     * @param followToplist2 衍生最优序列之一
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist1,int[] followToplist2){

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < followToplist1.length; i++) {
            list.add(followToplist1[i]);
        }
        for (int i = 0; i < followToplist2.length; i++) {
            if(!list.contains(followToplist2[i])){
                list.add(followToplist2[i]);
            }
        }
        boolean flag = true ;
        for (int i = 0; i < sourceToplist.length; i++) {
            if(!list.contains(sourceToplist[i])){
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
            List<Long> times = new ArrayList<>();

            for (int i = 0; i < mutantSet.size(); i++) {
//            for (int i = 0; i < 1; i++) {
                System.out.println("开始测试" + objectName + "的" + mutantSet.getMutantID(i));

                //开始记录时间
                long startTime = System.currentTimeMillis();
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
                BinList[] followUpArrays = followUpList(sourceArray,sourceTopArray);
                int[] followUpArray1 = new int[followUpArrays[0].list.size()];
                int[] followUpArray2 = new int[followUpArrays[1].list.size()];
                for (int k = 0; k < followUpArray1.length; k++) {
                    followUpArray1[k] = followUpArrays[0].list.get(k);
                }

                for (int k = 0; k < followUpArray2.length; k++) {
                    followUpArray2[k] = followUpArrays[1].list.get(k);
                }


                //执行衍生测试数据并获取测试结果
                TestProgram testProgramForFollowUp1 = new TestProgram();
                int[] followTopArray1 = testProgramForFollowUp1.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),followUpArray1);

                TestProgram testProgramForFollowUp2 = new TestProgram();
                int[] followTopArray2 = testProgramForFollowUp2.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),followUpArray2);

                //验证原始数据和衍生数据的执行结果是否符合蜕变关系
                boolean flag = isConformToMR(sourceTopArray,followTopArray1, followTopArray2);

                //执行测试用例需要的时间：包括：生成测试数据，执行测试，验证结果
                long endtime = System.currentTimeMillis();
                times.add(endtime - startTime);
                //如果违反了蜕变关系，则添加到列表中，并记录执行结果
                if (!flag){
                    List<Integer> tempList = new ArrayList<>();
                    for (int k = 0; k < followTopArray1.length; k++) {
                        if (!tempList.contains(followTopArray1[k])){
                            tempList.add(followTopArray1[k]);
                        }
                    }
                    for (int k = 0; k < followTopArray2.length; k++) {
                        if (!tempList.contains(followTopArray2[k])){
                            tempList.add(followTopArray2[k]);
                        }
                    }

                    int[] followTopArray = new int[tempList.size()];
                    for (int k = 0; k < tempList.size(); k++) {
                        followTopArray[k] = tempList.get(k);
                    }

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
            logRecorder.write(index,loop,j,numberOfThreads,objectName,"MR14",
                    killedMutants,mutantSet.size(),time);
        }//j-循环次数
    }

    /**
     * 默认的循环次数
     */
    private static final int SEED = 1;


    /**
     * 服务类的前缀
     */
    private static final String PREFIXPATHOFSERVICE = "service." ;


    /**
     * 每一个实验对象需要测试的变异体名字
     */
    private static final String[] MUTANTSNAME = {"sequentialAndsequential", "sequentialAndconcurrent",
            "concurrentAndsequential", "concurrentAndconcurrent"} ;

    public static void main(String[] args) {
        MR14 mr = new MR14();
        String[] names = {"FineGrainedHeap","SkipQueue"};

        for (int i = 0; i < names.length; i++) {
            mr.executeService(3,0,5,names[i]);
        }

    }

}
