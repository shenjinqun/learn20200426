package jackie.learn.daily;
	
import java.lang.reflect.Method;
import java.util.Observer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jackie.learn.daily.IStudent;

public class IStudentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	//测试是否有clone方法
	public void cloneTest() throws NoSuchMethodException, SecurityException{
		Method method = IStudent.class.getMethod("clone", null);
		System.out.println("aaaa");
	}
	
	//测试是否有addObserver方法
	public void addObserverTest() throws NoSuchMethodException, SecurityException{
		Method method = IStudent.class.getMethod("addObserver", Observer.class);
		System.out.println("bbbb");
	}

	//测试toString方法
	@Test
	public void toStringMethodTest() throws NoSuchMethodException, SecurityException{
		Method method = IStudent.class.getMethod("toString", null);
		System.out.println("cccc");
	}
}
