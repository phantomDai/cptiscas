package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR14TestIndex2Loop4NumOfThreads10 extends TestCase{
	MR14 mr;
	@Before
	public void setUp(){
		mr = new MR14();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(2,4,10,"FineGrainedHeap");
	}
}