package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR16_2TestIndex3Loop0NumOfThreads5 extends TestCase{
	MR16_2 mr;
	@Before
	public void setUp(){
		mr = new MR16_2();
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