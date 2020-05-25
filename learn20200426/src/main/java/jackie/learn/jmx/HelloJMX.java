package jackie.learn.jmx;

public class HelloJMX implements HelloJMXMBean {
	private String userName;
	public void sayHello() {
		if (this.userName == null) {
			System.out.println("请从jconsole中输入用户名!");
		} else {
			System.out.println("Hello, " + this.userName);
		}
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public String getNowTime() {
		return Long.toString(System.currentTimeMillis());
	}
}
