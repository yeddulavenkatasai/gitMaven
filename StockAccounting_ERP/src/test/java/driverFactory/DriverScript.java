package driverFactory;

import java.util.Iterator;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFactory.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath ="./FileInput/DataEngine.xlsx";
String outputpath = "./FileOutput/HybridResults.xlsx";
String TestCases = "MasterTestCases";
ExtentReports report;
ExtentTest logger;
public void startTest()throws Throwable
{
	String Module_Status = "";
//create reference object to cell all excel methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
//iterate all rows in testcaese sheet
	for (int i = 1; i <=xl.rowcount(TestCases); i++)
	{
		if (xl.getcelldata(TestCases, i, 2).equalsIgnoreCase("Y"))
		{
			//readinge correspoding sheet or test case
			String TCModule = xl.getcelldata(TestCases, i, 1);
			report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generatedata()+".html");
			logger = report.startTest(TCModule);
			//iterate all rows in TCModle
			for (int j = 1; j <=xl.rowcount(TCModule); j++) 
			{
				//read cell from TCModule
				String Description = xl.getcelldata(TCModule, j, 0);
				String Object_Type = xl.getcelldata(TCModule, j, 1);
				String Locator_Type = xl.getcelldata(TCModule, j, 2);
				String Locator_Value = xl.getcelldata(TCModule, j, 3);
				String TestData = xl.getcelldata(TCModule, j, 4);
				String Status = xl.getcelldata(TCModule, j, 5);
				try {
					if (Object_Type.equalsIgnoreCase("startBrowser")) 
					{
						driver = FunctionLibrary.startBrowser();
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("openApplication")) 
					{
						FunctionLibrary.openApplication(driver);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(driver, Locator_Type,Locator_Value,TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("typeAction")) 
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("clickAction"))
					{
					    FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
					    logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("validateTitle")) 
					{
						FunctionLibrary.validateTitle(driver, Module_Status);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("clickbrowser")) 
					{
						FunctionLibrary.clickbrowser(driver);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("mouseClick")) 
					{
						FunctionLibrary.mouseClick(driver);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("categoryTable")) 
					{
						FunctionLibrary.categoryTable(driver, TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("dropDoneAction"))
					{
						FunctionLibrary.dropDoneAction(driver, Locator_Type, Locator_Value, TestData);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("captureStock")) 
					{
						FunctionLibrary.captureStock(driver, Locator_Type,Locator_Value);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("stockTable")) 
					{
						FunctionLibrary.stockTable(driver);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("capturesupp")) 
					{
						FunctionLibrary.capturesupp(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO,Description);
					}
					if (Object_Type.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable(driver);
						logger.log(LogStatus.INFO,Description);
					}
					//write as pass into TCModule
					xl.setcelldata(TCModule, j, 5,"Pass",outputpath);
					logger.log(LogStatus.PASS, Description);
					Module_Status = "True";
				} catch (Exception e) 
				{
					System.out.println(e.getMessage());
					//write as Fail into TCModule
					xl.setcelldata(TCModule, j, 5,"Fail", outputpath);
					logger.log(LogStatus.FAIL, Description);
					Module_Status = "False";
				}
				if (Module_Status.equalsIgnoreCase("True"))
				{
					xl.setcelldata(TestCases, i, 3, "Pass", outputpath);
				}else {
					xl.setcelldata(TestCases, i, 3, "Fail", outputpath);
				}
				report.endTest(logger);
				report.flush();
			}
		}
		else {
			//write Blocked which testcases are flaged to N
			xl.setcelldata(TestCases, i, 3 ,"Blocked",outputpath);
		}		
	}
	
	
}
}
