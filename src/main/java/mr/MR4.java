package mr;


import java.util.*;

/**
 * mr4蜕变关系：在原始序列的后面串联一个原始序列的子集，子集元素的个数分别为原始子集的1/10，3/10，5/10，7/10，10/10
 * @author phantom
 * @date 20181026
 */
public class MR4 implements MetamorphicRelations {
    @Override
    public int[] sourceList(int[] mylist) {
        return mylist;
    }


    public int[] followUpList(int[] mylist,int[] sourcetoplist){
        Random random = new Random();
        int[] sublist = new int[random.nextInt(mylist.length)+1] ;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < sublist.length; i++) {
            int temp = 0 ;
            temp = random.nextInt(mylist.length);
            while(list.contains(temp))
                temp = random.nextInt(mylist.length);
            list.add(temp);
            sublist[i] = mylist[temp] ;
        }
        int[] followlist = new int[mylist.length + sublist.length];
        System.arraycopy(mylist,0,followlist,0,mylist.length);
        System.arraycopy(sublist,0,followlist,mylist.length,sublist.length);

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
