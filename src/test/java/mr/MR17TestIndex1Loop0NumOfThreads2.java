package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR17TestIndex1Loop0NumOfThreads2 extends TestCase{
	MR17 mr;
	@Before
	public void setUp(){
		mr = new MR17();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(1,0,2,"FineGrainedHeap");
	}
}