package mr;



import set.mutants.MutantSet;
import testdata.TestData;
import testprograms.TestProgram;
import util.logs.*;

import java.util.*;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR8 implements MetamorphicRelations {

    private int local;
    private int[] addlist;

    @Override
    public int[] sourceList(int[] mylist) {
        int[] sourcelist = mylist ;
        return mylist ;
    }



    public int[] followUpList(int[] mylist, int[] sourcetoplist) {
        Random random = new Random();
        //产生串联子序列的长度1-10之间
        int length = random.nextInt(sourcetoplist.length) + 1;
        this.local = length;
        int[] sublist = new int[length];
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < length; i++) {
            while(true){
                int temp = random.nextInt(15);
                if (!list.contains(temp)){
                    list.add(temp);
                    sublist[i] = temp;
                    break;
                }
            }
        }
        addlist = sublist;
        int[] newlist = new int[mylist.length + length];
        System.arraycopy(mylist,0,newlist,0,mylist.length);
        System.arraycopy(sublist,0,newlist,mylist.length,sublist.length);
        return newlist;
    }


    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist 衍生最优序列
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        boolean flag = true ;
        Arrays.sort(addlist);
        if (addlist.length == 10){
            if (Arrays.equals(addlist,followToplist)){
                flag = true;
            } else{
                flag = false;
            }
        }else {
            //创建一个预期的数组
            int[] temp = new int[sourceToplist.length];
            //构造预期数组
            for (int i = 0; i < sourceToplist.length; i++) {
                if (i < addlist.length){
                    temp[i] = addlist[i];
                } else{
                    temp[i] = sourceToplist[i - addlist.length];
                }
            }
            Arrays.sort(temp);

            //将构造的数组与原始数组进行比较,若想等则为true
            if (Arrays.equals(temp,followToplist)){
                flag = true;
            } else{
                flag = false;
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
                int[] randomArray = testData.generateTestDataForMR8(j);

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

                //如果违反了蜕变关系，则添加到列表中，并记录执行结果
                if (!flag){
                    killedMutants.add(mutantSet.getMutantID(i));
                    wrongReport.writeLog(index,loop,j,numberOfThreads,objectName,
                            mutantSet.getMutantID(i),sourceTopArray,followTopArray);
                }
            }//i-遍历所有的变异体
            long endtime = System.currentTimeMillis();
            //将本次执行的结果记录到XLS文件中
            logRecorder.write(index,loop,j,numberOfThreads,objectName,"MR8",
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
