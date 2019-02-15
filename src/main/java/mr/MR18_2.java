package mr;

import set.mutants.MutantSet;
import testdata.TestData;
import testprograms.TestProgram;
import util.logs.LogRecorder;
import util.logs.WrongReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static mr.util.Constant.SEED;
/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR18_2 implements MetamorphicRelations {

    @Override
    public int[] sourceList(int[] mylist) {
        return mylist;
    }

    public int[] followUpList(int[] mylist,int[] sourcetoplist){
        return mylist;
    }


    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        boolean flag = true ;
        for (int i = 0; i < sourceToplist.length; i++) {
            if(sourceToplist[i] != followToplist[i]){
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
                System.out.println("MR18_2: 开始测试" + objectName + "的" + mutantSet.getMutantID(i) + "; loop:" + loop + " numberOfThreads:" + numberOfThreads);

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
                int[] followUpArray = followUpList(sourceArray,sourceTopArray);

                Random random = new Random();
                //获取m的值
                int m = random.nextInt(9) + 12 ;


                //执行衍生测试数据并获取测试结果
                TestProgram testProgramForFollowUp = new TestProgram();
                testProgramForFollowUp.setDefaultNumber(m,serviceName);
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
            logRecorder.write(index,loop,j,numberOfThreads,objectName,"MR18_2",
                    killedMutants,mutantSet.size(),time);
        }//j-循环次数
    }




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
        MR18_2 mr = new MR18_2();
        //        String[] names = {"SimpleLinear","SimpleTree","SequentialHeap","FineGrainedHeap","SkipQueue"};
//        String[] names = {"FineGrainedHeap","SkipQueue"};
//        String[] names = {"SimpleLinear"};
//        String[] names = {"SimpleTree"};
        String[] names = {"SkipQueue"};

        for (int i = 0; i < names.length; i++) {
            mr.executeService(3,0,5,names[i]);
        }
        System.exit(0);

    }



}
