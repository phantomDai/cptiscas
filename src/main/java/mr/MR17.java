package mr;

import bin.BinList;
import set.mutants.MutantSet;
import testdata.TestData;
import testprograms.TestProgram;
import util.logs.LogRecorder;
import util.logs.WrongReport;

import java.util.*;
import static mr.util.Constant.SEED;
/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR17 implements MetamorphicRelations {

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


    private boolean isConformToMR(int[] sourceToplist, int[] followToplist){
        if (Arrays.equals(sourceToplist,followToplist)){
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
                System.out.println("MR17: 开始测试" + objectName + "的" + mutantSet.getMutantID(i) + "; loop:" + loop + " numberOfThreads:" + numberOfThreads);

                //开始记录时间
                long startTime = System.currentTimeMillis();
                //随机产生1W个数据
                TestData testData = new TestData();
                int[] randomArray = testData.generateTestData(j);

                //获取原始测试数据
                int[] sourceArray = sourceList(randomArray);
                //对原始数据进行排序
                Arrays.sort(sourceArray);

                //执行原始测试数据并获取测试结果
                TestProgram testProgramForSource = new TestProgram();
                //根据本蜕变关系设置取出的数据的总个数
                testProgramForSource.setDefaultNumber(DEFAULTNUMBER,serviceName);
                int[] sourceTopArray = testProgramForSource.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),sourceArray);
//                System.out.println(Arrays.toString(sourceTopArray));

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

                //合并followUpArray1 和followUpArray2
                int[] followArray = new int[followTopArray1.length + followTopArray2.length];
                System.arraycopy(followTopArray1,0,followArray,0,followTopArray1.length);
                System.arraycopy(followTopArray2,0,followArray,followTopArray1.length,followTopArray2.length);

//                System.out.println(Arrays.toString(followArray));

                //执行followArray，并获取测试结果
                TestProgram testProgramForFollowArray = new TestProgram();
                int[] followTopArray = testProgramForFollowArray.executeServiceAndGetResult(index,numberOfThreads,serviceName,
                        mutantSet.getMutantFullName(i),followArray);

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
            logRecorder.write(index,loop,j,numberOfThreads,objectName,"MR17",
                    killedMutants,mutantSet.size(),time);
        }//j-循环次数
    }




    /**
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


    public static void main(String[] args) {
        MR17 mr = new MR17();
//        String[] names = {"SkipQueue"};
        String[] names = {"FineGrainedHeap"};

        for (int i = 0; i < names.length; i++) {
            mr.executeService(2,0,10,names[i]);
        }
        System.exit(0);
    }

}
