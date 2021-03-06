package or.kosta.mvc.inter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyHandlerInterceptor extends HandlerInterceptorAdapter{
	 // Controller 에 들어가기전 접근
	   @Override
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	         throws Exception {
	      System.out.println("preHandler 동작!");
	      HandlerMethod method= (HandlerMethod) handler;   // hanler를 Object로 뽑아내야하는데, 
	      System.out.println("Bean :"+method.getBean());
	      System.out.println("Method :"+method.getMethod());
	      return true;
	   }
	   // Controller의 메서드의 처리가 종료된 이후 return View 가 실행되기 
	   // 직전
	   @Override
	   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	         ModelAndView modelAndView) throws Exception {
	      System.out.println("viewNmae :"+modelAndView.getViewName());
	      modelAndView.addObject("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	      
	   }
	   // Controller가 수행된 후 View 처리까지 끝난 뒤 호출
	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	         throws Exception {
	      System.out.println("afterCompletion 실행!");
	      
	   }
}
