package tutorials.lunan.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Circle implements Shape{
	
	private Point center;
	
	
	public Point getCenter() {
		return center;
	}

	@Autowired	//@Required
	@Qualifier("circleRelated")
	public void setCenter(Point center) {
		this.center = center;
	}


	@Override
	public void draw(){
		System.out.println("Drawing Circle");
		System.out.println("Circle center is: (" + center.getX() + "," + center.getY() + ")");
	}
}
