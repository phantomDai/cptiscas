package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR10_2TestIndex3Loop4NumOfThreads10 extends TestCase{
	MR10_2 mr;
	@Before
	public void setUp(){
		mr = new MR10_2();
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