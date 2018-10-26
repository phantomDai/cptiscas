package mr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
        int sublength = random.nextInt(mylist.length - 9) + 10;
        int[] x = new int[sublength];
        for (int i = 0; i < x.length; i++) {
            x[i] = random.nextInt(NUMBERRANGE);
        }
        this.tempx = x ;
        List<Integer> tempsourcelist = new ArrayList<Integer>();
        for (int i = 0; i < mylist.length; i++) {
            tempsourcelist.add(mylist[i]);
        }
        //将数组转化为list
        List<Integer> tempxlist = Arrays.stream(x).boxed().collect(Collectors.toList());
        for (int i = 0; i < tempxlist.size(); i++) {
            int temp = tempxlist.get(i);
            for (int j = 0; j < tempsourcelist.size(); j++) {
                if (tempsourcelist.get(j) == temp){
                    tempsourcelist.remove(j);
                    break;
                }
            }
        }
        int[] returnlist = new int[tempsourcelist.size()];
        for (int i = 0; i < tempsourcelist.size(); i++) {
            returnlist[i] = tempsourcelist.get(i);
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
}
