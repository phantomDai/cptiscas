package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR2TestIndex0Loop2NumOfThreads2 extends TestCase{
	MR2 mr;
	@Before
	public void setUp(){
		mr = new MR2();
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