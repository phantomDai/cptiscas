package mr;



import bin.BinList;

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
public class MR16_2 implements MetamorphicRelations {

    @Override
    public int[] sourceList(int[] mylist) {
        int[] sourcelist = mylist ;
        return sourcelist;
    }


    public BinList[] followUpList(int[] mylist, int[] sourcetoplist){
        BinList[] binlists = new BinList[2] ;
        for (int i = 0; i < binlists.length; i++) {
            binlists[i] = new BinList();
        }
        Random random = new Random();
        int local = random.nextInt(724) + 200;

        for (int i = 0; i < mylist.length; i++) {
            if (i < local){
                binlists[0].put(mylist[i]);
            } else{
                binlists[1].put(mylist[i]);
            }
        }
        return binlists ;
    }


    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist1 衍生最优序列之一
     * @param followToplist2 衍生最优序列之一
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist1,int[] followToplist2){
        List<Integer> templist = new ArrayList<Integer>();
        List<Integer> follow1list = Arrays.stream(followToplist1).boxed().collect(Collectors.toList());
        for (int i = 0; i < followToplist2.length; i++) {
            if (follow1list.contains(followToplist2[i])){
                templist.add(followToplist2[i]);
            }
        }

        List<Integer> comparedList = Arrays.stream(sourceToplist).boxed().collect(Collectors.toList());
        //判断是否符合蜕变关系
        boolean flag = true ;
        for (int i = 0; i < templist.size(); i++) {
            if(!comparedList.contains(templist.get(i))){
                flag = false;
                break;
            }
        }

        if (flag){
            return true;
        }else {
            return false;
        }
    }
}
