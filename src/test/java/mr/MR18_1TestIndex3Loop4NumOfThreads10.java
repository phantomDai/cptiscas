package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR18_1TestIndex3Loop4NumOfThreads10 extends TestCase{
	MR18_1 mr;
	@Before
	public void setUp(){
		mr = new MR18_1();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(3,4,10,"FineGrainedHeap");
	}
}