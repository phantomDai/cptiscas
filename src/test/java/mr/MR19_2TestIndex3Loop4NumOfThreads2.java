package mr;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MR19_2TestIndex3Loop4NumOfThreads2 extends TestCase{
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
		mr.executeService(3,4,2,"FineGrainedHeap");
	}
}