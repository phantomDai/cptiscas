package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR12TestIndex1Loop2NumOfThreads10 extends TestCase{
	MR12 mr;
	@Before
	public void setUp(){
		mr = new MR12();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(1,2,10,"FineGrainedHeap");
	}
}