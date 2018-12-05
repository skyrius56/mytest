package or.kosta.mvc.inter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyHandlerInterceptor extends HandlerInterceptorAdapter{
	 // Controller �� ������ ����
	   @Override
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	         throws Exception {
	      System.out.println("preHandler ����!");
	      HandlerMethod method= (HandlerMethod) handler;   // hanler�� Object�� �̾Ƴ����ϴµ�, 
	      System.out.println("Bean :"+method.getBean());
	      System.out.println("Method :"+method.getMethod());
	      return true;
	   }
	   // Controller�� �޼����� ó���� ����� ���� return View �� ����Ǳ� 
	   // ����
	   @Override
	   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	         ModelAndView modelAndView) throws Exception {
	      System.out.println("viewNmae :"+modelAndView.getViewName());
	      modelAndView.addObject("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	      
	   }
	   // Controller�� ����� �� View ó������ ���� �� ȣ��
	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	         throws Exception {
	      System.out.println("afterCompletion ����!");
	      
	   }
}
