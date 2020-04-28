package jackie.learn.daily;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {

	public static ArrayList<IStudent<Number>> studentList = null;
	public static IStudent<Number> student;
	public static volatile boolean completed = false;
	static {
		student = new Student<Number>(1, "zhangsan", "123456");
		studentList = new ArrayList<IStudent<Number>>();
	};
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		new Thread( new Runnable() {
			public void run() {
				while( !ClientMain.completed ) {};
				countDownLatch.countDown();
			}
		}).start();
		
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
		newFixedThreadPool.execute(new StudentThread(0, student));
		newFixedThreadPool.execute(new StudentThread(1, student));
		newFixedThreadPool.execute(new StudentThread(2, student));
		
		countDownLatch.await();
		newFixedThreadPool.shutdown();
		System.out.println(ClientMain.studentList);
		
		Socket socket = new Socket( "localhost", 5000);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream( baos );
		
		Collections.sort(ClientMain.studentList, new DescStrategy());
		objectOutputStream.writeObject(ClientMain.studentList);
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(baos.toByteArray());
		
	}
}

class StudentThread implements Runnable{
	int mode;
	IStudent<Number> student;
	
	public StudentThread(int mode, IStudent<Number> student) {
		this.mode = mode;
		this.student = student;
	}

	public void run() {
		for( int i =0; i < 2; i++ ) {
			synchronized (ClientMain.studentList) {
				try {
					while( ClientMain.studentList.size() % 3 != mode ) {
						ClientMain.studentList.wait();
					}
					IStudent<Number> newStudent = StudentProcess.newStudent(student);
					ClientMain.studentList.add(newStudent);
					ClientMain.studentList.notifyAll();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if( mode == 2 ){
			ClientMain.completed = true;
		}
	}
}

class StudentProcess {
	public static IStudent<Number> newStudent(IStudent<Number> student) {
		int id = ClientMain.studentList.size() + 1;

		try {
			IStudent<Number> s = (IStudent<Number>) student.clone();
			s.setId(id);
			s.setName("xm_" + id);
			s.setPassword("mm_" + id);
			return s;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}