package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR5TestIndex2Loop0NumOfThreads5 extends TestCase{
	MR5 mr;
	@Before
	public void setUp(){
		mr = new MR5();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(2,0,5,"FineGrainedHeap");
	}
}