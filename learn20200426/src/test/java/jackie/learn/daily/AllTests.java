package jackie.learn.daily;
	
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
 
@RunWith(Suite.class)
@SuiteClasses({ 
	IStudentTest.class, 
	StudentTest.class, 
	IPasswordFacadeTest.class,
	ClientMainTest.class,
	ServerMainTest.class, 
	MyProxyTest.class, 
	MyAnnotationTest.class, 
	MyRuntimeExceptionTest.class,
	MyClassLoaderTest.class
})
public class AllTests {

}