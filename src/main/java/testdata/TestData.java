package testdata;

import java.util.Random;

/**
 * @author phantom
 * @Description 产生测试的数据
 * @date 2018/10/22/022
 */
public class TestData {

    /**产生数据的默认个数*/
    private int numberOfTestData = 10000 ;

    /**
     * 产生数据
     * @param seed 指定产生数据的种子
     * @return data
     */
    public int[] generateTestData (int seed){

        //初始化列表
        int[] data = new int[numberOfTestData];

        //根据指定的种子产生数据，种子与当前测试的额重复次数有关
        Random random = new Random(seed);

        //产生数据
        for (int i = 0; i < numberOfTestData; i++) {
            data[i] = random.nextInt(9000);
        }
        return data;
    }

    public int getNumberOfTestData() {
        return numberOfTestData;
    }
}
