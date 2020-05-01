package vanillaScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

public class TC013_Shishka {
	
	ChromeDriver driver;
	ChromeOptions option;
	DesiredCapabilities cap;
	WebDriverWait wait;
	Actions act;
	ExtentHtmlReporter html;
	ExtentReports reports;
	ExtentTest test;
	String testCaseName = "TC013_ShikshaTCs";
	 	
	
	 @BeforeTest
	  public void beforeTest() {
		 System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_83.exe");
		 option= new ChromeOptions();
		 option.addArguments("--disable-notifications");
		 cap = new DesiredCapabilities();
		 cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		 option.merge(cap);
		 driver= new ChromeDriver(option);
		 act  = new Actions(driver);
		 wait = new WebDriverWait(driver, 15);
	 }
	 
		 @BeforeClass
		 public void extentReportStart() {
			html = new ExtentHtmlReporter("./Reports/reports.html");
//			html.setAppendExisting(true);
			reports = new ExtentReports();
			reports.attachReporter(html);
			 
			 
		 }
	  
	 @BeforeMethod
	  public void startApp() {
		 driver.get("https://studyabroad.shiksha.com/");
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		 
	  }
  @Test
  public void shishkaTC() throws InterruptedException, IOException {
	  try {
		test = reports.createTest("TC013", testCaseName);
		  act.moveToElement(driver.findElementByXPath("//label[text()='Colleges ' and @for='sm3']")).perform();
		  driver.findElementByXPath("//a[text()='MS in Computer Science &Engg']").click();
		  System.out.println("Course Selected");
		  
		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='GRE']")));
		  driver.findElementByXPath("//p[text()='GRE']").click();
		  System.out.println("GRE: Selected");
		  Thread.sleep(3000);
		  
		  Select ddScore =  new Select(driver.findElementByClassName("score-select-field"));
		  ddScore.selectByValue("GRE--300--4");
		  System.out.println("Score: Selected");	  
		  
		  Thread.sleep(3000);
//	  wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//p[text()='Max 10 Lakhs']")));
		  driver.findElementByXPath("//p[text()='Max 10 Lakhs']").click();
		  System.out.println("Annual fee: Selected");
		  
		  Thread.sleep(3000);
		  driver.findElementByXPath("//a[text()='USA']//ancestor::label").click();
		  System.out.println("Country: Selected");
		  
		  Thread.sleep(3000);
		  wait.until(ExpectedConditions.elementToBeClickable(By.id("categorySorter")));
		  WebElement eleSort = driver.findElementById("categorySorter");
		  Select ddSort= new Select(eleSort);
		  ddSort.selectByValue("fees_ASC");
		  System.out.println("Sorted by Asc value");
		 
		  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='tuple-box']")));
			 Thread.sleep(5000);
			 List<WebElement> listTotalCollege = driver.findElementsByXPath("//div[@class='tuple-box']");
			 System.out.println("Total number of colleges listed up on selecting the filters: "+listTotalCollege.size());
			
			/* for (int i = 1; i <= listTotalCollege.size(); i++) { String publicUniv =
			 * driver.findElementByXPath("(//p[text()='Public university']/span)["+i+"]").
			 * getAttribute("class"); String scholar =
			 * driver.findElementByXPath("(//p[text()='Scholarship']/span)["+i+"]").
			 * getAttribute("class"); String accmd =
			 * driver.findElementByXPath("(//p[text()='Accommodation']/span)["+i+"]").
			 * getAttribute("class");
			 * System.out.println(publicUniv+"   "+scholar+"   "+accmd ); }
			 */

		 
		 Map<String,Float> maps = new HashMap<String, Float>();
		 for (int i = 1; i <= listTotalCollege.size(); i++) {		 
			 String publicUniv = driver.findElementByXPath("(//p[text()='Public university']/span)["+i+"]").getAttribute("class");
			 String scholar = driver.findElementByXPath("(//p[text()='Scholarship']/span)["+i+"]").getAttribute("class");
			 String accmd = driver.findElementByXPath("(//p[text()='Accommodation']/span)["+i+"]").getAttribute("class");
			 if(publicUniv.equalsIgnoreCase("tick-mark") && scholar.equalsIgnoreCase("tick-mark") && accmd.equalsIgnoreCase("tick-mark"))
			 {	String annualFee = driver.findElementByXPath("(//strong[text()=' 1st Year Total Fees']/following-sibling::p)["+i+"]").getText();
				float annualFeeInt = Float.parseFloat(annualFee.replaceAll("[^0-9.]", ""));
//			System.out.println(annualFeeInt); 
				maps.put(""+i,annualFeeInt);			 
			 }		 
		 }
		 
