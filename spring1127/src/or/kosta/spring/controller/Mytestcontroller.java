package or.kosta.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Mytestcontroller {
@RequestMapping(value="/test",method=RequestMethod.GET)
//git�� �߰��� ���ο� �ڵ� ����
	public String myview() {
		return "myview";
	}
	
}
