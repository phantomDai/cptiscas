package threads;

import java.util.List;

/**
 * describe: put data into list
 *
 * @author phantom
 * @date 2018/11/29
 */
public class AddThread extends Thread {

    /**data*/
    List<Integer> mylist;

    /**flag*/
    volatile boolean flag = false;

    /**
     * control the state of thread
     */
    public void cancel(){
        this.flag = true;
    }


    AddThread(List mylist){
        this.mylist = mylist;
    }

    @Override
    public void run(){
        System.out.println("我是" + Thread.currentThread().getName() + "，开始执行任务");

        while(!flag){
            for (int i = 0; i < mylist.size(); i++){
                if (i >= mylist.size()){
                    System.out.println("i 的值过大，" + i);
                }

            }
        }
    }


}
