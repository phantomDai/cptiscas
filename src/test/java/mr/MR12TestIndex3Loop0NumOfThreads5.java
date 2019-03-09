package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR12TestIndex3Loop0NumOfThreads5 extends TestCase{
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
		mr.executeService(3,0,5,"FineGrainedHeap");
	}
}