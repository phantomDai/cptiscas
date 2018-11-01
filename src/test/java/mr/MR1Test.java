package mr;

import org.junit.Test;


/** 
* MR1 Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮ�� 27, 2018</pre> 
* @version 1.0 
*/ 
public class MR1Test { 



/** 
* 
* Method: executeService(int index, int loop, int numberOfThreads, String objectName) 
* 
*/ 
@Test
public void testExecuteService() throws Exception {
MR1 mr = new MR1();
mr.executeService(0,0,10,"SkipQueue");
} 


} 
