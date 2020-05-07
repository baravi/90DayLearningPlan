package phaseTwoEvaluation;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.FileUtil;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class TC02_AzureMicrosoft {
	public static ChromeOptions options;
	public static ChromeDriver driver;
	public static WebDriverWait wait;
	public static ExtentHtmlReporter html;
	public static ExtentReports extent;
	public static String url = "https://azure.microsoft.com/en-in/";
	
	@BeforeTest
	  public void beforeTest() {
		 html = new ExtentHtmlReporter("./Reports/reports.html");
			html.setAppendExisting(true);
			extent = new ExtentReports();
			extent.attachReporter(html);
	  }	
	 @BeforeClass
	  public void beforeClass() {
		 System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_83.exe");
			options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
			options.merge(cap);
			driver = new ChromeDriver(options);
			wait = new WebDriverWait(driver, 30);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 
	  }	
	 
	 @BeforeMethod
	  public void startApp() {
		 driver.get(url);
		 System.out.println(url+": launched successfully");
		 
	  }
	
  @Test
  public void f() throws InterruptedException, IOException {
	  ExtentTest testLevel = extent.createTest("TC002", "Azure_MicroSoft_ValidationPhase2-");
	  try {
		driver.findElementById("navigation-pricing").click();
		  driver.findElementByXPath("//a[contains(text(),'Pricing calculator')]").click();
		  System.out.println("Pricing calculator : Selected");
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Containers') and @data-event-property ='containers']")));
		  driver.findElementByXPath("//button[contains(text(),'Containers') and @data-event-property ='containers']").click();
		  driver.findElementByXPath("(//button[contains(@title,'Container Instances')]//span[contains(text(),'Container Instances')])[2]").click();
		  System.out.println("Container instance : Selected");
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-module-loc")));
		  driver.findElementById("new-module-loc").click();
		  System.out.println("Container Instance Added View : selected");
		  
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("region")));
		  Select ddRegion = new Select(driver.findElementByName("region"));
		  ddRegion.selectByVisibleText("South India");
		  System.out.println("Region :Selected");
		  driver.findElementByName("seconds").sendKeys("",Keys.BACK_SPACE);
		  driver.findElementByName("seconds").sendKeys("",Keys.DELETE);
		  Thread.sleep(2000);
		  driver.findElementByName("seconds").sendKeys("80000");
		  Select ddMemory = new Select(driver.findElementByName("memory"));
		  ddMemory.selectByVisibleText("4 GB");
		  System.out.println("Memory :Selected");
		  driver.findElementByXPath("//span[text()='Show Dev/Test Pricing']").click();
		  System.out.println("Show Dev/Test Pricing : Selected");
		  
		  driver.findElementByXPath("//select[@class='select currency-dropdown']//option[@value='INR']").click();
		  System.out.println("Currency INR  :Selected");
		  String estimatedMnthlyPrice = driver.findElementByXPath("((//div[@class='column large-3 text-right total']/div)[2]//span)[2]").getText();
		  System.out.println("Estimated monthly price: "+estimatedMnthlyPrice);
		  driver.findElementByXPath("//button[text()='Export']").click();
		  System.out.println("Button Export: clicked");
		  Thread.sleep(10000);
		  XSSFWorkbook book = new XSSFWorkbook("C:\\Users\\BaraviPriya\\Downloads\\ExportedEstimate.xlsx");
		  System.out.println("File downloaded successfully"); 	  
//	  WebElement element = driver.findElementByXPath("(//ul[@class='calc-tablist']/li)[2]");
		  JavascriptExecutor js = (JavascriptExecutor) driver;			  
		  js.executeScript("window.scrollTo(0,document.body.scrollTop)"); 
		  Thread.sleep(2000);
		  driver.findElementByXPath("(//ul[@class='calc-tablist']/li)[2]").click();
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='CI/CD for Containers']")));
		  driver.findElementByXPath("//span[text()='CI/CD for Containers']").click();
		  System.out.println("CI/CD for Containers : selected");
		  JavascriptExecutor jsd = (JavascriptExecutor) driver;			  
		  jsd.executeScript("window.scrollBy(0,500)"); 
		  Thread.sleep(3000);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add to estimate']")));
		  driver.findElementByXPath("//button[text()='Add to estimate']").click();
		  System.out.println("Add to estimate : selected");	  
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Estimate added!']")));
		  driver.findElementByXPath("//select[@class='select currency-dropdown']//option[@value='INR']").click();
		  System.out.println("Currency INR  :Selected");
		  driver.findElementByXPath("//span[text()='Show Dev/Test Pricing']").click();
		  System.out.println("Show Dev/Test Pricing : Selected");
		  driver.findElementByXPath("//button[text()='Export']").click();
		  System.out.println("Button Export: clicked");
		  Thread.sleep(10000);
		  XSSFWorkbook book1 = new XSSFWorkbook("C:\\Users\\BaraviPriya\\Downloads\\ExportedEstimate (1).xlsx");
		  System.out.println("File downloaded successfully");
		  
		  testLevel.pass("TC-Azure_MicroSoft_ValidationPhase2 : Pass");
	} catch (Exception e) {
		File source = driver.getScreenshotAs(OutputType.FILE);
		File dest = new File("./Reports/images/TC02_AzureMircosoft.png");
		FileUtils.copyFile(source, dest);
		testLevel.fail("TC-Azure_MicroSoft_ValidationPhase2 : Fail"+e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath("./Reports/images/TC02_AzureMircosoft.png").build());
		
			}
	  
  }
 

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

 

  @AfterClass
  public void afterClass() {
	  extent.flush();

  }

}
