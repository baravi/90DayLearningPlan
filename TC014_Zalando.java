package vanillaScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.FileUtil;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class TC014_Zalando {

	ChromeDriver driver;
	ChromeOptions options;
	DesiredCapabilities cap;
	Actions act;
	WebDriverWait wait;
	ExtentHtmlReporter html;
	ExtentReports reports;
	ExtentTest createTest;
	String ImageName  = "img";
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_83.exe");
		options = new ChromeOptions();
		options.addArguments("--disable-notifications");
//		cap = new DesiredCapabilities();
//		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
//		options.merge(cap);
		driver = new ChromeDriver(options);
		act = new Actions(driver);
		wait = new WebDriverWait(driver, 15);
		html = new ExtentHtmlReporter("./Reports/reports.html");
		html.setAppendExisting(true);
		reports = new ExtentReports();
		reports.attachReporter(html);
	}

	@BeforeMethod
	public void startApp() {
		driver.get("https://www.zalando.com/");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void zalandoTCs() throws InterruptedException, IOException {
		
		createTest = reports.createTest("TC013", "Zalando website automation");
		try {
			String alertText = driver.switchTo().alert().getText();
			System.out.println("Alert Text: " + alertText);
			driver.switchTo().alert().accept();
			System.out.println("Alert: Accepted");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		driver.findElementByXPath("//a[contains(text(),'uk')]").click();
//		System.out.println("Country : Selected");
		createTest.pass("Country selected");
		driver.findElementByXPath("(//span[text()='Women'])[2]").click();
		driver.findElementByXPath("//span[text()='Clothing']").click();
		driver.findElementByXPath("//li[@class='cat_item-z_SDi']//a[text()='Coats']").click();
		Thread.sleep(5000);
		driver.findElementById("uc-btn-accept-banner").click();
		act.moveToElement(driver.findElementByXPath("//li[@class='cat_filter-yI6Mr']//span[text()='Material']")).click()
				.perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='cotton (100%)']")));
		driver.findElementByXPath("//span[text()='cotton (100%)']").click();
		act.moveToElement(driver.findElementByXPath("//li[@class='cat_filter-yI6Mr']//span[text()='Material']")).click()
				.perform();
		createTest.pass("Material : 100% Cotton selected");
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//span[text()='Length']")).click().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='thigh-length']")));
		driver.findElementByXPath("//span[text()='thigh-length']").click();
		act.moveToElement(driver.findElementByXPath("//span[text()='Length']")).click().perform();
		createTest.pass("Length : Thin Length selected");
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='MANTEL - Parka - navy']")));
		act.moveToElement(driver.findElementByXPath("//div[text()='MANTEL - Parka - navy']")).click().perform();
		createTest.pass("PDP page: Opened");
		driver.findElementByXPath("(//div[@class='pFccbT X8C95H'])[2]").click();
		createTest.pass("Colour: Olive selected");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("picker-trigger")));
		driver.findElementById("picker-trigger").click();
		driver.findElementByXPath("//span[text()='M']").click();
		boolean availabilityFlagOlive = driver.findElementByXPath("//h2[text()='Out of stock']").isDisplayed();
		System.out.println("OOS? " + availabilityFlagOlive);
		if (availabilityFlagOlive == true) {
			System.out.println("Colour: Olive and the Size M is OOS..! So, selecting the colour Navy");
			driver.findElementByXPath("(//div[@class='pFccbT X8C95H'])[2]").click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("picker-trigger")));
			driver.findElementById("picker-trigger").click();
			driver.findElementByXPath("//span[text()='M']").click();
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElementByXPath("(//span[@class='AtOZbZ' and text()='Free'])[1]")));
			boolean shipmentFlagFreedisplayed = driver
					.findElementByXPath("(//span[@class='AtOZbZ' and text()='Free'])[1]").isDisplayed();
			System.out.println("Shipment Free? " + shipmentFlagFreedisplayed);
			if (shipmentFlagFreedisplayed == true) {
				driver.findElementByXPath("//span[text()='Add to bag']").click();
				createTest.pass("Product added to the cart");
				act.moveToElement(driver.findElementByXPath("//a[@class='z-navicat-header_navToolItemLink']"))
						.perform();
				driver.findElementByXPath("//div[text()='Go to bag']").click();
				createTest.pass("In to the Cart page");
				String estimatedDelivery = driver
						.findElementByXPath("(//span[@class='z-2-text z-2-text-body z-2-text-black'])[1]").getText();
				System.out.println("Estimated deliver is : " + estimatedDelivery);
				Thread.sleep(2000);
				act.moveToElement(driver.findElementByXPath("//a[text()='Free delivery & returns*']")).perform();
				String toolTipText = driver
						.findElementByXPath("(//span[@class='z-navicat-header-uspBar_message-split_styled'])[2]")
						.getAttribute("title");
				System.out.println("The Tool Tip text is : " + toolTipText);
				driver.findElementByXPath("//a[text()='Free delivery & returns*']").click();
				System.out.println("FreeDeliverReturns link clicked");
				Thread.sleep(3000);
				boolean startChatFlag = driver.findElementByXPath("//span[text()='Start chat']").isDisplayed();
				if (startChatFlag == true) {
					System.out.println("Chat Flag enabled? " + startChatFlag);

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Start chat']")));
					act.moveToElement(driver.findElementByXPath("//span[text()='Start chat']")).click().perform();
					Set<String> setWindows = driver.getWindowHandles();
					List<String> listWindows = new ArrayList<String>(setWindows);
					driver.switchTo().window(listWindows.get(1));
					createTest.pass("Switched to Chat Window");
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("prechat_customer_name_id")));
					driver.findElementById("prechat_customer_name_id").sendKeys("Baravi C");
					driver.findElementById("prechat_customer_email_id").sendKeys("Tester@gmail.com", Keys.ENTER);
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("liveAgentChatTextArea")));
					driver.findElementById("liveAgentChatTextArea").sendKeys("Hi", Keys.RETURN);
					Thread.sleep(3000);
					String operatorResponse = driver.findElementByXPath("((//span[@class='operator'])[3]//span)[2]")
							.getText();
					createTest.pass("Get the Operator response");
					System.out.println("Operator Response: " + operatorResponse);
				} else {
					System.out.println("Start Chat option will be available only in UK Time");
				}
			} else {
				System.out.println("Shipment is not free.");
			}
			}else {
				File source = driver.getScreenshotAs(OutputType.FILE);
				File dest = new File("./Reports/Images/"+ImageName+".png");
				FileUtils.copyFile(source, dest);
				createTest.fail("None of the products are available", MediaEntityBuilder.createScreenCaptureFromPath("./Reports/Images/"+ImageName+".png").build());
				
			}
		}
	

	@AfterMethod
	public void afterMethod() {
		System.out.println("Execution completed");
		driver.quit();
	}
	/*
	 * 04/05/2020 =========== 1) Go to https://www.zalando.com/ 2) Get the Alert
	 * text and print it 3) Close the Alert box and click on Zalando.uk 4) Click
	 * Women--> Clothing and click Coat 5) Choose Material as cotton (100%) and
	 * Length as thigh-length 6) Click on Q/S designed by MANTEL - Parka coat 7)
	 * Check the availability for Color as Olive and Size as 'M' 8) If the previous
	 * preference is not available, check availability for Color Navy and Size 'M'
	 * 9) Add to bag only if Standard Delivery is free 10) Mouse over on Your Bag
	 * and Click on "Go to Bag" 11) Capture the Estimated Deliver Date and print 12)
	 * Mouse over on FREE DELIVERY & RETURNS*, get the tool tip text and print 13)
	 * Click on Start chat in the Start chat and go to the new window 14) Enter you
	 * first name and a dummy email and click Start Chat 15) Type Hi, click Send and
	 * print thr reply message and close the chat window.
	 */

	@AfterClass
	public void afterClass() {
		reports.flush();
		
	}

}
