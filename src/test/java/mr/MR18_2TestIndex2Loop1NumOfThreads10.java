package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR18_2TestIndex2Loop1NumOfThreads10 extends TestCase{
	MR18_2 mr;
	@Before
	public void setUp(){
		mr = new MR18_2();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(2,1,10,"FineGrainedHeap");
	}
}