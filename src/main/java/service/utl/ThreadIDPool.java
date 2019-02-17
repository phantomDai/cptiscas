package service.utl;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/02/17
 */
public class ThreadIDPool {
    private List<String> threadIDs;

    public ThreadIDPool(){
        threadIDs = new ArrayList<>();
    }

    public void addID(String threadID){
        threadIDs.add(threadID);
    }

    public String getID(int index){
        return threadIDs.get(index);
    }

    public int getLength(){
        return threadIDs.size();
    }

    public boolean contain(String ID){
        boolean flag = threadIDs.contains(ID);
        return flag;
    }

    public List<String> getThreadIDs(){
        return threadIDs;
    }
}
