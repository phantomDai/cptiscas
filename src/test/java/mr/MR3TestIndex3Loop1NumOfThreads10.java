package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR3TestIndex3Loop1NumOfThreads10 extends TestCase{
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
		mr.executeService(3,1,10,"FineGrainedHeap");
	}
}