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

//(@Controller +  @ResponseBody) 순수한 값만을 사용하기 위한
//@ResponseBody 역할을 한다.
@RestController 
@RequestMapping("/members")
public class SpringRestController {

	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello Rest Ful";
	}
	
	// 객체를 JSON으로 반환 하는 경우
	// 웹에서 요청시에 jackson-databind 라이브러리가 있어야함.
	@RequestMapping("/respVO")
	public MemberVO respVO() {
		MemberVO vo = new MemberVO();
		vo.setId("skyrius");
		vo.setName("박정호");
		vo.setNum(8);
		vo.setAddr1("인천");
		vo.setPwd("test00");
		return vo;
	}
	
	
	
	// 컬렉션 타입의 객체를 반환하여 JSON-ARRAY로 반환하는 경우
	@RequestMapping("/respList")
	//public List<MemberVO> respList(){
		 public ResponseEntity<List<MemberVO>> respListNot(){
		List<MemberVO> list = new ArrayList<>();
		for(int i=0; i<=12; i++) {
			MemberVO vo = new MemberVO();
			vo.setId("skyrius"+i);
			vo.setName("박정호"+i);
			vo.setNum(i);
			vo.setAddr1("인천"+i);
			vo.setPwd("test0"+i);
			list.add(vo);
		}
		//return list;
		return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
		// 결과값을 보내어 처리 할수 있다.
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
					vo.setName("박정호"+i);
					vo.setNum(i);
					vo.setAddr1("인천"+i);
					vo.setPwd("test0"+i);
					list.add(vo);
				}
			}
			else {
				for(int i=0; i<=12; i++) {
					MemberVO vo = new MemberVO();
					vo.setId("xman"+i);
					vo.setName("김길동"+i);
					vo.setNum(i);
					vo.setAddr1("가디"+i);
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
