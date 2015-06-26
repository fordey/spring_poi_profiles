package serviceTester.core.constants;

public class MIPScenarioConstants {

	public MIPScenarioConstants(){
		//disabling default constructor
	}
	
	public static final String COLLECTION_MIP = "mipScenariosTestRun";
	public static final String COLLECTION_FILTER = "mipFilterFields";
	
	public static final int SCENARIO_WORKSHEET = 1;
	public static final int MIP_SCENARIOS_STARTING_ROW = 3;
		
	//column names for input
	public static final String MIP_NSC="nsc";
	public static final String MIP_ACCOUNTNUMBER="accountNumber";
	public static final String MIP_LOAN_TYPE = "MIP_LOAN_TYPE";
	public static final String MIP_PROPERTY_TYPE = "MIP_PROPERTY_TYPE";
	public static final String MIP_FIRST_TIME_BUYER_1 = "MIP_FIRST_TIME_BUYER_1";
	public static final String MIP_FIRST_TIME_BUYER_2 = "MIP_FIRST_TIME_BUYER_2";
	public static final String MIP_APPLIC_TYPE_1 = "MIP_APPLIC_TYPE_1";
	public static final String MIP_APPLIC_TYPE_2 = "MIP_APPLIC_TYPE_2";
	public static final String MIP_SELF_EMPLOYED_1 = "MIP_SELF_EMPLOYED_1";
	public static final String MIP_SELF_EMPLOYED_2 = "MIP_SELF_EMPLOYED_2";
	public static final String MIP_NUM_DEPENDENTS_1 = "MIP_NUM_DEPENDENTS_1";
	public static final String MIP_NUM_DEPENDENTS_2 = "MIP_NUM_DEPENDENTS_2";
	
	public static final String MIP_APP_1_SALARY_1 = "MIP_SALARY_1";
	public static final String MIP_APP_1_SALARY_2 = "MIP_SALARY_2";
	public static final String MIP_APP_1_SALARY_3 = "MIP_SALARY_3";
	
	public static final String MIP_APP_2_SALARY_1 = "MIP_SALARY_4";
	public static final String MIP_APP_2_SALARY_2 = "MIP_SALARY_5";
	public static final String MIP_APP_2_SALARY_3 = "MIP_SALARY_6";
	
	
	public static final String MIP_SALARY_2 = "MIP_SALARY_2";
	public static final String MIP_PERS_LOANS_1 = "MIP_PERS_LOANS_1";
	public static final String MIP_PERS_LOANS_2 = "MIP_PERS_LOANS_2";
	
	public static final String MIP_CHILDCARE_1 = "MIP_CHILDCARE_1";
	public static final String MIP_CHILDCARE_2 = "MIP_CHILDCARE_2";
	
	public static final String MIP_OTHER_PAYMENTS_1 = "MIP_OTHER_PAYMENTS_1";
	public static final String MIP_OTHER_PAYMENTS_2 = "MIP_OTHER_PAYMENTS_2";
	
	public static final String MIP_PURCHASE_PRICE = "MIP_PURCHASE_PRICE";
	public static final String MIP_LOAN_AMOUNT = "MIP_LOAN_AMOUNT";
	public static final String MIP_TERM = "MIP_TERM";
	public static final String MIP_ADJUST_LOAN_AMOUNT = "MIP_ADJUST_LOAN_AMOUNT";
	public static final String MIP_NDI_USE_REPAY = "MIP_NDI_USE_REPAY";
	public static final String MIP_INTEREST_RATE = "MIP_INTEREST_RATE";
	public static final String MIP_MORT_FORM = "MIP_MORT_FORM";
	
	
	
	//column location
	public static final int MIP_TEST_DESC = 0;
	public static final int MIP_LOAN_TYPE_COLUMN = 1;
	public static final int MIP_PROPERTY_TYPE_COLUMN = 2;
	public static final int MIP_FIRST_TIME_BUYER_1_COLUMN = 3;
	public static final int MIP_FIRST_TIME_BUYER_2_COLUMN = 4;
	public static final int MIP_APPLIC_TYPE_1_COLUMN = 5;
	public static final int MIP_APPLIC_TYPE_2_COLUMN = 6;
	public static final int MIP_SELF_EMPLOYED_1_COLUMN = 7;
	public static final int MIP_SELF_EMPLOYED_2_COLUMN = 8;
	public static final int MIP_NUM_DEPENDENTS_1_COLUMN = 9;
	
	public static final int MIP_NUM_DEPENDENTS_2_COLUMN = 10;
	
	public static final int MIP_APP1_SALARY_1_COLUMN = 11;
	public static final int MIP_APP2_SALARY_1_COLUMN = 12;
	
	public static final int MIP_APP1_SALARY_2_COLUMN = 13;
	public static final int MIP_APP2_SALARY_2_COLUMN = 14;
	
	public static final int MIP_APP1_SALARY_3_COLUMN = 15;
	public static final int MIP_APP2_SALARY_3_COLUMN = 16;
	
	
	public static final int MIP_PERS_LOANS_1_COLUMN = 17;
	public static final int MIP_PERS_LOANS_2_COLUMN = 18;
	
	
	public static final int MIP_CHILDCARE_1_COLUMN = 19;
	public static final int MIP_CHILDCARE_2_COLUMN = 20;
	
	public static final int MIP_OTHER_PAYMENTS_1_COLUMN = 21;
	public static final int MIP_OTHER_PAYMENTS_2_COLUMN = 22;
	
	
	public static final int MIP_PURCHASE_PRICE_COLUMN = 23;
	public static final int MIP_LOAN_AMOUNT_COLUMN = 24;
	public static final int MIP_TERM_COLUMN = 25;
	public static final int MIP_RATE_COLUMN = 26;
	public static final int MIP_ADJUST_LOAN_AMOUNT_COLUMN = 27;
	public static final int MIP_NDI_USE_REPAY_COLUMN = 28;
	public static final int MIP_MORT_FORM_COLUMN = 29;

	//markers on file to signify gaps -- on column 1
	public static final String REG_EXP_SKIP_ROW = "^TEST TYPE.*|Group-Base.*";
	
	public static final String RESULTS_WORKSHEET = "testResults";
	public static final String RESULTS_WORKSHEET_TITLE = "TEST RUN RESULTS";
		
	
	
	
	
	
}
