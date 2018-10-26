package mr;



import java.util.*;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR9 implements MetamorphicRelations {

    @Override
    public int[] sourceList(int[] mylist) {
        int[] sourcelist = mylist ;
        return mylist ;
    }



    public int[] followUpList(int[] mylist, int[] sourcetoplist) {
        Arrays.sort(sourcetoplist);
        //获得原始最优序列中的最大值
        int max = sourcetoplist[sourcetoplist.length-1] + 1;
        Random random = new Random();
        //获得串联序列的长度
        int length = random.nextInt(sourcetoplist.length) + 1;
        int[] templist = new int[length];
        for (int i = 0; i < length; i++) {
            templist[i] = random.nextInt(mylist.length -500) + 500;
        }
        int[] followlist = new int[mylist.length + length];
        System.arraycopy(mylist,0,followlist,0,mylist.length);
        System.arraycopy(templist,0,followlist,mylist.length,templist.length);
        return followlist;
    }


    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist 衍生最优序列
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        if (Arrays.equals(sourceToplist,followToplist)){
            return true;
        }else {
            return false;
        }
    }
}
