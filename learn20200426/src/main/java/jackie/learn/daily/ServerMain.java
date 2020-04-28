package jackie.learn.daily;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import jackie.learn.daily.MyAnnotation.ProcessType;

public class ServerMain {

	public static ArrayList<IStudent<Number>> studentList = null;
	public static void main(String[] args) throws InterruptedException {
		
		ReceiveInfo receiveInfo = new ReceiveInfo();
		receiveInfo.start();
		receiveInfo.join();
		parseStudent(studentList.get(0));
	}
	
	@MyAnnotation(process=ProcessType.JUMP)
	public static void parseStudent(IStudent<Number> student) {
		IPasswordFacade iPasswordFacade = (IPasswordFacade) student;
		iPasswordFacade.setPassword("abc");
		iPasswordFacade = (IPasswordFacade) student.getTeacher();
		iPasswordFacade.setPassword("abc");
		System.out.println("门面模式触发了");
		
		student.addObserver(new MyLog());
		student.addObserver(new MyConsole());
		student.getPassword();
		
		IStudent<Number> factory = (IStudent<Number>) MyProxy.factory(student);
		factory.setPassword("123");
		
		student.setId(1234);
		
	}
}

class ReceiveInfo extends Thread {
	@Override
	public void run() {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(5000));
			serverSocketChannel.configureBlocking(false);
			Selector selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			ByteBuffer byteBuffer = ByteBuffer.allocate(100);
			
			while( true ) {
				selector.select();
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				for (Iterator iterator = selectedKeys.iterator(); iterator.hasNext();) {
					SelectionKey selectionKey = (SelectionKey) iterator.next();
					if( selectionKey.isAcceptable()) {
						SocketChannel accept = serverSocketChannel.accept();
						accept.configureBlocking(false);
						accept.register(selector, selectionKey.OP_READ);
					}
					if( selectionKey.isReadable()) {
						SocketChannel channel = (SocketChannel) selectionKey.channel();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						while( channel.read(byteBuffer) > 0 ) {
							baos.write(byteBuffer.array());
							byteBuffer.flip();
						}
						ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
						Object readObject = objectInputStream.readObject();
						System.out.println(readObject);
						ServerMain.studentList = (ArrayList<IStudent<Number>>) readObject;
						return;
					}
					selectedKeys.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}