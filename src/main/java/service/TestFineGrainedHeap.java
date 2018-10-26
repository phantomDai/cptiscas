package service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 测试FineGrainedHeap的主要类
 */
public class TestFineGrainedHeap {

    /**从list中取出数据的默认个数**/
    private static final int DEFAULTNUMBER = 10 ;

    /**默认开启的线程数目: 10*/
    private static final int DEFAULTTHREAD = 10;

    /**列表中数的总个数*/
    private static final int RANGE = 10000;

    /**变异体的实例*/
    Object mutantInstance;

    /**向列表中添加元素的方法名*/
    private static final String METHODNAME_ADD = "add" ;

    /**从列表中去一个元素的方法名*/
    private static final String METHODNAME_REMOVE = "removeMin" ;

    /**待测的对象*/
    private Class clazz = null;

    /**待测对象的构造器*/
    private Constructor constructor = null;

    /**待测对象中添加元素的方法*/
    Method method_add ;

    /**待测对象中取数据的方法*/
    Method method_remove;


    /**执行取数据的线程组*/
    RemoveMinThread[] removeMinThreads;

    /**添加数据的线程组*/
    AddThread[] addThreads;

    /**存放取出元素的向量*/
    Vector<Integer> vector = new Vector<Integer>();

    /**产生数据的对象*/
    Random random = new Random();

