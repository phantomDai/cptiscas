package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR14TestIndex3Loop4NumOfThreads2 extends TestCase{
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
		mr.executeService(3,4,2,"FineGrainedHeap");
	}
}