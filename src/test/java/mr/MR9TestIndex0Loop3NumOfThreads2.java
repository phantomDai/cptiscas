package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR9TestIndex0Loop3NumOfThreads2 extends TestCase{
	MR9 mr;
	@Before
	public void setUp(){
		mr = new MR9();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(0,3,2,"FineGrainedHeap");
	}
}