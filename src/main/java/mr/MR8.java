package mr;



import java.util.*;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR8 implements MetamorphicRelations {

    private int local;
    private int[] addlist;

    @Override
    public int[] sourceList(int[] mylist) {
        int[] sourcelist = mylist ;
        return mylist ;
    }



    public int[] followUpList(int[] mylist, int[] sourcetoplist) {
        Random random = new Random();
        //产生串联子序列的长度1-10之间
        int length = random.nextInt(sourcetoplist.length) + 1;
        this.local = length;
        int[] sublist = new int[length];
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < length; i++) {
            while(true){
                int temp = random.nextInt(15);
                if (!list.contains(temp)){
                    list.add(temp);
                    sublist[i] = temp;
                    break;
                }
            }
        }
        addlist = sublist;
        int[] newlist = new int[mylist.length + length];
        System.arraycopy(mylist,0,newlist,0,mylist.length);
        System.arraycopy(sublist,0,newlist,mylist.length,sublist.length);
        return newlist;
    }


    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist 衍生最优序列
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        boolean flag = true ;
        Arrays.sort(addlist);
        if (addlist.length == 10){
            if (Arrays.equals(addlist,followToplist)){
                flag = true;
            } else{
                flag = false;
            }
        }else {
            //创建一个预期的数组
            int[] temp = new int[sourceToplist.length];
            //构造预期数组
            for (int i = 0; i < sourceToplist.length; i++) {
                if (i < addlist.length){
                    temp[i] = addlist[i];
                } else{
                    temp[i] = sourceToplist[i - addlist.length];
                }
            }
            Arrays.sort(temp);

            //将构造的数组与原始数组进行比较,若想等则为true
            if (Arrays.equals(temp,followToplist)){
                flag = true;
            } else{
                flag = false;
            }
        }
        if (flag){
            return true;
        }else {
            return false;
        }
    }

}
