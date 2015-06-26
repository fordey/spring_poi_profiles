package serviceTester.core.services.impl;

import static serviceTester.core.constants.MIPScenarioConstants.MIP_LOAN_TYPE;
import static serviceTester.core.constants.MIPScenarioConstants.MIP_LOAN_TYPE_COLUMN;
import static serviceTester.core.constants.MIPScenarioConstants.SCENARIO_WORKSHEET;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import serviceTester.core.constants.MIPScenarioConstants;
import serviceTester.core.domain.MIPScenario;
import serviceTester.core.domain.TestRun;
import serviceTester.core.domain.xStream.MIP871Response;
import serviceTester.core.services.api.MIPFilterFieldsService;
import serviceTester.core.services.api.MipScenariosService;
import serviceTester.core.services.api.ResponseProcessService;
import serviceTester.core.services.api.WebServiceClient;
import serviceTester.mongo.repository.MIPServiceTesterRepository;
import serviceTester.utils.ExelUtils;
import serviceTester.utils.ModelConverter;
import serviceTester.core.domain.xStream.Field;

@Service
public class MIPScenariosServicesImpl implements MipScenariosService {

	private static final Logger logger = LoggerFactory
			.getLogger(MIPScenariosServicesImpl.class);
	private static final String XLS_EXT = ".xls";
	private static final String XLSX_EXT = ".xlsx";

	@Value(value = "${mipScenarios.file}")
	private String excelFile;

	@Value(value = "${mipScenarios.sourcelocation}")
	private String sourcelocation;

	@Value(value = "${mipScenarios.resultsfile}")
	private String resultsFileName;

	@Value(value = "${request.userid}")
	private String userId;

	@Value(value = "${mipScenarios.resultsfileext}")
	private String resultsFileExt;

	@Value(value = "${mipScenarios.resultslocation}")
	private String resultsLocation;

	@Autowired
	MIPServiceTesterRepository mipScenarioRepo;

	@Autowired
	WebServiceClient wsClient;

	@Autowired
	ResponseProcessService responseProcessor;

	@Autowired
	MIPFilterFieldsService filterService;

	@Override
	public int processTestScenarios(List<MIPScenario> mipScenarios)
			throws Exception {

		int processedCount = 0;

		TestRun testRun = new TestRun();

		logger.debug("Debuging the service requests ... logging messages");

		for (MIPScenario mipScenario : mipScenarios) {
			String rawRequest = generateScenarioRequest(mipScenario);
			logger.debug("message .... {}", rawRequest);

			String xmlString = wsClient.sendReceive(rawRequest);
			logger.debug("response .... {}", xmlString);

			MIP871Response response = (MIP871Response) responseProcessor
					.returnMIPObjectFromXML(xmlString);

			LinkedHashMap<String, String> reponseMap = extractResponseFields(response);

			mipScenario.setScenarioOutput(reponseMap);

			mipScenario.setScenarioOutOutAsString(ModelConverter
					.extractFieldsFromHashMap(reponseMap));

			processedCount++;
		}

		logger.debug("testRun completed ... saving results");

		testRun.setTestScenarios(mipScenarios);

		mipScenarioRepo.save(testRun);

		return processedCount;

	}

	private String generateScenarioRequest(MIPScenario scenario) {
		String requestPayLoad = buildRequestPayload(scenario);
		return requestPayLoad;
	}

	private LinkedHashMap<String, String> extractResponseFields(
			MIP871Response response) {
		LinkedHashMap<String, String> output = new LinkedHashMap<String, String>();

		for (Field f : response.getFields()) {
			output.put(f.getName(), f.getValue());
		}

		return output;
	}

	private String buildRequestPayload(MIPScenario scenario) {
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Request>");
		sb.append("<Log>P</Log>");
		sb.append("<ID>");
		sb.append("<version>1.0</version>");
		sb.append("<AppID>NBP</AppID>");
		sb.append("<AppName>?</AppName>");
		sb.append("<UsrID>");
		sb.append(userId);
		sb.append("</UsrID>");
		sb.append("<UnqID/>");
		sb.append("</ID>");
		sb.append("<regionCode>ROI</regionCode>");
		sb.append("<sourceNSC>931292</sourceNSC>");
		sb.append("<staffNumber>");
		sb.append(userId);
		sb.append("</staffNumber>");
		sb.append("<deviceId>NP</deviceId>");
		sb.append("<Transaction>MIPCalculation871</Transaction>");
		sb.append("<TransactionVersion>1</TransactionVersion>");
		sb.append("<MIPCalculation871>");
		sb.append(scenario.getScenarioInputAsString());
		sb.append("<FILLER/>");
		sb.append("</MIPCalculation871>");
		sb.append("</Request>");

		return sb.toString();

	}

	@Override
	public List<MIPScenario> getAllScenariosFromExcel() throws Exception {

		String fileName = sourcelocation + excelFile;

		logger.info("Accessing file ... {}", fileName);

		FileInputStream fis = new FileInputStream(new File(fileName));

		List<MIPScenario> scenarios = new ArrayList<MIPScenario>();

		Workbook wb = ExelUtils.getWorkBookFromFile(fis);

		Sheet scenarioSheet = ExelUtils.getMIPScenarioWorkSheetFromWb(wb);

		Iterator<Row> rows = scenarioSheet.rowIterator();

		try {

			while (rows.hasNext()) {
				Row row = rows.next();

				if (!endRow(row)) {

					if (!skipRow(row)) {
						logger.info("Processing Row number : {}",
								row.getRowNum());
						MIPScenario scenario = new MIPScenario();
						extractScenarioDataFromRow(scenario, row);
						scenarios.add(scenario);

					} else {
						logger.info("Skipped Row number: {}", row.getRowNum());
					}
				} else {
					break;
				}
			}
		} catch (Exception ex) {
			logger.error("error occurred processing excel data :  {}",
					ex.getMessage());

		} finally {
			if (fis != null) {
				fis.close();
			}

		}

		logger.info("{} scenario loaded", scenarios.size());

		return scenarios;

	}

