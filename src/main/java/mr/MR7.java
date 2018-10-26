package mr;


import java.util.*;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR7 implements MetamorphicRelations {
    private static int target_index;
    private static int target ;




    @Override
    public int[] sourceList(int[] mylist) { return mylist; }



    public int[] followUpList(int[] mylist, int[] toplist) {
        int[] followlist = new int[mylist.length + 1] ;
        boolean flag = false ;
        for (int i = 1; i < toplist.length; i++) {
            int max = toplist[i];
            int min = toplist[i-1];
            if ((max - min) != 1) {
                flag = true;
                break ;
            }
        }
        if (!flag){
            System.out.println("top序列不符合要求没有办法插入！");
            return null ;
        }else{
            Random random = new Random();
            int additem = 0 ;
            while(true){
                int temp = random.nextInt(toplist.length-1) + 1;
                if((toplist[temp] - toplist[temp - 1]) != 1){
                    additem = toplist[temp] - 1;
                    this.target_index = temp ;
                    this.target = additem ;
                    break ;
                }
            }
            for (int i = 0; i < followlist.length; i++) {
                if (i < mylist.length)
                    followlist[i] = mylist[i] ;
                else
                    followlist[i] = additem ;
            }
            return followlist;
        }
    }


    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist 衍生最优序列
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        boolean flag = true ;
        for (int i = 0; i < sourceToplist.length; i++) {
            if (i < target_index){
                if(sourceToplist[i] != followToplist[i]){
                    flag = false;
                    break;
                }
            }else if (i == target_index ){
                if (followToplist[i] != target){
                    flag = false;
                    break;
                }
            }else{
                if (followToplist[i] != sourceToplist[i-1]){
                    flag = true;
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
}
