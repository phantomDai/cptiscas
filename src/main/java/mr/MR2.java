package mr;


import java.util.*;

/**
 * 蜕变关系MR2，该蜕变关系旨在原始序列的前面和后面串联一个序列观
 * @author phantom
 * @date 20181026
 */
public class MR2 implements MetamorphicRelations {

    @Override
    public int[] sourceList(int[] mylist) { return mylist; }



    public int[] followUpList(int[] mylist, int[] sourcetoplist) {

        Random random = new Random();
        int site = random.nextInt(mylist.length);
        int[] w_list = new int[site];
        int[] v_list = new int[mylist.length - site];
        for (int i = 0; i < site; i++) {
            w_list[i] = mylist[i];
        }
        for (int i = site; i < mylist.length; i++) {
            v_list[i-site] = mylist[i];
        }
        int[] newlist = new int[mylist.length];
        System.arraycopy(v_list,0,newlist,0,v_list.length);
        System.arraycopy(w_list,0,newlist,v_list.length,w_list.length);
        return newlist;
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
