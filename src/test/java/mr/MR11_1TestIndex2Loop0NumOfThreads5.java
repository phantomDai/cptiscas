package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR11_1TestIndex2Loop0NumOfThreads5 extends TestCase{
	MR11_1 mr;
	@Before
	public void setUp(){
		mr = new MR11_1();
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