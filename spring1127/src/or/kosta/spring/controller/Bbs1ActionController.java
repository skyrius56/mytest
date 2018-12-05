package or.kosta.spring.controller;

import java.security.Principal;
import java.util.List;





import or.kosta.spring.dao.BbsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vo.Bbs1CommVO;
import vo.Bbs1VO;
import vo.PageVO;
import vo.SearchVO;

@Controller
public class Bbs1ActionController {
	 @Autowired
	 private BbsDao dao;
	 
	@RequestMapping(value="/bbs1Form")
	public String bbsForm(){
		return "/bbs/bbs1Form";
	}
	
	@RequestMapping(value="/bbs1In",method=RequestMethod.POST)
	 public ModelAndView insert(Bbs1VO vo){
	  dao.insertBbs1(vo);
	  ModelAndView mav = new ModelAndView();
	  mav.setViewName("redirect:/bbs1List");
	  return mav;
	 }
	 @RequestMapping(value="/bbs1List")
	 public ModelAndView list(Integer page, SearchVO vo,Principal principal){
	  
	  // pageVO�� ȹ��
	  int totalRows = dao.getTotalCount();
	  PageVO pageInfo = makePageVO(page, totalRows);
	  
	  // ������ ������ ����
	  vo.setBegin(String.valueOf(pageInfo.getStartRow()));
	  vo.setEnd(String.valueOf(pageInfo.getEndRow()));
	  
	  // ������ ����Ʈ ȹ��
	  List<Bbs1VO> list = dao.getList(vo);
	  
	  
	  System.out.println(list.size());
	  ModelAndView mav = new ModelAndView();
	  mav.setViewName("bbs/bbsList");
	  mav.addObject("list", list);
	  mav.addObject("pageInfo", pageInfo); // pageInfo ����
	  if(principal != null)
	  mav.addObject("principal", principal.getName());
	  return mav;
	 }
	 


	 
	 public PageVO makePageVO(Integer page, int totalRows) {
	  
	  PageVO pageInfo = new PageVO();
	  int rowsPerPage = 15; // ���������� ������ ��ϼ� - properties
	  int pagesPerBlock = 5; // �� ��ϴ� ������ ������ �� - properties
	  if (page == null) page = 0;
	  if(page  == 0) page = 1; //������ �ʱ�ȭ
	  int currentPage = page; // ���� ������ ��
	  int currentBlock = 0; // ���� ��� �ʱ�ȭ
	  if (currentPage % pagesPerBlock == 0) { // ���� ��� �ʱⰪ
	   currentBlock = currentPage / pagesPerBlock;
	  } else { // ���� ����̳�
	   currentBlock = currentPage / pagesPerBlock + 1;
	  }
	  int startRow = (currentPage - 1) * rowsPerPage + 1; // ���۸�ϰ�����
	  int endRow = currentPage * rowsPerPage;// ������ ��ϰ� ����

	  
	  // ��ü ������ ��
	  
	  System.out.println("totalRows:" + totalRows);
	  // ��ü ������ ���ϴ� ����
	  int totalPages = 0;
	  if (totalRows % rowsPerPage == 0) {
	   totalPages = totalRows / rowsPerPage;
	  } else {
	   totalPages = totalRows / rowsPerPage + 1;
	  }
	  // ��ü ��ϰ��� ���ϴ� ����
	  int totalBlocks = 0;
	  if (totalPages % pagesPerBlock == 0) {
	   totalBlocks = totalPages / pagesPerBlock;
	  } else {
	   totalBlocks = totalPages / pagesPerBlock + 1;
	  }
	  // ��� ����������� PageVO�� �����Ѵ�.
	  pageInfo.setCurrentPage(currentPage);
	  pageInfo.setCurrentBlock(currentBlock);
	  pageInfo.setRowsPerPage(rowsPerPage);
	  pageInfo.setPagesPerBlock(pagesPerBlock);
	  pageInfo.setStartRow(startRow);
	  pageInfo.setEndRow(endRow);
	  pageInfo.setTotalRows(totalRows);
	  pageInfo.setTotalPages(totalPages);
	  pageInfo.setTotalBlocks(totalBlocks);
	  
	  return pageInfo;
	 }
	 
	 @RequestMapping(value="/bbs1detail",method=RequestMethod.GET)
	 public ModelAndView detail(int num,Principal principal){
		 System.out.println("principal"+principal.getName());
	  Bbs1VO vo  = dao.getDetail(num);
	  //����� ����Ʈ�ϱ� ���� Dao�� ȣ��
	  List<Bbs1CommVO> cList = dao.getCommList(num);
	  
	  dao.BbsHitUp(num);
	  
	  ModelAndView mav = new ModelAndView();
	  mav.setViewName("bbs/bbsDetail");
	  mav.addObject("vo", vo);
	  mav.addObject("cList", cList);
	  mav.addObject("principal", principal.getName());
	  return mav;
	 }
	 @RequestMapping(value="/bbs1Comm",method=RequestMethod.POST)
	 public ModelAndView bbsCommin(Bbs1CommVO v){
	  System.out.println("writer : "+v.getWriter());
	  dao.insertComm(v);
	  ModelAndView mav = new ModelAndView();
	  mav.setViewName("redirect:/bbs1detail?num="+v.getKcode());
	  // num���� ������ ������ �䰡 �ȸ������..
	  return mav;
	  
	 }
	
}
