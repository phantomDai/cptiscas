package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR19_2TestIndex1Loop0NumOfThreads10 extends TestCase{
	MR19_2 mr;
	@Before
	public void setUp(){
		mr = new MR19_2();
	}
	@After
	public void tearDown(){
		mr = null;
	}
	@Test
	public void testMR(){
		mr.executeService(1,0,10,"FineGrainedHeap");
	}
}