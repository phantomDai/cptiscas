package main;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author phantom
 * @Description 程序运行的入口
 * @date 2018/10/31/031
 */
public class Main {
    /**
     * MR的名字
     */
    private static final String[] MRNAMES = {"MR1","MR2","MR3","MR4","MR5","MR6","MR7","MR8","MR9",
            "MR10_1","MR10_2","MR10_3","MR11_1","MR11_2","MR11_3","MR12","MR13",
            "MR14","MR15","MR16_1","MR16_2","MR17","MR18_1","MR18_2","MR19_1","MR19_2"};
//    private static final String[] MRNAMES = {"MR1"};

    /**
     * MR的包名
     */
    private static final String MRPACKAGE = "mr." ;

    /**
     * 测试对象的名字
     */
//    private static final String[] OBJECTNAMES = {"SimpleLinear","SimpleTree","SequentialHeap",
//            "FineGrainedHeap","SkipQueue"};
//    private static final String[] OBJECTNAMES = {"SimpleTree"};
    private static final String[] OBJECTNAMES = {"FineGrainedHeap"};
//    private static final String[] OBJECTNAMES = {"SkipQueue"};
//    private static final String[] OBJECTNAMES = {"SequentialHeap"};
//    private static final String[] OBJECTNAMES = {"SimpleLinear"};

    /**
     * 测试场景的编码
     */
    private static final int[] INDEXS = {0, 1, 2, 3};
//    private static final int[] INDEXS = {3};

    /**
     *并发测试开启的线程数目
     */
    private static final int[] NUMBEROFTHREADS = {2, 5, 10};
//    private static final int[] NUMBEROFTHREADS = {10};


    /**
     * 循环控制
     */
    private static final int LOOP = 5;

    /**MR的类对象*/
    private Class MRClass;

    /**MR的构造器实例*/
    private Constructor MRConstructor;

    /**MR类的实例*/
    private Object instance ;

    /**MR的方法实例*/
    private Method MRmethod_executeService;

    /**
     * 要调用的方法的名字
     */
    private static final String METHOD_NAME = "executeService";



    /**
     * 执行测试的主方法
     */
    public void startExecuteTesting(){
        for (int i = 0; i < OBJECTNAMES.length; i++) {
            //如果待测程序是“sequentialHeap”的话是需要测试1种场景

            for (int j = 0; j < MRNAMES.length; j++) {
//                for (int j = 0; j < 1; j++) {
                String MRName = MRPACKAGE + MRNAMES[j];
                initializeObject(MRName);
                for (int k = 0; k < LOOP; k++) {
                    for (int l = 0; l < INDEXS.length; l++) {
                        for (int m = 0; m < NUMBEROFTHREADS.length; m++) {
                            try {
                                MRmethod_executeService.invoke(instance,INDEXS[l],k,NUMBEROFTHREADS[m],OBJECTNAMES[i]);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * 实例化所有的对象
     */
    private void initializeObject(String MRName){
        try {
            MRClass = Class.forName(MRName);
            MRConstructor = MRClass.getConstructor(null);
            instance = MRConstructor.newInstance(null);
            MRmethod_executeService = MRClass.getMethod(METHOD_NAME,int.class,int.class,int.class,String.class);
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
    public static void main(String[] args) {
        Main main = new Main();
        main.startExecuteTesting();
        System.exit(0);
    }
}
