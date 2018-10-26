package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author phantom
 * @Description
 * @date 2018/10/26/026
 */
public class AddTest {

    @Before
    public void before() throws Exception {
        System.out.println("测试开始");
    }

    @After
    public void after() throws Exception {
        System.out.println("测试结束");
    }

    /**
     *
     * Method: add(int a, int b)
     *
     */
    @Test
    public void testAdd() throws Exception {
        Add add = new Add();
        int a = add.add(1,2);
        Assert.assertEquals(3,a);
    }



}
