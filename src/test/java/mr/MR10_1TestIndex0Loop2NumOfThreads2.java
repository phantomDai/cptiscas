package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR10_1TestIndex0Loop2NumOfThreads2 extends TestCase{
	MR10_1 mr;
	@Before
	public void setUp(){
		mr = new MR10_1();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(0,2,2,"FineGrainedHeap");
	}
}