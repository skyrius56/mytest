package or.kosta.mvc.controller;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmailController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping("email")
	public String mail() {
		return "email";
	}
	
	@RequestMapping("sendEmail")
	public ModelAndView sendMail(String mailTo, String subject, String text) throws AddressException, MessagingException {
		ModelAndView mv = new ModelAndView("success");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		System.out.println("mail To : " + mailTo);
		String from="''";
		// InternetAddress()�� ���� �ּ� ������� ����
		mimeMessage.setFrom(new InternetAddress(from));
		mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(mailTo));
		mimeMessage.setSubject(subject);
		mimeMessage.setText(text, "UTF-8", "html");
		mailSender.send(mimeMessage);
		return mv;
	}
	
}
