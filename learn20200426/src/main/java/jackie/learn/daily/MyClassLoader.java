package jackie.learn.daily;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {

	private FileInputStream fis;

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		String path = Thread.class.getResource("/").getFile().replace("test-", "") + name.replace(".", "/") + ".class";

		try {
			fis = new FileInputStream(path);
			byte[] b = new byte[100];
			int len;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len=fis.read(b)) != -1 ) {
				baos.write(b, 0, len);
			}
			byte[] byteArray = baos.toByteArray();
			Class<?> defineClass = defineClass(name, byteArray, 0, byteArray.length);
			return defineClass;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> findClass = new MyClassLoader().findClass("learn.jackie.daily.Student");
		Object newInstance = findClass.newInstance();

		System.out.println(newInstance);
	}
}
