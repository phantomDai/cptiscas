package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR13TestIndex3Loop2NumOfThreads10 extends TestCase{
	MR13 mr;
	@Before
	public void setUp(){
		mr = new MR13();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(3,2,10,"FineGrainedHeap");
	}
}