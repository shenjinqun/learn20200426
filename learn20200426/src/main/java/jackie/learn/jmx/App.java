package jackie.learn.jmx;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class App {
	public static void main(String[] args) throws InterruptedException, MalformedObjectNameException,
			InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		// Agent
		MBeanServer mServer = ManagementFactory.getPlatformMBeanServer();
		HelloJMX helloJMX = new HelloJMX();
		ObjectName helloON = new ObjectName("jackie:name=helloJMX");
		mServer.registerMBean(helloJMX, helloON);

		new MyThread(helloJMX).start();
		
		new CountDownLatch(1).await();

	}
}

class MyThread extends Thread{
	HelloJMX helloJMX;
	public MyThread( HelloJMX helloJMX) {
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