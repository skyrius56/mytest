package or.kosta.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	@RequestMapping("/")
	public String defaultView() {
		return "index";
	}
	@RequestMapping("/myindex")
	public String myDefaultView() {
		return "index";
	}
	
	@RequestMapping("/mytest")
	public String myDefaultView1() {
		return "index";
	}
	
	@RequestMapping("/mymember")
	public String myMemberView1() {
		return "ajaxMember";
	}
	
}
