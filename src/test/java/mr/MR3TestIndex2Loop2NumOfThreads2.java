package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR3TestIndex2Loop2NumOfThreads2 extends TestCase{
	MR3 mr;
	@Before
	public void setUp(){
		mr = new MR3();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(2,2,2,"FineGrainedHeap");
	}
}