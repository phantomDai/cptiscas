package mr;

/**
 *
 * @author phantom
 * @date 20181026
 */
public class MR19_1 implements MetamorphicRelations {

    @Override
    public int[] sourceList(int[] mylist) {
        return mylist;
    }

    public int[] followUpList(int[] mylist,int[] sourcetoplist){
        return mylist;
    }



    private boolean isConformToMR(int[] sourceToplist,int[] followToplist){
        boolean flag = true ;
        for (int i = 0; i < followToplist.length; i++) {
            if(followToplist[i] != sourceToplist[i]){
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
