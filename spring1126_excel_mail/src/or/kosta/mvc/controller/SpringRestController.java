package or.kosta.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import or.kosta.vo.MemberVO;

//(@Controller +  @ResponseBody) ������ ������ ����ϱ� ����
//@ResponseBody ������ �Ѵ�.
@RestController 
@RequestMapping("/members")
public class SpringRestController {

	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello Rest Ful";
	}
	
	// ��ü�� JSON���� ��ȯ �ϴ� ���
	// ������ ��û�ÿ� jackson-databind ���̺귯���� �־����.
	@RequestMapping("/respVO")
	public MemberVO respVO() {
		MemberVO vo = new MemberVO();
		vo.setId("skyrius");
		vo.setName("����ȣ");
		vo.setNum(8);
		vo.setAddr1("��õ");
		vo.setPwd("test00");
		return vo;
	}
	
	
	
	// �÷��� Ÿ���� ��ü�� ��ȯ�Ͽ� JSON-ARRAY�� ��ȯ�ϴ� ���
	@RequestMapping("/respList")
	//public List<MemberVO> respList(){
		 public ResponseEntity<List<MemberVO>> respListNot(){
		List<MemberVO> list = new ArrayList<>();
		for(int i=0; i<=12; i++) {
			MemberVO vo = new MemberVO();
			vo.setId("skyrius"+i);
			vo.setName("����ȣ"+i);
			vo.setNum(i);
			vo.setAddr1("��õ"+i);
			vo.setPwd("test0"+i);
			list.add(vo);
		}
		//return list;
		return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
		// ������� ������ ó�� �Ҽ� �ִ�.
		//return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/respList2")
	public ResponseEntity<List<MemberVO>> respList(String code){
		List<MemberVO> list = new ArrayList<>();
		try {
			if(code.equals("1")) {
				for(int i=0; i<=12; i++) {
					MemberVO vo = new MemberVO();
					vo.setId("skyrius"+i);
					vo.setName("����ȣ"+i);
					vo.setNum(i);
					vo.setAddr1("��õ"+i);
					vo.setPwd("test0"+i);
					list.add(vo);
				}
			}
			else {
				for(int i=0; i<=12; i++) {
					MemberVO vo = new MemberVO();
					vo.setId("xman"+i);
					vo.setName("��浿"+i);
					vo.setNum(i);
					vo.setAddr1("����"+i);
					vo.setPwd("gogoman"+i);
					list.add(vo);
				}
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
