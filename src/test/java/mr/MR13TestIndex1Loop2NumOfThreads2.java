package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR13TestIndex1Loop2NumOfThreads2 extends TestCase{
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
		mr.executeService(1,2,2,"FineGrainedHeap");
	}
}