package or.kosta.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.primitives.Bytes;

import or.kosta.mvc.dao.ShowDao;
import or.kosta.vo.PageVO;
import or.kosta.vo.SearchVO;
import or.kosta.vo.ShowVO;

@Controller
public class UpSaveController {

	private static final int BUFFER_SIZE = 4096;
	
	@Autowired
	private ShowDao dao;
	
	@GetMapping("/showform")
	public String upform() {
		return "showform";
	}
	@RequestMapping(value="/fileDown")
	public void fileDown(@RequestParam("fileName") String fileName, HttpSession session,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		//applicationContext ��ü�� request�� ���� ��
		ServletContext context = request.getServletContext();
		
		//���ε�� ��� ����
		String path = session.getServletContext().getRealPath("/resources/imgfile/")+fileName;
		
		System.out.println("���ε�� ��� : " + path);
		// �� ��η� ���ϰ�ü�� ����
		File downloadFile = new File(path);
		System.out.println("DownLoadFile : " + downloadFile.exists());
		
		// FileInputStream���� �о��
		FileInputStream fi =  new FileInputStream(downloadFile);
		
		// ��û��ü�κ��� ����� �������� ����Ÿ���� ��
		String mimeType = context.getMimeType(path);
		
		//���� ����Ÿ�Ӱ��� ������ �׳� ����Ʈ�� ��Ʈ������ ����
		if(mimeType == null) {
			mimeType = "application/octet-stream";
		}
		
		// ������ ����Ÿ�� ����
		response.setContentType(mimeType);
		
		// �ٿ�ε�� ������ ���� ����
		response.setContentLength((int) downloadFile.length());
		
		// �ٿ�ε� type�� ������
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", 
				downloadFile.getName());
		
		// ���� �ٿ�ε� Ÿ���� ������ ����� ����
		response.setHeader(headerKey, headerValue);
		
		// �������� ���� ��Ʈ���� ����
		OutputStream outputStream = response.getOutputStream();
		
		// ���۸� ������ ������ ���� ����
		byte[] buffer = new byte[BUFFER_SIZE];
		
		// ���� �������� ������, �ڿ� ������ ��!
		int byteRead = -1;
		while((byteRead = fi.read(buffer)) != -1) {
			outputStream.write(buffer, 0, byteRead);
		}
		fi.close();
		outputStream.close();
	}
	
	@RequestMapping("downloadExcel")
	public ModelAndView downloadExcel(){
		System.out.println("???");
		List<ShowVO> list = dao.getList();
		System.out.println("listSize : "+ list.size());
		return new ModelAndView("excelView","list", list);
	}
	
	
	
	// ���� ���ε� �Ͽ� DB�� ���� �ִ� ��.
	@RequestMapping(value="/upsave2", method=RequestMethod.POST)
	public ModelAndView save2(@ModelAttribute("svo") ShowVO vo,
			HttpServletRequest request) {
		String img_path = "resources\\imgfile";
		String r_path = request.getRealPath("/");
		String oriFn = vo.getMultipartFile().getOriginalFilename();
		StringBuffer path = new StringBuffer();
		path.append(r_path).append(img_path).append("\\");
		path.append(oriFn);
		File f = new File(path.toString());
		try {
			vo.getMultipartFile().transferTo(f);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		vo.setPath(vo.getMultipartFile().getOriginalFilename());
		dao.saveShow(vo);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:showlist?page=1");
		return mv;
	}
	
	@RequestMapping(value="/showlist", method=RequestMethod.GET)
	public ModelAndView list(int page, String searchType, String searchValue) {
		PageVO pageInfo = new PageVO();
		int rowsPerPage = 5;
		int pagesPerBlock = 5;
		int currentPage = page;
		int currentBlock = 0;
		if(currentPage % pagesPerBlock == 0) {
			currentBlock = currentPage / pagesPerBlock;
		}else {
			currentBlock = currentPage / pagesPerBlock + 1;
		}
		
		int startRow = (currentPage - 1) * rowsPerPage + 1;
		int endRow = currentPage * rowsPerPage;
		SearchVO svo = new SearchVO();
		svo.setBegin(String.valueOf(startRow));
		svo.setEnd(String.valueOf(endRow));
		svo.setSearchType(searchType);
		svo.setSearchValue(searchValue);
		System.out.println("searchType : " + searchType);
		System.out.println("searchValue : " + searchValue);
		
		// ��ü ���ڵ� ��
		int totalRows = dao.getTotalCount(svo);
		System.out.println("totalRows : " + totalRows);
		int totalPages = 0;
		
		// ��ü �������� ���ϴ� ����
		if(totalRows % rowsPerPage == 0) {
			totalPages = totalRows / rowsPerPage;
		}else {
			totalPages = totalRows / rowsPerPage + 1;
		}
		
		// ��ü ����� ���ϴ� ����
		int totalBlocks = 0;
		if(totalPages % pagesPerBlock == 0) {
			totalBlocks  = totalPages / pagesPerBlock;
		}else {
			totalBlocks = totalPages / pagesPerBlock + 1;
		}
		
		// PageVO�� setter�� ���� ����.
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setCurrentBlock(currentBlock);
		pageInfo.setRowsPerPage(rowsPerPage);
		pageInfo.setPagesPerBlock(pagesPerBlock);
		pageInfo.setStartRow(startRow);
		pageInfo.setEndRow(endRow);
		pageInfo.setTotalRows(totalRows);
		pageInfo.setTotalPages(totalPages);
		pageInfo.setTotalBlocks(totalBlocks);
		
		//---------------------------------------
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showlist");
		// Map�� Dao�� ����.
		List<ShowVO> list = dao.getListSearch(svo);
		
		// �������� ����Ʈ ���� ModelAndView�� ����ؼ� �� ����.
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("searchType", searchType);
		mv.addObject("searchValue", searchValue);
		mv.addObject("list", list);
		return mv;
	}
}
