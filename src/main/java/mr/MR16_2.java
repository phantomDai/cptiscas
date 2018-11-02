package mr;



import bin.BinList;
import set.mutants.MutantSet;
import testdata.TestData;
import testprograms.TestProgram;
import util.logs.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *m的取值范围在11-19之间
 * @author phantom
 * @date 20181026
 */
public class MR16_2 implements MetamorphicRelations {

    @Override
    public int[] sourceList(int[] mylist) {
        int[] sourcelist = mylist ;
        return sourcelist;
    }


    public BinList[] followUpList(int[] mylist, int[] sourcetoplist){
        BinList[] binlists = new BinList[2] ;
        for (int i = 0; i < binlists.length; i++) {
            binlists[i] = new BinList();
        }
        Random random = new Random();
        int local = random.nextInt(9000) + 500;

        for (int i = 0; i < mylist.length; i++) {
            if (i < local){
                binlists[0].put(mylist[i]);
            } else{
                binlists[1].put(mylist[i]);
            }
        }
        return binlists ;
    }


    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist1 衍生最优序列之一
     * @param followToplist2 衍生最优序列之一
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist1,int[] followToplist2){
        List<Integer> templist = new ArrayList<Integer>();
        List<Integer> follow1list = Arrays.stream(followToplist1).boxed().collect(Collectors.toList());
        for (int i = 0; i < followToplist2.length; i++) {
            if (follow1list.contains(followToplist2[i])){
                templist.add(followToplist2[i]);
            }
        }

        List<Integer> comparedList = Arrays.stream(sourceToplist).boxed().collect(Collectors.toList());
        //判断是否符合蜕变关系
        boolean flag = true ;
        for (int i = 0; i < templist.size(); i++) {
            if(!comparedList.contains(templist.get(i))){
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

                //产生v2序列要取出的数据的个数
                Random random = new Random();
                int v2number = random.nextInt(9) + 11;

                //获取原始测试数据
                int[] sourceArray = sourceList(randomArray);

                //执行原始测试数据并获取测试结果
                TestProgram testProgramForSource = new TestProgram();
                //根据本蜕变关系设置取出的数据的总个数
                testProgramForSource.setDefaultNumber((DEFAULTNUMBER + v2number),serviceName);
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
                testProgramForFollowUp2.setDefaultNumber(v2number,serviceName);
                int[] followTopArray2 = testProgramForFollowUp2.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),followUpArray2);

                //验证原始数据和衍生数据的执行结果是否符合蜕变关系
                boolean flag = isConformToMR(sourceTopArray,followTopArray1, followTopArray2);

                //如果违反了蜕变关系，则添加到列表中，并记录执行结果
                if (!flag){
                    List<Integer> tempList = new ArrayList<>();
                    List<Integer> resultList = new ArrayList<>();
                    for (int k = 0; k < followTopArray1.length; k++) {
                        tempList.add(followTopArray1[k]);
                    }
                    for (int k = 0; k < followTopArray2.length; k++) {
                        if (tempList.contains(followTopArray2[k])){
                            resultList.add(followTopArray2[k]);
                        }
                    }

                    int[] followTopArray = new int[resultList.size()];
                    for (int k = 0; k < resultList.size(); k++) {
                        followTopArray[k] = resultList.get(k);
                    }

                    killedMutants.add(mutantSet.getMutantID(i));
                    wrongReport.writeLog(index,loop,j,numberOfThreads,objectName,
                            mutantSet.getMutantID(i),sourceTopArray,followTopArray);
                }
            }//i-遍历所有的变异体
            long endtime = System.currentTimeMillis();
            //将本次执行的结果记录到XLS文件中
            logRecorder.write(index,loop,j,numberOfThreads,objectName,"MR16_2",
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


    /**
     * 默认取出的数据的个数
     */
    private static final int DEFAULTNUMBER = 10 ;
}
