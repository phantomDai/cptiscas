package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR2TestIndex2Loop4NumOfThreads2 extends TestCase{
	MR2 mr;
	@Before
	public void setUp(){
		mr = new MR2();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(2,4,2,"FineGrainedHeap");
	}
}