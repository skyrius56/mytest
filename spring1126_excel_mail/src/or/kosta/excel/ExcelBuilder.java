package or.kosta.excel;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import or.kosta.vo.ShowVO;


@SuppressWarnings("deprecation")
public class ExcelBuilder extends AbstractExcelView{
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// HSSF - ����ũ�� ����Ʈ�� �������� Ŭ����
		// DispatcherServlet�� ���ؼ� Model�� �޽��ϴ�.
		// ${listBooks}
		List<ShowVO> list = (List<ShowVO>) model.get("list");
		
		//poi����
		HSSFSheet sheet = workbook.createSheet("Show Lists");
		sheet.setDefaultColumnWidth(30);
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		HSSFRow header = sheet.createRow(0);
				
		// ������ �� Ÿ��Ʋ
		String[] str = {"��ȣ", "�ڵ�", "�̹���", "�ۼ���", "����", "��¥"};
		for(int i=0; i<str.length; i++) {
			header.createCell(i).setCellValue(str[i]);
			header.getCell(i).setCellStyle(style);
		}
		
		int rowCount = 1;
		// ���� ������ ä��
		for(ShowVO vo : list) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(vo.getNum());
			aRow.createCell(1).setCellValue(vo.getGrpcode());
			aRow.createCell(2).setCellValue(vo.getPath());
			aRow.createCell(3).setCellValue(vo.getWriter());
			aRow.createCell(4).setCellValue(vo.getPrice());
			aRow.createCell(5).setCellValue(vo.getRegdate());
		}
		// ���� ��ü�κ��� �ٿ�ε� ���� Ÿ�԰� �����̸��� �����Ѵ�.
		response.setContentType("Application/Msexcel");
		response.setHeader("Content-Disposition",
				"attachment; filename=board_excel.xls");
	}
}
