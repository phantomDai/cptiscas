package bin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phantom
 * @Description 某些蜕变关系的衍生测试用例会将序列裁成两部分，该数据结构将存储这样的数据
 * @date 2018/10/24/024
 */
public class BinList {
    public List<Integer> list ;

    public BinList(){
        list = new ArrayList<Integer>();
    }

    public void put(int item){
        list.add(item);
    }
}
