package or.kosta.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AndroidRequest1Controller {

	@GetMapping("/value1")
	@ResponseBody 
	// ������ ������ �����ϱ� ���ؼ� ����� �ٵ�
	// ���⼭ ������ ���� ���� ����ش�!!!
	public String ex1_hello(String msg, Model model) {
		StringBuffer sb = new StringBuffer();
		
		String contents = msg + ": Walk up!";
		sb.append(contents);
		sb.append("<br>");
		sb.append("---------");
		sb.append("<input type='text'>");
		
		System.out.println(contents);
		//model.addAttribute("contents", contents);
		//return "app1";
		return sb.toString();
	}

	
//	@GetMapping("/value2")
//	@ResponseBody
//	public Map<String, String> ex1_hello(){
//		Map<String, String> map = new HashMap<>();
//		map.put("1", "kosta188");
//		map.put("2", "kosta198");
//		map.put("3", "kosta208");
//		return map;
//	}
}
