package mr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR11_3 implements MetamorphicRelations {
    private static List<String> killedMutans ;
    public MR11_3() {
        killedMutans = new ArrayList<String>();
    }

    @Override
    public int[] sourceList(int[] mylist) {
        return mylist;
    }


    public int[] followUpList(int[] mylist,int[] sourcetoplist) {
        MR1 mr1 = new MR1();
        int[] newlist = mr1.followUpList(sourcetoplist,sourcetoplist);
        int[] followlist = new int[mylist.length + sourcetoplist.length];
        System.arraycopy(newlist,0,followlist,0,newlist.length);
        System.arraycopy(mylist,0,followlist,newlist.length,mylist.length);
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
