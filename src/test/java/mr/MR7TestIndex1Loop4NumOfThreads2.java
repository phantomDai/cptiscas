package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR7TestIndex1Loop4NumOfThreads2 extends TestCase{
	MR7 mr;
	@Before
	public void setUp(){
		mr = new MR7();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(1,4,2,"FineGrainedHeap");
	}
}