package tutorials.lunan.jsp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController{
	@RequestMapping(value="/student", method=RequestMethod.GET)
	public ModelAndView student(){
		return new ModelAndView("LHtutorials/student", "command", new Student());
	}
	
	@RequestMapping(value="/addStudent", method=RequestMethod.POST)
	public String addStudent(@ModelAttribute("SpringWeb")Student student, ModelMap model, HttpServletResponse response){
		model.addAttribute("name", student.getName());
		model.addAttribute("age", student.getAge());
		model.addAttribute("id", student.getId());
		
		Cookie name = new Cookie("name",
				student.getName());
	   Cookie age = new Cookie("age", student.getAge().toString());

	   // Set expiry date after 24 Hrs for both the cookies.
	   name.setMaxAge(60); 
	   age.setMaxAge(60*60*24); 
	   
	   response.addCookie(name);
	   response.addCookie(age);
	   
	   // Add both the cookies in the response header.
	   //model.get(name);
	   
      
		return "LHtutorials/result";
	}
}
