package edu.iastate.cs309.danePracticeAndTest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/test")
public class Practice {

	@RequestMapping(value = "/practice", method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		model.addAttribute("message", "This is a test, please ignore.");
		return "daneTest";
	}

}
