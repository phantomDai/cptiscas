package mr;

import bin.BinList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int local = random.nextInt(724) + 200;
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
}
