package mr;

import java.util.Arrays;


/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR13 implements MetamorphicRelations {

    @Override
    public int[] sourceList(int[] mylist) {
        int[] sourcelist = mylist ;
        return sourcelist;
    }



    public int[] followUpList(int[] mylist,int[] sourcetoplist) {
        int[] followlist = mylist ;
        for (int i = 0; i < followlist.length; i++) {
            followlist[i] += 1 ;
        }
        return followlist;
    }



    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist 衍生最优序列
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        for (int i = 0; i < sourceToplist.length; i++) {
            sourceToplist[i] += 1;
        }
        if (Arrays.equals(sourceToplist,followToplist)){
            return true;
        }else {
            return false;
        }
    }
}
