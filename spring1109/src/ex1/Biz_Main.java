package ex1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Biz_Main {
	public static void main(String[] args) {
		ApplicationContext ctx = new GenericXmlApplicationContext("ex1/ex1_anno.xml");
		BizService dao = ctx.getBean("dao",BizService.class);
		dao.bizMethod1("");
				
	}

}
