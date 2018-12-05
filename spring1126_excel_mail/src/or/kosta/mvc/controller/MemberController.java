package or.kosta.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import or.kosta.vo.MemberVO;

@RestController
@RequestMapping("/members2")
public class MemberController {
	
	@Autowired
	private SqlSessionTemplate ss;
	
	@RequestMapping("/allmember")
	public List<MemberVO> SelectAllMember(){
		List<MemberVO> list = new ArrayList<>();
		list = ss.selectList("mem.selAllMember");
		return list;
	}

}
