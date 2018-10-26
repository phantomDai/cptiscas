package mr;

import bin.BinList;

import java.util.*;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR17 implements MetamorphicRelations {

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


    private boolean isConformToMR(int[] sourceToplist, int[] followToplist){
        if (Arrays.equals(sourceToplist,followToplist)){
            return true;
        }else {
            return false;
        }
    }
}
