package mr;


import java.util.*;

/**
 * mr3该蜕变关系的含义是：在原始序列串联k个相同的序列top结果仍然和原始序列的结果相同
 * 关于k值的说明：k值是在1-10之间任意取值
 * @author phantom
 * @date 20181026
 */
public class MR3 implements MetamorphicRelations {


    public MR3(){ }

    @Override
    public int[] sourceList(int[] mylist) { return mylist ; }



    public int[] followUpList(int[] mylist, int[] sourcetoplist) {
        Random random = new Random();
        //在1-10之间取值
        int k = random.nextInt(10) + 1 ;
        int[] templist = new int[mylist.length * k] ;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < mylist.length; j++) {
                int temp = j + (mylist.length * i) ;
                templist[temp] = mylist[j] ;
            }
        }
        return templist ;
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
