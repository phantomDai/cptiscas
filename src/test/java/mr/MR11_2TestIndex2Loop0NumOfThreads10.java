package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR11_2TestIndex2Loop0NumOfThreads10 extends TestCase{
	MR11_2 mr;
	@Before
	public void setUp(){
		mr = new MR11_2();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(2,0,10,"FineGrainedHeap");
	}
}