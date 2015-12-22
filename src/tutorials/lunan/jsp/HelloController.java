package tutorials.lunan.jsp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello")
public class HelloController{
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String uploadFile(Model model) {
		return "LHtutorials/uploadFile";
	}
 
	@RequestMapping(value = "feedbackList", method = RequestMethod.GET)
	public String feedbackList(Model model) {
		return "feedbackList";
	}
	
   @RequestMapping(method = RequestMethod.GET)
   public String printHello(ModelMap model, HttpServletRequest request) {
      model.addAttribute("message", "Hello Spring MVC Framework!, path is:" + request.getSession().getServletContext().getRealPath("/"));
      System.out.println(request.getSession().getServletContext().getRealPath("/"));
      return "LHtutorials/hello";
   }

}