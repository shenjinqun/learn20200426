package jackie.learn.jmx;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * 测试
 * @author Jackie
 *
 */
public class App {
	public static void main(String[] args) throws InterruptedException, MalformedObjectNameException,
			InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		// Agent
		MBeanServer mServer = ManagementFactory.getPlatformMBeanServer();
		HelloJMXMBean2 myJMX = new HelloJMXMBean2();
		ObjectName helloON = new ObjectName("aaaa:type=bbbb,name=helloJMXkjfsdjf");
		mServer.registerMBean(myJMX, helloON);

		new MyThread(myJMX).start();
		
		new CountDownLatch(1).await();

	}
}

class MyThread extends Thread{
	HelloJMXMBean2 helloJMX;
	public MyThread( HelloJMXMBean2 helloJMX) {
		this.helloJMX = helloJMX;
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println("username:" + helloJMX.getUserName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}