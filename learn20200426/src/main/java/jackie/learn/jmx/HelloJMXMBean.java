package jackie.learn.jmx;

public interface HelloJMXMBean {
	void sayHello();

	void setUserName(String userName);

	String getUserName();

	String getNowTime();
}
