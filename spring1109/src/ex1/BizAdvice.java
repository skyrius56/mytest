package ex1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//<aop:config> ��, ProxyFactoryBean ����
@Aspect //Aspect ������ �� Ŭ������� ���
public class BizAdvice {
	@Before("execution(* ex1.BizServiceImple.*(..))")
	public void beforeMethod(JoinPoint jp) {
		String name = jp.getSignature().getName();
		Object[] obj = jp.getArgs(); // �����Ͻ� ������ �޼��� ���ڰ�!
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
		System.out.println("��ȯ��: ");
		System.out.println(ret);
	}

}
