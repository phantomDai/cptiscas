package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR15TestIndex2Loop2NumOfThreads10 extends TestCase{
	MR15 mr;
	@Before
	public void setUp(){
		mr = new MR15();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(2,2,10,"FineGrainedHeap");
	}
}