	private void extractScenarioDataFromRow(MIPScenario scenario, Row row) {

		scenario.setDesc(ExelUtils.getCellValue(row,
				MIPScenarioConstants.MIP_TEST_DESC));

		LinkedHashMap<String, String> inputs = new LinkedHashMap<String, String>();

		inputs.put(MIPScenarioConstants.MIP_APP_1_SALARY_1, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_APP1_SALARY_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_APP_1_SALARY_2, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_APP1_SALARY_2_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_APP_1_SALARY_3, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_APP1_SALARY_3_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_APP_2_SALARY_1, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_APP2_SALARY_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_APP_2_SALARY_2, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_APP2_SALARY_2_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_APP_2_SALARY_3, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_APP2_SALARY_3_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_APPLIC_TYPE_1, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_APPLIC_TYPE_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_APPLIC_TYPE_2, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_APPLIC_TYPE_2_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_FIRST_TIME_BUYER_1, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_FIRST_TIME_BUYER_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_FIRST_TIME_BUYER_2, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_FIRST_TIME_BUYER_2_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_PURCHASE_PRICE, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_PURCHASE_PRICE_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_INTEREST_RATE, ExelUtils
				.getCellValue(row, MIPScenarioConstants.MIP_RATE_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_LOAN_AMOUNT, ExelUtils
				.getCellValue(row, MIPScenarioConstants.MIP_LOAN_AMOUNT_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_TERM, ExelUtils.getCellValue(row,
				MIPScenarioConstants.MIP_TERM_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_MORT_FORM, ExelUtils.getCellValue(
				row, MIPScenarioConstants.MIP_MORT_FORM_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_SELF_EMPLOYED_1, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_SELF_EMPLOYED_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_SELF_EMPLOYED_2, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_SELF_EMPLOYED_2_COLUMN));
		inputs.put(MIP_LOAN_TYPE,
				ExelUtils.getCellValue(row, MIP_LOAN_TYPE_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_PROPERTY_TYPE, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_PROPERTY_TYPE_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_NUM_DEPENDENTS_1, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_NUM_DEPENDENTS_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_NUM_DEPENDENTS_2, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_NUM_DEPENDENTS_2_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_PERS_LOANS_1,
				ExelUtils.getCellValue(row,
						MIPScenarioConstants.MIP_PERS_LOANS_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_PERS_LOANS_2,
				ExelUtils.getCellValue(row,
						MIPScenarioConstants.MIP_PERS_LOANS_2_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_CHILDCARE_1, ExelUtils
				.getCellValue(row, MIPScenarioConstants.MIP_CHILDCARE_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_CHILDCARE_2, ExelUtils
				.getCellValue(row, MIPScenarioConstants.MIP_CHILDCARE_2_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_OTHER_PAYMENTS_1, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_OTHER_PAYMENTS_1_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_OTHER_PAYMENTS_2, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_OTHER_PAYMENTS_2_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_ADJUST_LOAN_AMOUNT, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_ADJUST_LOAN_AMOUNT_COLUMN));
		inputs.put(MIPScenarioConstants.MIP_NDI_USE_REPAY, ExelUtils
				.getCellValue(row,
						MIPScenarioConstants.MIP_NDI_USE_REPAY_COLUMN));

		scenario.setScenarioInput(inputs);
	}

	public String exportMIPTestResultsExcel(List<MIPScenario> mipScenarios,
			boolean applyFilter) throws Exception {
		String fileName = createFileName();
		List<MIPScenario> filteredScenarios = null;

		logger.debug("Excel filename .. {}", fileName);

		Workbook wb;

		// check for which version the ext is set for
		if (checkIsXMLExt()) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}

		List<String> filterNames = null;
		if (applyFilter) {
			filteredScenarios = filterAllScenarios(mipScenarios);
		}

		boolean success = ExelUtils.exportSheetToExcel(filteredScenarios, wb,
				fileName);

		if (success) {
			return fileName;
		} else {
			return "error occurred";
		}

	}

	private List<MIPScenario> filterAllScenarios(List<MIPScenario> completeList) {
		List<MIPScenario> filteredScenarios = new ArrayList<MIPScenario>();
		for (MIPScenario s : completeList) {
			MIPScenario f = filterService.returnFilteredMIP(s);
			filteredScenarios.add(f);
		}
		return filteredScenarios;

	}

	private boolean checkIsXMLExt() {
		return (resultsFileExt.equalsIgnoreCase(XLS_EXT) ? false : true);
	}

	private String createFileName() {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy_MM_dd_hhmmss");
		String ts = dt.format(new Date());

		StringBuilder sb = new StringBuilder(resultsLocation);
		sb.append(resultsFileName);
		sb.append(ts);
		sb.append(resultsFileExt);

		return sb.toString();
	}

	private boolean endRow(Row row) {
		boolean endRow = false;

		Cell cell = row.getCell(0);

		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK
				|| blankFormula(cell)) {
			endRow = true;
		}

		return endRow;
	}

	private boolean blankFormula(Cell cell) {
		boolean result = false;
		if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			String text = cell.getStringCellValue();

			if (text.length() == 0 || text.isEmpty()) {
				result = true;
			}

		}

		return result;

	}

	private boolean skipRow(Row row) {
		boolean skip = false;

		Cell cell = row.getCell(0);

		String cellValue = cell.getStringCellValue();

		skip = Pattern
				.matches(MIPScenarioConstants.REG_EXP_SKIP_ROW, cellValue);

		return skip;
	}

}