		 List<Float> listAnnualFee = new ArrayList<Float>();
		 System.out.println("Total number of colleges having all the 3 options: "+maps.size());
		 System.out.println("Displaying the annual fees of the colleges having all the 3 options---->");
		 	 for (Entry<String, Float> eachEle : maps.entrySet()) {		 
				 System.out.println(eachEle);
				listAnnualFee.add(eachEle.getValue());			 
  }
Collections.sort(listAnnualFee);
 Float lowestFloat = listAnnualFee.get(0);
 for (Entry<String, Float> eachEle : maps.entrySet()) {		 
		if(eachEle.getValue().equals(lowestFloat)) {
			String key = eachEle.getKey();
			driver.findElementByXPath("(//p[text()='Add to compare'])["+key+"]").click();
			break;
		}
}
 
System.out.println("Best College: Selected and the Annual fee is "+listAnnualFee.get(0));
Thread.sleep(3000);
driver.findElementByXPath("(//div[@id='recommendationDiv']//a)[1]").click();
System.out.println("Selected another college to compare");
driver.findElementByXPath("//strong[text()='Compare Colleges >']").click();
System.out.println("Compare button : clicked");

driver.findElementByXPath("//label[@for='year1']/span").click();
System.out.println("Year: Selected");

act.moveToElement(driver.findElementByXPath("//div[text()='Preferred Countries']")).click().perform();
//driver.findElementByXPath("//div[text()='Preferred Countries']").click();
driver.findElementByXPath("//label[contains(@for,'USA')]").click();
driver.findElementByLinkText("ok").click();
System.out.println("Preferred country: selected");

driver.findElementByXPath("//strong[text()='Masters']").click();
System.out.println("Study level: selected");
act.moveToElement(driver.findElementByXPath("//div[text()='Preferred Course']")).click().perform();
driver.findElementByXPath("//li[text()='MS']").click();
System.out.println("Preferred course: selected");
Thread.sleep(3000);

act.moveToElement(driver.findElementByXPath("//div[text()='Preferred Specialisations']")).click().perform();
driver.findElementByXPath("//li[text()='Computer Science & Engineering']").click();
System.out.println("Without selecting mandatory fields, clicking on signup Button");

driver.findElementById("signup").click();

System.out.println("Error messages are: ");


List<WebElement> listEleError = driver.findElementsByXPath("//div[contains(@id,'error') and contains(@style,'display: block')]/div[2]");
for (WebElement eachErrorele : listEleError) {
		System.out.println(eachErrorele.getText());	
}

File imgSource = driver.getScreenshotAs(OutputType.FILE);
File imgDest = new File("./Reports/"+testCaseName+".png");
FileUtils.copyFile(imgSource, imgDest);

test.pass(testCaseName+": Pass", MediaEntityBuilder.createScreenCaptureFromPath("./../Reports/"+testCaseName+".png").build());
	
	}
	  catch (Exception e) {
		  test.fail(e.getMessage());
		
	}
 }	
  
  
  
  @AfterMethod
  public void afterMethod() {	  
	  driver.close();
	  reports.flush();
  
  }
  
}/*
	 * 30/04/2020 ========== 1) Go to https://studyabroad.shiksha.com/ 2) Mouse over
	 * on Colleges and click MS in Computer Science &Engg under MS Colleges 3)
	 * Select GRE under Exam Accepted and Score 300 & Below 4) Max 10 Lakhs under
	 * 1st year Total fees, USA under countries 5) Select Sort By: Low to high 1st
	 * year total fees 6) Click Add to compare of the College having least fees with
	 * Public University, Scholarship and Accomadation facilities 7) Select the
	 * first college under Compare with similar colleges 8) Click on Compare
	 * College> 9) Select When to Study as 2021 10) Select Preferred Countries as
	 * USA 11) Select Level of Study as Masters 12) Select Preferred Course as MS
	 * 13) Select Specialization as "Computer Science & Engineering" 14) Click on
	 * Sign Up 15) Print all the warning messages displayed on the screen for missed
	 * mandatory fields
	 */