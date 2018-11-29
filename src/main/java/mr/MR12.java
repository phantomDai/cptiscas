package mr;

import set.mutants.MutantSet;
import testdata.TestData;
import testprograms.TestProgram;
import util.logs.LogRecorder;
import util.logs.WrongReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import static mr.util.Constant.SEED;
/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR12 implements MetamorphicRelations{

    private int[] tempx;
    private static int NUMBERRANGE = 9000 ;

    private int[] sourcetoplist;

    @Override
    public int[] sourceList(int[] mylist) {
        return mylist;
    }


    public int[] followUpList(int[] mylist, int[] sourcetoplist) {
        this.sourcetoplist = sourcetoplist;
        Random random = new Random();
        //随机一个x序列的长度在10-10000之间
        int k = random.nextInt(mylist.length) / 2 ;
        int[] x = new int[k];
        for (int i = 0; i < x.length; i++) {
            x[i] = random.nextInt(NUMBERRANGE);
//            x[i] = random.nextInt(20);
        }
//
//        for (int i = 0; i < x.length; i++) {
//            System.out.print(x[i] + ", ");
//        }
//        System.out.println();



        this.tempx = x ;
        List<Integer> vlist = new ArrayList<Integer>();
        for (int i = 0; i < mylist.length; i++) {
            vlist.add(mylist[i]);
        }
        //将数组转化为list
        List<Integer> tempxlist = Arrays.stream(x).boxed().collect(Collectors.toList());
        for (int i = 0; i < tempxlist.size(); i++) {
            if (vlist.contains(tempxlist.get(i))){
                vlist.remove(tempxlist.get(i));
            }
        }

        int[] returnlist = new int[vlist.size()];
        for (int i = 0; i < vlist.size(); i++) {
            returnlist[i] = vlist.get(i);
        }

        //得到tempx
        //首先将sourceTopArray转化为List
        List<Integer> sourceArray = new ArrayList<>();
        List<Integer> xlist = new ArrayList<>();
        for (int i = 0; i < sourcetoplist.length; i++) {
            sourceArray.add(sourcetoplist[i]);
        }

        for (int i = 0; i < sourceArray.size(); i++) {
            if (tempxlist.contains(sourceArray.get(i))){
                xlist.add(sourceArray.get(i));
            }
        }

        if (xlist.size() != 0){
            tempx = new int[xlist.size()];
            for (int i = 0; i < xlist.size(); i++) {
                tempx[i] = xlist.get(i);
            }
        }

        return returnlist;
    }



    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist 衍生最优序列
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){

        List<Integer> tempxlist = Arrays.stream(tempx).boxed().collect(Collectors.toList());
        List<Integer> xliststar = new ArrayList<Integer>();
        for (int i = 0; i < sourceToplist.length; i++) {
            if (tempxlist.contains(sourceToplist[i])){
                xliststar.add(sourceToplist[i]);
            }
        }

        boolean flag = true ;

        if (xliststar.size() == 0){
            if (Arrays.equals(sourceToplist,followToplist)){
                flag = true ;
            } else{
                flag = false ;
            }
        }else {
            int[] temp = new int[followToplist.length + xliststar.size()];
            for (int i = 0; i < temp.length; i++) {
                if (i < xliststar.size()){
                    temp[i] = xliststar.get(i);
                }else {
                    temp[i] = followToplist[i-xliststar.size()];
                }
            }
            Arrays.sort(temp);

            List<Integer> templist = Arrays.stream(temp).boxed().collect(Collectors.toList());
            for (int i = 0; i < sourceToplist.length; i++) {
                if (!templist.contains(sourceToplist[i])){
                    flag = false ;
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
                int[] randomArray = testData.generateTestData(j);

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
            logRecorder.write(index,loop,j,numberOfThreads,objectName,"MR12",
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
        MR12 mr = new MR12();
//        int[] data = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
//        int[] sourcetop = {1,2,3,4,5,6,7,8,9,10};
//
//        int[] follow = mr.followUpList(data,sourcetop);
//        for (int i = 0; i < follow.length; i++) {
//            System.out.print(follow[i] + ", ");
//        }
//        System.out.println();
//        for (int i = 0; i < mr.tempx.length; i++) {
//            System.out.print(mr.tempx[i] + ",");
//        }



        //        String[] names = {"SimpleLinear","SimpleTree","SequentialHeap","FineGrainedHeap","SkipQueue"};
//        String[] names = {"FineGrainedHeap","SkipQueue"};
//        String[] names = {"SimpleLinear"};
        String[] names = {"SimpleTree"};
//        String[] names = {"SkipQueue"};

        for (int i = 0; i < names.length; i++) {
            mr.executeService(3,0,5,names[i]);
        }

    }


}
