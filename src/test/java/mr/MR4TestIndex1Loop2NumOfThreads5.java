package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR4TestIndex1Loop2NumOfThreads5 extends TestCase{
	MR4 mr;
	@Before
	public void setUp(){
		mr = new MR4();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(1,2,5,"FineGrainedHeap");
	}
}