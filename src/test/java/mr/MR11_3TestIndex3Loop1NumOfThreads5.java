package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR11_3TestIndex3Loop1NumOfThreads5 extends TestCase{
	MR11_3 mr;
	@Before
	public void setUp(){
		mr = new MR11_3();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(3,1,5,"FineGrainedHeap");
	}
}