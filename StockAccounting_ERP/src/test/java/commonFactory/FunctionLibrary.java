package commonFactory;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.swing.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.AddHasCasting;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.propertyFileUtil;

public class FunctionLibrary {
public static WebDriver driver;
//Method for launch browser
public static WebDriver startBrowser() throws Throwable
{
	if (propertyFileUtil.getValueforkey("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}else if (propertyFileUtil.getValueforkey("Browser").equalsIgnoreCase("firefox")) 
	{
	    driver = new FirefoxDriver();	    		
	}else {
		System.out.println("Browser value is not matching");
	}
	return driver;
}
//method for launching url
public static void openApplication(WebDriver driver) throws Throwable
{
	driver.get(propertyFileUtil.getValueforkey("Url"));
}
//method for wait for element
public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String mywait)
{
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(mywait)));
	if (LocatorType.equalsIgnoreCase("name"))
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}else 
		if (LocatorType.equalsIgnoreCase("xpath"))
		{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));	
	}else 
		if (LocatorType.equalsIgnoreCase("id"))
		{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
}
//method for textboxs
public static void typeAction(WebDriver driver,String LocatorType,String LocatorValue,String testData)
{
	if(LocatorType.equalsIgnoreCase("name")) {
		driver.findElement(By.name(LocatorValue)).clear();
		driver.findElement(By.name(LocatorValue)).sendKeys(testData);
	}
	else if (LocatorType.equalsIgnoreCase("xpath")) {
		driver.findElement(By.xpath(LocatorValue)).clear();
		driver.findElement(By.xpath(LocatorValue)).sendKeys(testData);
	}
	else if (LocatorType.equalsIgnoreCase("id")) {
		driver.findElement(By.id(LocatorValue)).clear();
		driver.findElement(By.id(LocatorValue)).sendKeys(testData);
	}
}
//method for bottions,links,checkboxes,redio buttions and image
public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue)
{
	if (LocatorType.equalsIgnoreCase("name")) {
		driver.findElement(By.name(LocatorValue)).click();
	}
	else if (LocatorType.equalsIgnoreCase("xpath")) {
		driver.findElement(By.xpath(LocatorValue)).click();	
	}
	else if (LocatorType.equalsIgnoreCase("id")) {
		driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
	}
}
//method for valideting title
public static void validateTitle(WebDriver driver,String Exepcted_Title)
{
	String Actual_Title = driver.getTitle();
	try{
	Assert.assertEquals(Actual_Title, Exepcted_Title,"Title is not Matching");
       }catch (Throwable t ) 
	{
		System.out.println(t.getMessage());  
	}
}
//method for closing browser
public static void clickbrowser(WebDriver driver)
{
	driver.quit();
}
//method for MouseClick
public static void mouseClick(WebDriver driver) throws Throwable
{
	Actions ac = new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]"))).perform();
	Thread.sleep(3000);
	ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]"))).click().perform();
}
//method for category Table
public static void categoryTable(WebDriver drive,String Exp_Data) throws Throwable
{
	if(!driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-testbox"))).isDisplayed())
		//if search testbox panel is not displayed click search peanl buttion
		driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-panel"))).click();
	    //enter category name
	    driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-testbox"))).sendKeys(Exp_Data);
	    driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-button"))).click();
	    Thread.sleep(3000);
	    String Act_Data = driver.findElement(By.xpath("//table['@class=table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
	    System.out.println(Exp_Data+"    "+Act_Data);
	    Assert.assertEquals(Exp_Data, Act_Data,"category Name is not Matching");	    
}
//method for dropdone list
public static void dropDoneAction(WebDriver driver ,String LocatorType,String LocatorValue,String testData) throws Throwable
{
	if (LocatorType.equalsIgnoreCase("xpath")) 
	{
		int value = Integer.parseInt(testData);
		WebElement element = driver.findElement(By.xpath(LocatorValue));
		Select select = new Select(element);
		select.selectByIndex(value);	
	}
	else if (LocatorType.equalsIgnoreCase("id")) 
	{
		int value = Integer.parseInt(testData);
		WebElement element = driver.findElement(By.id(LocatorValue));
		Select select = new Select(element);
		select.selectByIndex(value);	
	}
	else if (LocatorType.equalsIgnoreCase("name")) 
	{
		int value = Integer.parseInt(testData);
		WebElement element = driver.findElement(By.name(LocatorValue));
		Select select = new Select(element);
		select.selectByIndex(value);	
	}
}
//method for captureStock
public static void captureStock(WebDriver driver,String LocatorType, String LocatorValue) throws Throwable
{
	String Expected_Num="";
	if (LocatorType.equalsIgnoreCase("xpath"))
	{
		Expected_Num = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	else if (LocatorType.equalsIgnoreCase("id"))
	{
		Expected_Num = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	else if (LocatorType.equalsIgnoreCase("name"))
	{
		Expected_Num = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	FileWriter fw = new FileWriter("./CaptureData/stocknum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(Expected_Num);
	bw.flush();
	bw.close();
}
//method for stockTable
public static void stockTable(WebDriver driver) throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/stocknum.txt");
	BufferedReader br = new BufferedReader(fr);
    String Exp_stockNumber = br.readLine();
    if(!driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-testbox"))).isDisplayed())
		//if search testbox panel is not displayed click search peanl buttion
		driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-panel"))).click();
	    //enter category name
	    driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-testbox"))).sendKeys(Exp_stockNumber);
	    driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-button"))).click();
	    Thread.sleep(3000);
	    String Act_StockNum = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	    System.out.println(Exp_stockNumber+"     "+Act_StockNum);
	    Assert.assertEquals(Exp_stockNumber, Act_StockNum,"stock number not matching");
}
//mathod for capturesupp
public static void capturesupp(WebDriver driver,String LocatorType,String LocatorValue) throws Throwable
{
	String Expected_Data = "";
	if (LocatorType.equalsIgnoreCase("xpath"))
	{
		Expected_Data = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}else if (LocatorType.equalsIgnoreCase("name"))
	{
		Expected_Data = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}else if (LocatorType.equalsIgnoreCase("id"))
	{
		Expected_Data = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}FileWriter fw = new FileWriter("./CaptureData/suppliernum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(Expected_Data);
	bw.flush();
	bw.close();
}
//method for supplierTable
public static void supplierTable(WebDriver driver) throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/suppliernum.txt");
	BufferedReader br = new BufferedReader(fr);
    String Exp_supplierNumber = br.readLine();
    if(!driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-testbox"))).isDisplayed())
		//if search testbox panel is not displayed click search peanl buttion
		driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-panel"))).click();
	    //enter category name
	    driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-testbox"))).sendKeys(Exp_supplierNumber);
	    driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-button"))).click();
	    Thread.sleep(3000);
	String Act_suppliernumber = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();  
	System.out.println(Exp_supplierNumber+"     "+Act_suppliernumber);
    Assert.assertEquals(Exp_supplierNumber, Act_suppliernumber,"Supplier number not Matching");
}

//method for capturecustomer
public static void capturecustomer(WebDriver driver,String LocatorType,String LocatorValue ) throws Throwable
{
	String Expected_num = "";
	if (LocatorType.equalsIgnoreCase("xpath"))
	{
		Expected_num = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}else if (LocatorType.equalsIgnoreCase("name"))
	{
		Expected_num = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}else if (LocatorType.equalsIgnoreCase("id"))
	{
		Expected_num = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}FileWriter fw = new FileWriter("./CaptureData/Customernum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(Expected_num);
	bw.flush();
	bw.close();	
}
//method for ConfirmTable
public static void ConfirmTable(WebDriver driver) throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/Customernum.txt");
	BufferedReader br = new BufferedReader(fr);
    String CustomerExp_Number = br.readLine();
    if(!driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-testbox"))).isDisplayed())
		//if search testbox panel is not displayed click search peanl buttion
		driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-panel"))).click();
	    //enter category name
	    driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-testbox"))).sendKeys(CustomerExp_Number);
	    driver.findElement(By.xpath(propertyFileUtil.getValueforkey("search-button"))).click();
	    Thread.sleep(3000);
	    String Act_Customernumber = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();  
		System.out.println( CustomerExp_Number+"     "+Act_Customernumber);
	    Assert.assertEquals( CustomerExp_Number, Act_Customernumber,"Supplier number not Matching");
}
//javatimestamp
public static String generatedata() 
{
	Date date = new Date();
    DateFormat df = new SimpleDateFormat("YYYY_MM_HH_DD_MM_SS");
	return df.format(date);
	
}





























}
