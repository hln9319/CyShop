package tutorials.lunan.spring;


//import java.util.List;

public class Triangle implements Shape/*implements InitializingBean, DisposableBean*/{
	
	private Point pointA;
	private Point pointB;
	private Point pointC;
	//private ApplicationContext context = null;



	public Point getPointA() {
		return pointA;
	}



	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}



	public Point getPointB() {
		return pointB;
	}



	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}



	public Point getPointC() {
		return pointC;
	}



	public void setPointC(Point pointC) {
		this.pointC = pointC;
	}


	@Override
	public void draw(){
		System.out.println("Drawing Triangle");
		System.out.println("Point A = (" + getPointA().getX() + "," + getPointA().getY() + ")");
		System.out.println("Point B = (" + getPointB().getX() + "," + getPointB().getY() + ")");
		System.out.println("Point C = (" + getPointC().getX() + "," + getPointC().getY() + ")");
		
	}

	/*	when implementing ApplicationContextAware, BeanNameAware, use those methods
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// TODO Auto-generated method stub
		this.context = context;
		Point point = (Point)context.getBean("pointA");
		System.out.println(point.getX());
	}

	@Override
	public void setBeanName(String beanName) {
		// TODO Auto-generated method stub
		System.out.println("Bean name is " + beanName);
	}
	*/

	/*@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("InitializingBean init method called for Triangle");
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DisposableBean destroy bean method called for Triangle");
	}*/
	
	public void myInit(){
		System.out.println("My init method called for Triangle");
	}
}
