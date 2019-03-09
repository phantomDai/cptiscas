package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR19_1TestIndex3Loop0NumOfThreads10 extends TestCase{
	MR19_1 mr;
	@Before
	public void setUp(){
		mr = new MR19_1();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(3,0,10,"FineGrainedHeap");
	}
}