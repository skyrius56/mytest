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
		//applicationContext 객체를 request로 부터 얻어냄
		ServletContext context = request.getServletContext();
		
		//업로드된 경로 얻음
		String path = session.getServletContext().getRealPath("/resources/imgfile/")+fileName;
		
		System.out.println("업로드된 경로 : " + path);
		// 그 경로로 파일객체를 생성
		File downloadFile = new File(path);
		System.out.println("DownLoadFile : " + downloadFile.exists());
		
		// FileInputStream으로 읽어옴
		FileInputStream fi =  new FileInputStream(downloadFile);
		
		// 요청객체로부터 연결된 브라우저의 마임타입을 얻어냄
		String mimeType = context.getMimeType(path);
		
		//만약 마임타임값이 없으면 그냥 디폴트로 스트림으로 연결
		if(mimeType == null) {
			mimeType = "application/octet-stream";
		}
		
		// 지정된 마임타입 세팅
		response.setContentType(mimeType);
		
		// 다운로드될 파일의 길이 세팅
		response.setContentLength((int) downloadFile.length());
		
		// 다운로드 type을 설정함
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", 
				downloadFile.getName());
		
		// 위의 다운로드 타입의 정보를 헤더로 설정
		response.setHeader(headerKey, headerValue);
		
		// 브라우저로 부터 스트림을 연결
		OutputStream outputStream = response.getOutputStream();
		
		// 버퍼를 끼워서 빠르게 전달 목적
		byte[] buffer = new byte[BUFFER_SIZE];
		
		// 이제 브라우저로 보내고, 자원 닫으면 끝!
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
	
	
	
	// 파일 업로드 하여 DB에 값을 넣는 모델.
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
		
		// 전체 레코드 수
		int totalRows = dao.getTotalCount(svo);
		System.out.println("totalRows : " + totalRows);
		int totalPages = 0;
		
		// 전체 페이지를 구하는 공식
		if(totalRows % rowsPerPage == 0) {
			totalPages = totalRows / rowsPerPage;
		}else {
			totalPages = totalRows / rowsPerPage + 1;
		}
		
		// 전체 블록을 구하는 공식
		int totalBlocks = 0;
		if(totalPages % pagesPerBlock == 0) {
			totalBlocks  = totalPages / pagesPerBlock;
		}else {
			totalBlocks = totalPages / pagesPerBlock + 1;
		}
		
		// PageVO에 setter로 갑을 주입.
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
		// Map을 Dao에 주입.
		List<ShowVO> list = dao.getListSearch(svo);
		
		// 페이지와 리스트 값을 ModelAndView를 사용해서 값 전달.
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("searchType", searchType);
		mv.addObject("searchValue", searchValue);
		mv.addObject("list", list);
		return mv;
	}
}
