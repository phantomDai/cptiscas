package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR8TestIndex1Loop3NumOfThreads2 extends TestCase{
	MR8 mr;
	@Before
	public void setUp(){
		mr = new MR8();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(1,3,2,"FineGrainedHeap");
	}
}