package serviceTester.utils;

import static serviceTester.core.constants.MIPScenarioConstants.SCENARIO_WORKSHEET;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import serviceTester.core.constants.MIPScenarioConstants;
import serviceTester.core.domain.AbstractScenario;
import serviceTester.core.services.api.MIPFilterFieldsService;

public class ExelUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ExelUtils.class);
	
	@Autowired
	MIPFilterFieldsService filterService;

	
	//overloaded method was previously used
	public static Workbook getWorkBookFromFile(File excelFile) throws Exception{
		return WorkbookFactory.create(excelFile);
	}
	
	public static Workbook getWorkBookFromFile(FileInputStream fis) throws Exception{
		return WorkbookFactory.create(fis);
	}
	
	public static Sheet getMIPScenarioWorkSheetFromWb(Workbook wb) throws Exception{
		
		Sheet sheet = wb.getSheetAt(SCENARIO_WORKSHEET);
		
		
		return sheet;
	}
	
		
	public static String getCellValue(Row row, int cellNum){
		
		Cell c = row.getCell(cellNum);
		
		String result = new String();
		
		try{
			switch(c.getCellType()){
			case Cell.CELL_TYPE_STRING:
				result = c.getStringCellValue();
				break;	
			case Cell.CELL_TYPE_BLANK:
				result = null;
				break;
			case Cell.CELL_TYPE_NUMERIC:
				Double d = c.getNumericCellValue();
				int i  = d.intValue();
				result = String.valueOf(i);
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = c.getStringCellValue();
				break;
			default:
				throw new IllegalArgumentException(String.format("Unsupported Format In Row %d Cell %d", row.getRowNum(), cellNum));
			
			}
		}catch(Exception ex){
			logger.error("Error occured ExelUtils :: getCellValue row : {} cell {}", row.getRowNum(), cellNum);
			
			result = null;
		}
				
		return result;
	}
	
	
	public static boolean exportSheetToExcel(List<? extends AbstractScenario> scenarios, Workbook wb, String fileName)  throws Exception {
		Map<String, CellStyle> styles = createStyles(wb);
		
		Sheet ws = wb.createSheet(MIPScenarioConstants.RESULTS_WORKSHEET);
		
		//set print setup to landscape
		PrintSetup printSetup = ws.getPrintSetup();
		printSetup.setLandscape(true);
		
		//title Row
		//Row titleRow = ws.createRow(0);
		//createTitleRow(ws, titleRow, styles);     
	     
		if(scenarios !=null && scenarios.size() > 0){
			//create heading rows -use first element
			Row headingsRow = ws.createRow(1);
			createHeadingRow(ws, headingsRow, styles, scenarios.get(0));
	
			createDetailRows(ws, styles, scenarios);
		
		}
	     
		FileOutputStream os = new FileOutputStream(fileName);
		wb.write(os);
		os.close();
		
		return true;
	}
	
	private static void createDetailRows(Sheet ws, Map<String, CellStyle> styles, List<? extends AbstractScenario> scenarios){
		
		logger.debug("creating detail rows....");
		
		int rowNumber = 2;
		int cellCount = 0;
		for(AbstractScenario scenario : scenarios){
			LinkedHashMap<String, String>inputs = scenario.getScenarioInput();
			LinkedHashMap<String, String>outputs = scenario.getScenarioOutput();
			
			Row detailRow = ws.createRow(rowNumber);
			
			cellCount = 0;
			Cell descCell = detailRow.createCell(cellCount);
			descCell.setCellValue(scenario.getDesc());
			descCell.setCellStyle(styles.get("desc-cell"));
			cellCount++;
			
			//loop through input fields
			for(Map.Entry<String, String> entry: inputs.entrySet()){
				Cell detailCell = detailRow.createCell(cellCount);
				if(entry.getValue() != null){
					detailCell.setCellValue(entry.getValue());
				}else{
					detailCell.setCellValue("null");
				}
				detailCell.setCellStyle(styles.get("detail-cell"));
				cellCount++;
			}
		
			//loop through the output fields
			if(outputs!=null){
				for(Map.Entry<String, String> entry: outputs.entrySet()){
					Cell detailCell = detailRow.createCell(cellCount);
					detailCell.setCellValue(entry.getValue());
					detailCell.setCellStyle(styles.get("detail-cell"));
					cellCount++;
				}
			}
			
			rowNumber++;
		}
		
		logger.debug("details rows created autosizing columns");
		autosizeAllColumns(ws, cellCount);
		
	}
	
	private static void autosizeAllColumns(Sheet ws, int cellCount){
		for(int i=0; i<cellCount;i++){
			ws.autoSizeColumn(i, true);
		}
	}
	
	
	private static void createHeadingRow(Sheet ws, Row headingsRow, Map<String, CellStyle> styles, AbstractScenario firstScenario){
		
		logger.debug("Creating heading rows ....");
		
		LinkedHashMap<String, String> inputs = firstScenario.getScenarioInput();
		LinkedHashMap<String, String> outputs = firstScenario.getScenarioOutput();
				
		headingsRow.setHeightInPoints(40);
		
		int cellCount = 1; //leave space for desc in details
		//input headings
		for(Map.Entry<String, String> entry: inputs.entrySet()){
			Cell headerCell = headingsRow.createCell(cellCount);
			headerCell.setCellValue(entry.getKey());
			headerCell.setCellStyle(styles.get("header-input"));
			cellCount++;
		}
		
		//output headings after the inputs
		if(outputs!=null){
			for(Map.Entry<String, String> entry: outputs.entrySet()){
				Cell headerCell = headingsRow.createCell(cellCount);
				headerCell.setCellValue(entry.getKey());
				headerCell.setCellStyle(styles.get("header-output"));
				logger.debug("Heading Cell {} created ...", entry.getKey());
				cellCount++;
			}
		}
		logger.debug("Heading rows created ....");
	}
	
	
	@SuppressWarnings("unused")
	private static void createTitleRow(Sheet ws, Row titleRow, Map<String, CellStyle> styles){
		titleRow.setHeightInPoints(45);
    	Cell titleCell = titleRow.createCell(2);
    	titleCell.setCellValue(MIPScenarioConstants.RESULTS_WORKSHEET_TITLE);
    	titleCell.setCellStyle(styles.get("title"));
    	ws.addMergedRegion(CellRangeAddress.valueOf("$C$1:$P$1"));
		
	}
	
	
	
	private static Map<String,CellStyle> createStyles(Workbook wb){
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style;
		
		//title font
		Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        
        //title cell style
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);
		
		//headers font
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short)11);
        headerFont.setColor(IndexedColors.WHITE.index);
        
        //header-input style
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setWrapText(true);
        styles.put("header-input", style);
		
       //header-output style
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setWrapText(true);
        styles.put("header-output", style);
        
        //detail cell format
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("detail-cell", style);       
        
        //desc cell
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("desc-cell", style);    
        
        //detail amount cell format
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        
        styles.put("amountAsDecimal", style); 
        
		return styles;
		
	}
	
	
}
