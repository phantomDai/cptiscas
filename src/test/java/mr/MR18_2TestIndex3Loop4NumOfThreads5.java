package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR18_2TestIndex3Loop4NumOfThreads5 extends TestCase{
	MR18_2 mr;
	@Before
	public void setUp(){
		mr = new MR18_2();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(3,4,5,"FineGrainedHeap");
	}
}