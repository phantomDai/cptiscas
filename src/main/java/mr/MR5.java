package mr;

import java.util.*;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR5 implements MetamorphicRelations {

    public MR5(){ }

    @Override
    public int[] sourceList(int[] mylist) { return mylist; }



    public int[] followUpList(int[] mylist, int[] toplist) {
        int[] followlist = new int[mylist.length + 1] ;
        //在toplist中随机选取一个数
        Random random = new Random();
        int next = random.nextInt(toplist.length);
        int inserteditem = toplist[next] ;
        for (int i = 0; i < followlist.length; i++) {
            if (i < mylist.length) {
                followlist[i] = mylist[i];
            } else{
                followlist[i] = inserteditem ;
            }
        }
        return followlist ;
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