    /**
     * 添加数据的方式：顺序
     * 取数据的方式：顺序
     */
    public void sequentialAndsequential(int[] list, String myMutantFullName, int numberOfThreads){
        //清空集合
        vector.clear();

        //获取一个实例
        getInstance(list.length,myMutantFullName);

        //顺序地添加数据
        sequentAddDataToList(list);

        //顺序地移除数据
        for (int i = 0; i < DEFAULTTHREAD; i++) {
            boolean flag = false;
            while (!flag){
                try{
                    Object result = method_remove.invoke(mutantInstance,null);
                    flag = addElements(result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * 添加数据的方式：顺序
     * 取数据的方式：并发
     * @param list 生成的随机序列
     * @param myMutantFullName  具体的变异体名字或者源程序的名字
     * @param numberOfThreads 开启的线程数目
     */
    public void sequentialAndconcurrent(int[] list, String myMutantFullName, int numberOfThreads){
        //清空集合
        vector.clear();

        //获取一个实例
        getInstance(list.length,myMutantFullName);

        //顺序地添加数据
        sequentAddDataToList(list);

        //并发的移除数据
        //根据开启的线程数目决定循环的次数
        int loop = 10 / numberOfThreads;
        for (int j = 0; j < loop; j++) {
            //初始化线程
            for (int i = 0; i < numberOfThreads; i++) {
                removeMinThreads[i] = new RemoveMinThread();
            }
            //开启线程
            for (int i = 0; i < numberOfThreads; i++) {
                removeMinThreads[i].start();
            }
            //执行线程中的任务
            for (int i = 0; i < numberOfThreads; i++) {
                try {
                    removeMinThreads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 添加数据的方式：并发
     * 取数据的方式：顺序
     * @param list 生成的随机序列
     * @param myMutantFullName 具体的变异体名字或者源程序的名字
     * @param numberOfThreads 开启的线程数目
     */
    public void concurrentAndsequential(int[] list, String myMutantFullName, int numberOfThreads){

        //获取一个实例
        getInstance(list.length,myMutantFullName);

        //并发地向列表中添加数据
        //每个线程需要添加的数据的集合的集合
        List<List<Integer>> lists = new ArrayList<>();
        //每个线程需要添加的数据的数目
        int pre_thread = list.length / numberOfThreads ;
        //将整形数组转化为列表
        List<Integer> mylist = Arrays.stream(list).boxed().collect(Collectors.toList());

        for (int i = 0; i < numberOfThreads; i++) {
            List<Integer> tempList = new ArrayList<>();
            if (i <= (numberOfThreads - 2)) {
                tempList.addAll(mylist.subList(i * pre_thread, i * pre_thread + numberOfThreads));
            }else {
                tempList.addAll(mylist.subList(i * pre_thread, mylist.size()));
            }
            lists.add(tempList);
            tempList.clear();
        }
        //初始化线程
        for (int i = 0; i < numberOfThreads; i++) {
            addThreads[i] = new AddThread(lists.get(i));
        }
        //启动线程
        for (int i = 0; i < numberOfThreads; i++) {
            addThreads[i].start();
        }
        //执行线程中的任务
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                addThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //顺序地在列表中取数据
        sequentRemoveDataFromList();


    }


    /**
     * 添加数据的方式：并发
     * 取数据的方式：并发
     * @param list 生成的随机序列
     * @param myMutantFullName 具体的变异体名字或者源程序的名字
     * @param numberOfThreads 开启的线程数目
     */
    public void concurrentAndconcurrent(int[] list, String myMutantFullName, int numberOfThreads){

        /*********************  并发地添加数据  ******************************/
        //获取一个实例
        getInstance(list.length,myMutantFullName);

        //并发地向列表中添加数据
        //每个线程需要添加的数据的集合的集合
        List<List<Integer>> lists = new ArrayList<>();
        //每个线程需要添加的数据的数目
        int pre_thread = list.length / numberOfThreads ;
        //将整形数组转化为列表
        List<Integer> mylist = Arrays.stream(list).boxed().collect(Collectors.toList());
        for (int i = 0; i < numberOfThreads; i++) {
            List<Integer> tempList = new ArrayList<Integer>();
            if (i <= (numberOfThreads - 2)) {
                tempList.addAll(mylist.subList(i * pre_thread, i * pre_thread + numberOfThreads));
            }else {
                tempList.addAll(mylist.subList(i * pre_thread, mylist.size()));
            }
            lists.add(tempList);
            tempList.clear();
        }
        //初始化线程
        for (int i = 0; i < numberOfThreads; i++) {
            addThreads[i] = new AddThread(lists.get(i));
        }
        //启动线程
        for (int i = 0; i < numberOfThreads; i++) {
            addThreads[i].start();
        }
        //执行线程中的任务
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                addThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*********************  并发地取数据  ******************************/
        //并发的移除数据
        //根据开启的线程数目决定循环的次数
        int loop = 10 / numberOfThreads;
        for (int j = 0; j < loop; j++) {
            //初始化线程
            for (int i = 0; i < numberOfThreads; i++) {
                removeMinThreads[i] = new RemoveMinThread();
            }
            //开启线程
            for (int i = 0; i < numberOfThreads; i++) {
                removeMinThreads[i].start();
            }
            //执行线程中的任务
            for (int i = 0; i < numberOfThreads; i++) {
                try {
                    removeMinThreads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取待测程序的实例以及初始化要被调用的方法
     * @param lengthOfList 列表的长度
     * @param myMutantFullName
     */
    private void getInstance(int lengthOfList, String myMutantFullName){
        try {
            clazz = Class.forName(myMutantFullName);
            constructor = clazz.getConstructor(int.class);
            mutantInstance = constructor.newInstance(lengthOfList);
            method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);
            method_remove = clazz.getMethod(METHODNAME_REMOVE,null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得vector中的数据
     * @return 返回优先级最高的数据
     */
    public int[] getResults(){
        int[] tempList = new int[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            tempList[i] = vector.get(i);
        }
        return tempList;
    }


    /**
     * 将数据顺序地添加到列表中
     * @param list
     */
    private void sequentAddDataToList(int[] list){
        for (int ele: list) {
            try {
                method_add.invoke(mutantInstance,ele,ele);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 逐个从数据结构中移除数据
     */
    private void sequentRemoveDataFromList(){
        //清空容器
        vector.clear();
        //取数据
        try {
            for (int i = 0; i < DEFAULTNUMBER; i++) {
                Object temp = method_remove.invoke(mutantInstance,null);
                vector.add(Integer.parseInt(temp.toString()));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }





    /**
     * 顺序地向向量中添加元素。
     * @param element 从列表中取出的一个元素
     * @return 是否成功地向向量中添加了元素
     */
    private synchronized boolean addElements(Object element){
        if (element == null){
            vector.add(random.nextInt(1000) + 7000);
            return true;
        }else {
            if (vector.contains(Integer.parseInt(element.toString()))) {
                return false;
            }else{
                vector.add(Integer.parseInt(element.toString()));
                return true;
            }
        }
    }


    /**
     * 从列表中取数据的线程
     */
    class RemoveMinThread extends Thread{
        volatile boolean flag = false;

        @Override
        public void run(){
            while (!flag){
                try {
                    Object result = method_remove.invoke(mutantInstance,null);
                    flag = addElements(result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class AddThread extends  Thread{
        List<Integer> mylist;
        AddThread(List<Integer> list){
            mylist = list;
        }
        @Override
        public void run(){
            for (int i = 0; i < mylist.size(); i++) {
                try {
                    method_add.invoke(mutantInstance,mylist.get(i));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
