package or.kosta.mvc.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import or.kosta.vo.SearchVO;
import or.kosta.vo.ShowVO;

// ShowDao를 @Autowired하기 위한..
@Repository
public class ShowDao {
	@Autowired
	private SqlSessionTemplate ss;
	public void saveShow(ShowVO vo) {
		System.out.println("writer : " + vo.getWriter());
		System.out.println("grpcode : " + vo.getGrpcode());
		System.out.println("price : " + vo.getPrice());
		System.out.println("path : " + vo.getPath());
		System.out.println("pwd : " + vo.getPwd());
		System.out.println("comm : " + vo.getComm());
		System.out.println("reip : " + vo.getReip());
		ss.insert("showshop.showins", vo);
	}

	
	public List<ShowVO> getList(){
		return ss.selectList("showshop.list2");
	}
	
	public int getTotalCount(SearchVO svo) {
		return ss.selectOne("showshop.totalCount", svo);
	}
	
	public List<ShowVO> getListSearch(SearchVO svo){
		System.out.println("MAp"+svo.getSearchValue());
		return ss.selectList("showshop.listsearch", svo);
	}
}
