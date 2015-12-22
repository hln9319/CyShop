package tutorials.lunan.spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DrawingApp {

	public static void main(String[] args) {
		//Triangle triangle = new Triangle();
		//BeanFactory factory = new XmlBeanFactory(new FileSystemResource("spring.xml"));
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("tutorials/lunan/spring/spring.xml");
		//context.registerShutdownHook();
		//Triangle triangle = (Triangle) context.getBean("triangle");
		Shape shape = (Shape) context.getBean("circle");
		//Triangle triangle2 = (Triangle) context.getBean("triangle2");
		//triangle2.getPointA().setX(10);
		//triangle2.getPointB().setX(10);
		shape.draw();
		
	}

}
