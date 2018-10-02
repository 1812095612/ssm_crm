package test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextTest {
	
	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

	@Test
	public void testDataSource() throws SQLException {
		
		DataSource dataSource = ioc.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		System.out.println(connection.getMetaData().getDatabaseProductVersion());
		connection.close();
		
	}
	
	@Test
	public void test01(){
		
		String s1 = "a" + "b" + 1;
		String s2 = "ab1";
		System.out.println(s1 == s2);//true
		
		String s3 = "a";
		String s4 = "ab";
		String s5 = s3 + "b";
		System.out.println(s4 == s5);//false
		
	}

}
