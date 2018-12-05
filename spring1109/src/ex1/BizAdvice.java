package ex1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//<aop:config> 즉, ProxyFactoryBean 선언
@Aspect //Aspect 역할을 할 클래스라고 명시
public class BizAdvice {
	@Before("execution(* ex1.BizServiceImple.*(..))")
	public void beforeMethod(JoinPoint jp) {
		String name = jp.getSignature().getName();
		Object[] obj = jp.getArgs(); // 비지니스 로직의 메서드 인자값!
		for(Object e : obj) {
			System.out.println(e);
		}
		System.out.println("name: "+name);
	}
	@AfterReturning(
			pointcut="execution(* ex1.BizServiceImple.second(..))",
			returning="ret")
	public void myReturnMethod(JoinPoint jp, Object ret) {
		String namev = jp.getSignature().getName();
		System.out.println("Name :"+namev);
		System.out.println("반환값: ");
		System.out.println(ret);
	}

}
