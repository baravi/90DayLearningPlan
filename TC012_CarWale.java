package vanillaScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import gherkin.ParserException.UnexpectedEOFException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

public class TC012_CarWale {
	
	ChromeDriver driver;
	ChromeOptions option;
	DesiredCapabilities cap;
	WebDriverWait wait;
	Select dd;
	Actions act;
	int temp;
	int k=0;
	boolean flag = false;
	Map<String, String> map;
	ExtentHtmlReporter html;
	 ExtentReports reports;


@BeforeTest
public void beforeTest() {
		  System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_83.exe");
		  option = new ChromeOptions();
		  option.addArguments("--disable-notifications");
		  cap = new DesiredCapabilities();
		  cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		  option.merge(cap);	  
		  driver = new ChromeDriver(option);
		  wait =  new WebDriverWait(driver, 20);
		  act = new Actions(driver);
		  map = new LinkedHashMap<String, String>();
		  html = new ExtentHtmlReporter("./Reports/reports.html");
		  reports = new ExtentReports();
		  reports.attachReporter(html);
		  
	  }

@BeforeMethod
public void startApp() {	  
		  driver.get("https://www.carwale.com/");
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	  
	  }
	  
	
@Test
public void carWaleTCs() throws InterruptedException, IOException {
	ExtentTest test = reports.createTest("TC01", "CarWaleTestCases");
	  driver.findElementByXPath("//li[@data-tabs= 'usedCars']").click();
	  driver.findElementById("usedCarsList").sendKeys("Chennai");
	  Thread.sleep(2000);
	  driver.findElementById("usedCarsList").sendKeys("",Keys.TAB);	  
	  driver.findElementById("minInput").sendKeys("8",Keys.TAB);
	  driver.findElementById("maxInput").sendKeys("12",Keys.TAB);
	  driver.findElementById("btnFindCar").click();
	  Thread.sleep(3000);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("CarsWithPhotos")));
	  driver.findElementByName("CarsWithPhotos").click();
	  System.out.println("Car with Photo Filter option: Selected");
	  Thread.sleep(2000);
	 driver.findElementByXPath("((//div[@class='filter-set content-inner-block-10 position-rel'])[2]//span)[1]").click();
	  Thread.sleep(3000);
	  driver.findElementById("tags").sendKeys("Hyundai");
	  Thread.sleep(5000);	  
	  try {
		driver.findElementByXPath("//span[contains(text(),' Hyundai ')]").click();
	} catch (Exception e1) {
		act.sendKeys(Keys.PAGE_DOWN);
		driver.findElementByXPath("//span[contains(text(),' Hyundai ')]").click();
	}
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ul[@class='rootUl']//span)[1]")));
	  driver.findElementByXPath("//span[text()='Creta']").click();
	  Thread.sleep(2000);
	  driver.findElementByXPath("(//div[@class='manufacture-box content-inner-block-10']//span)[1]").click();
	  System.out.println("Car - Hyundai - Creta: Selected");
	  Thread.sleep(3000);
	  driver.findElementByXPath("(//div[@name='fuel']//span)[1]").click();
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@name='fuel']//li)[1]")));
	  driver.findElementByXPath("(//div[@name='fuel']//li)[1]").click();
	  System.out.println("Fuel type as Petrol: Selected");
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sort")));
	  dd = new Select(driver.findElementById("sort"));
	  dd.selectByValue("2");
	  System.out.println("Sorting Low to High KM: Selected");
	  Thread.sleep(5000);
	  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class='card-detail__vehicle-data']//td[1]/span")));
	  List<WebElement> elesTotalCarsList = driver.findElementsByXPath("//table[@class='card-detail__vehicle-data']//td[1]/span");
	  List<Integer> intList = new ArrayList<Integer>();
	  System.out.println("Total number of cars listed up on matching the filters: "+elesTotalCarsList.size());
	  driver.findElementByLinkText("Don't show anymore tips").click();
  
	 for (WebElement eachEle : elesTotalCarsList) {
	 intList.add(Integer.parseInt(eachEle.getText().replaceAll("\\D", "")));
	 
	 	}
	 

	 temp = intList.get(1);
	 for(int j=2;j<intList.size();j++) {		 
		 if(temp<intList.get(j)) {
		 }else {
			 temp= intList.get(j); 
			 System.out.println("Sort is not proper");
					 }		 
	 }
		
		  for (Integer integer : intList) { 
			  System.out.println(integer);
			  }
		 
	 System.out.println("The lowest KM driven car is : "+temp);
	 int i =1;
	 for (WebElement eachEle : elesTotalCarsList) {	

		 int parseInt = Integer.parseInt(eachEle.getText().replaceAll("\\D", ""));

		if(parseInt==temp) {

			System.out.println("Value of i is "+i);
			System.out.println("Value of temp is "+temp);			
				try {
					driver.findElementByXPath("(//span[contains(@class,'shortlist-icon--inactive')])["+i+"]").click();
				} catch (Exception e) {
					 act.sendKeys(Keys.PAGE_DOWN).perform();
					if(driver.findElementByXPath("(//span[contains(@class,'shortlist-icon--inactive')])["+i+"]").isDisplayed()) {
					driver.findElementByXPath("(//span[contains(@class,'shortlist-icon--inactive')])["+i+"]").click();
					}else {
						 act.sendKeys(Keys.PAGE_DOWN).perform();
						 driver.findElementByXPath("(//span[contains(@class,'shortlist-icon--inactive')])["+i+"]").click();				
					}
		}
		
		 	}
		i++;
	 }
	 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='action-box shortlistBtn']")));
	 driver.findElementByXPath("//li[@class='action-box shortlistBtn']").click();
	 Thread.sleep(5000);
	 wait.until(ExpectedConditions.elementToBeClickable(By.linkText("More details »")));
	 driver.findElementByLinkText("More details »").click();
	 Thread.sleep(5000);
	 
	 
	 Set<String> windowHandles = driver.getWindowHandles();
	 List<String> listWindow = new ArrayList<String>(windowHandles);
	 driver.switchTo().window(listWindow.get(1));
	 

	 act.sendKeys(Keys.PAGE_DOWN).perform();
	 act.sendKeys(Keys.PAGE_DOWN).perform();
	 Thread.sleep(5000);
	 List<WebElement> key = driver.findElementsByXPath("//div[@id='overview']//li/div[@class='equal-width text-light-grey']");
	List<WebElement> value = driver.findElementsByXPath("//div[@id='overview']//li/div[@class='equal-width dark-text']");
	 wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementsByXPath("//div[@class='overview-list padding-bottom10']//div[l@class='equal-width text-light-grey']")));
	 System.out.println("No of overview attibutes for the selected car: "+key.size());
	 System.out.println("Rechecking the no of overview attibutes for the selected car: "+value.size());
	 for (WebElement webElement : key) {
		 map.put(key.get(k).getText(), value.get(k).getText()); 
		k++;
	}
	 for (Entry<String, String> eachMapEle : map.entrySet()) {
		 System.out.println(eachMapEle);
		
	}
	 test.pass("TC- CarWale: Pass",MediaEntityBuilder.createScreenCaptureFromPath("./Reports/images/carWale.jpg").build());
	 
	}
	  
	
  

  

  @AfterMethod
  public void afterMethod() throws IOException {
	  File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  File dest =  new File("./Reports/images/carWale.jpg");	  
	 FileUtils.copyFile(source, dest);
  }

	/*
	 * 29/04/2020 ========== 1) Go to https://www.carwale.com/ 2) Click on Used 3)
	 * Select the City as Chennai 4) Select budget min (8L) and max(12L) and Click
	 * Search 5) Select Cars with Photos under Only Show Cars With 6) Select
	 * Manufacturer as "Hyundai" --> Creta 7) Select Fuel Type as Petrol 8) Select
	 * Best Match as "KM: Low to High" 9) Validate the Cars are listed with KMs Low
	 * to High 10) Add the least KM ran car to Wishlist 11) Go to Wishlist and Click
	 * on More Details 12) Print all the details under Overview 13) Close the
	 * browser.
	 */

  @AfterClass
  public void afterClass() {
	  reports.flush();
	
System.out.println("Execution completed successfully");	  
  }

}
