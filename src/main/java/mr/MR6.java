package mr;



import java.util.*;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR6 implements MetamorphicRelations {
    @Override
    public int[] sourceList(int[] mylist) { return mylist ; }


    public int[] followUpList(int[] mylist,int[] toplist) {
        Random random = new Random();
        int k = random.nextInt(toplist.length) + 1;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < mylist.length; i++) {
            list.add(mylist[i]);
        }

        for (int i = 0; i < k; i++) {
            List<Integer> templist = new ArrayList<Integer>();
            templist.clear();
            int templength = random.nextInt(toplist.length - 1);
            while(templength == 0){
                templength = random.nextInt(toplist.length - 1);
            }
            for (int j = 0; j < templength; j++) {
                int tempitem = random.nextInt(toplist.length);
                while (templist.contains(toplist[tempitem])) {
                    tempitem = random.nextInt(toplist.length);
                }
                templist.add(toplist[tempitem]);
                list.add(toplist[tempitem]);
            }
        }
        int[] followlist = new int[list.size()] ;
        for (int i = 0; i < list.size(); i++) {
            followlist[i] = list.get(i);
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
        if (Arrays.equals(sourceToplist,followToplist)){
            return true;
        }else {
            return false;
        }
    }

}
