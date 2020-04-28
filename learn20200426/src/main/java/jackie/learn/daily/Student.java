package jackie.learn.daily;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Observable;

import org.hamcrest.core.Is;

import jackie.learn.daily.MyAnnotation.ProcessType;

public class Student<T extends Number> extends Observable
		implements Cloneable, Serializable, IPasswordFacade, IStudent<T> {
	private static final long serialVersionUID = 1L;
	T id;
	String name;
	transient String password;
	Teacher teacher = new Teacher();

	public Student() {
	}

	public Student(T id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement stackTraceElement = stackTrace[2];

		try {
			Class<?> forName = Class.forName(stackTraceElement.getClassName());
			Method[] methods = forName.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.getName().equals(stackTraceElement.getMethodName())) {
					MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
					if (annotation != null && annotation.process() == ProcessType.JUMP) {
						System.out.println("注解触发了");
						return;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		setChanged();
		notifyObservers();
		return password;
	}

	public void setPassword(String password) {
		if ("123".equals(password)) {
			throw new MyRuntimeException("密码太简单了");
		}
		this.password = password;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		IStudent<Number> clone = (IStudent<Number>) super.clone();
		clone.setTeacher((Teacher) clone.getTeacher().clone());
		return clone;
	}

	@Override
	public String toString() {
		return "\nStudent [id=" + id + ", name=" + name + ", password=" + password + ", teacher=" + teacher + "]";
	}

}

class Teacher implements Cloneable, Serializable, IPasswordFacade {
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	String password;
	String subject;

	public Teacher() {
	}

	public Teacher(int id, String name, String password, String subject) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.subject = subject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", password=" + password + ", subject=" + subject + "]";
	}

}

class DescStrategy implements Comparator<IStudent<Number>> {

	public int compare(IStudent<Number> o1, IStudent<Number> o2) {
		return (int) (o2.getId().doubleValue() - o1.getId().doubleValue());
	}

}