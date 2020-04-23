package vanillaScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TC006_HondaBike {
	public static ChromeOptions options;
	public static ChromeDriver driver;
	public static WebDriverWait wait;
	public static ExtentHtmlReporter html;
	public static ExtentReports extent;

	@BeforeTest
	public void extentReports() {
		html = new ExtentHtmlReporter("./Reports/reports.html");
		html.setAppendExisting(true);
		extent = new ExtentReports();
		extent.attachReporter(html);

	}

	@BeforeMethod
	public void startApp() {
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

	@Test
	public void hondaBikeTC() throws InterruptedException {
		ExtentTest testLevel = extent.createTest("TC006", "HondaWebsiteAutomation");
		try {
			driver.get("https://www.honda2wheelersindia.com/");
			testLevel.pass("URL launched successfully");
		} catch (Exception e) {
			testLevel.fail("URL not launched");
		}
		Thread.sleep(3000);
		try {
			driver.findElementByXPath("//button[@class='close']").click();
			testLevel.pass("PopUp closed successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			testLevel.fail("PopUp didnt occured");
		}
		// Dio
		Float floatDioCC = null;
		try {
			driver.findElementByXPath("//a[text()='Scooter']").click();
			Thread.sleep(500);
			wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElementByXPath("((//div[@class='owl-wrapper-outer'])[2]//a)[1]")));
			driver.findElementByXPath("((//div[@class='owl-wrapper-outer'])[2]//a)[1]").click();
			Thread.sleep(500);
			wait.until(
					ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()='Specifications']")));
			driver.findElementByXPath("//a[text()='Specifications']").click();

			WebElement eleEngineDio = driver.findElementByXPath("//a[text()='ENGINE']");
			Thread.sleep(500);
			wait.until(ExpectedConditions.elementToBeClickable(eleEngineDio));
			Actions action = new Actions(driver);
			action.moveToElement(eleEngineDio).perform();
			String dioCC = driver.findElementByXPath("//div[@class='engine part-2 axx']//span[contains(text(),'cc')]")
					.getText();
			dioCC = dioCC.replace("cc", "");
			System.out.println(dioCC);
			floatDioCC = Float.valueOf(dioCC);
			System.out.println("CC of Dio: " + floatDioCC);
			testLevel.pass("Scooter : DIO's engine details fetched");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			testLevel.fail("Scooter : DIO's engine details are not fetched");
		}

		// Activa

		Float floatActivaCC = null;
		try {
			driver.findElementByXPath("//a[text()='Scooter']").click();
			Thread.sleep(500);
			wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElementByXPath("((//div[@class='owl-wrapper-outer'])[2]//a)[3]")));
			driver.findElementByXPath("((//div[@class='owl-wrapper-outer'])[2]//a)[3]").click();
			Thread.sleep(500);
			wait.until(
					ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()='Specifications']")));

			driver.findElementByXPath("//a[text()='Specifications']").click();
			WebElement eleEngineActiva = driver.findElementByXPath("//a[text()='ENGINE']");
			Thread.sleep(500);
			wait.until(ExpectedConditions.elementToBeClickable(eleEngineActiva));
			Actions action1 = new Actions(driver);
			action1.moveToElement(eleEngineActiva).perform();
			String activaCC = driver
					.findElementByXPath("//div[@class='engine part-4 axx']//span[contains(text(),'cc')]").getText();
			activaCC = activaCC.replace("cc", "");
			System.out.println(activaCC);
			floatActivaCC = Float.valueOf(activaCC);
			System.out.println("CC of Activa: " + floatActivaCC);
			testLevel.pass("Scooter : Activa's engine details fetched");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			testLevel.fail("Scooter : Activa's engine details are  fetched");
		}

		if (floatActivaCC > floatDioCC) {
			System.out.println("Scooter Activa is having the high CC: " + floatActivaCC);
		} else {
			System.out.println("Scooter Dio is having the high CC: " + floatDioCC);
		}

		try {
			driver.findElementByXPath("(//a[text()='FAQ'])[1]").click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.elementToBeClickable(driver
					.findElementByXPath("(//div[@class='home-box about-box tag'])[2]//a[text()='Activa 125 BS-VI']")));
			driver.findElementByXPath("(//div[@class='home-box about-box tag'])[2]//a[text()='Activa 125 BS-VI']")
					.click();
			driver.findElementByXPath("//a[text()=' Vehicle Price']").click();
			testLevel.pass("Link: VehiclePrice is clicked");
		} catch (Exception e) {
			e.printStackTrace();
			testLevel.fail("Link: VehiclePrice is not clicked");
		}

		WebElement eleCheck = driver.findElementById("ModelID6");
		Select slt = new Select(eleCheck);
		String selectedValue = slt.getFirstSelectedOption().getText();

		if (selectedValue.contains("Activa 125 BS")) {
			System.out.println("Activa 125 BS-VI is selected");
		} else {
			System.out.println("Activa 125 BS-VI is not selected");
		}
		driver.findElementByXPath("(//button[text()='Submit'])[6]").click();
		driver.findElementByXPath("//a[contains(text(),'price of Activa 125 BS-VI.')]").click();
		System.out.println("Price link: clicked");
		Set<String> setWindows = driver.getWindowHandles();
		List<String> listWindows = new ArrayList<String>(setWindows);
		driver.switchTo().window(listWindows.get(1));

		WebElement eleddState = driver.findElementById("StateID");
		Select ddState = new Select(eleddState);
		ddState.selectByVisibleText("Tamil Nadu");

		WebElement eleddCity = driver.findElementById("CityID");
		Select ddCity = new Select(eleddCity);
		ddCity.selectByVisibleText("Chennai");
		driver.findElementByXPath("//button[text()='Search']").click();
		// Table

		try {
			WebElement bikeTable = driver.findElementByXPath("//table[@id='gvshow']/tbody");

			List<WebElement> bikeTableRows = bikeTable.findElements(By.tagName("tr"));
			int numberOfRows = bikeTableRows.size();
			System.out.println("Number of rows in the Bike table: " + numberOfRows);
//Printing first row
			List<WebElement> eachRowColumn = bikeTableRows.get(0).findElements(By.tagName("td"));
			String model = eachRowColumn.get(1).getText();
			String exShowRoomPrice = eachRowColumn.get(2).getText();
			System.out.println("Model: " + model + "exShowRoom price: " + exShowRoomPrice);
			// inside for each
			for (int i = 1; i < bikeTableRows.size(); i++) {
				List<WebElement> eachRowColumn1 = bikeTableRows.get(i).findElements(By.tagName("td"));
				String model1 = eachRowColumn1.get(0).getText();
				String exShowRoomPrice1 = eachRowColumn1.get(1).getText();
				System.out.println("Model: " + model1 + "exShowRoom price: " + exShowRoomPrice1);

			}
			testLevel.pass("All the models are printed in the console");
		} catch (Exception e) {
			testLevel.fail("Failed to print all the models in the console");
			e.printStackTrace();
		}

		/*
		 * 1) Go to https://www.honda2wheelersindia.com/ 2) Click on scooters and click
		 * dio 3) Click on Specifications and mouseover on ENGINE 4) Get Displacement
		 * value 5) Go to Scooters and click Activa 125 6) Click on Specifications and
		 * mouseover on ENGINE 7) Get Displacement value 8) Compare Displacement of Dio
		 * and Activa 125 and print the Scooter name having better Displacement. 9)
		 * Click FAQ from Menu 10) Click Activa 125 BS-VI under Browse By Product 11)
		 * Click Vehicle Price 12) Make sure Activa 125 BS-VI selected and click submit
		 * 13) click the price link 14) Go to the new Window and select the state as
		 * Tamil Nadu and city as Chennai 15) Click Search 16) Print all the 3 models
		 * and their prices 17) Close the Browser
		 */

	}

	@AfterMethod
	public void closeBrowser() {
		driver.quit();
	}

	@AfterTest
	public void flushExtent() {
		extent.flush();
	}
}

//}
