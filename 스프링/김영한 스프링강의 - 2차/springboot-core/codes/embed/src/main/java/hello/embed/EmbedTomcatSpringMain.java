package hello.embed;

import hello.servlet.HelloServlet;
import hello.spring.HelloConfig;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class EmbedTomcatSpringMain {
	
	public static void main(String[] args) throws LifecycleException {
		System.out.println("EmbedTomcatSpringMain.main");
		
		// 톰캣 설정
		Tomcat tomcat = new Tomcat();
		Connector connector = new Connector();
		connector.setPort(8080);
		tomcat.setConnector(connector);
		
		// 스프링 컨테이너 생성
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(HelloConfig.class);

		// 디스패쳐 서블릿 생성, 스프링 컨테이너와 연결
		DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);

		// 디스패쳐 서블릿 등록
		Context context = tomcat.addContext("", "/");
		tomcat.addServlet("", "dispatcherServlet", dispatcherServlet);
		context.addServletMappingDecoded("/hello-spring", "dispatcherServlet");
		
		tomcat.start();
	}
}
