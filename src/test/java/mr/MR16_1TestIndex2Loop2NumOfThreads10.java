package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR16_1TestIndex2Loop2NumOfThreads10 extends TestCase{
	MR16_1 mr;
	@Before
	public void setUp(){
		mr = new MR16_1();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(2,2,10,"FineGrainedHeap");
	}
}