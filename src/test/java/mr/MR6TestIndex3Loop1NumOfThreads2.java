package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR6TestIndex3Loop1NumOfThreads2 extends TestCase{
	MR6 mr;
	@Before
	public void setUp(){
		mr = new MR6();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(3,1,2,"FineGrainedHeap");
	}
}