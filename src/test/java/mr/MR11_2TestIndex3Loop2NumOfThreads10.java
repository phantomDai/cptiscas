package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR11_2TestIndex3Loop2NumOfThreads10 extends TestCase{
	MR11_2 mr;
	@Before
	public void setUp(){
		mr = new MR11_2();
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