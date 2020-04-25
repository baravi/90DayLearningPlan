package vanillaScript;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

public class TC009_DemoRCRM {
	RemoteWebDriver driver;
	WebDriverWait wait;
	ChromeOptions options;
	DesiredCapabilities cap;
	Actions action;
	
	
	
	@DataProvider
	public String[][] dataProvider() {
		
		String[][] data = new String[1][11];
		data[0][0]= "admin";
		data[0][1]= "Baravi";
		data[0][2]= "C";
		data[0][3]= "cbaravi@gmail.com";
		data[0][4]= "9486403438";
		data[0][5]= "Plot 4, India";
		data[0][6]= "Salem";
		data[0][7]= "Tamil Nadu";
		data[0][8]= "India";
		data[0][9]= "636451";
		data[0][10]= "https://demo.1crmcloud.com/";
		
		return data;
					}
	

	@BeforeMethod
	public void startA() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_83.exe");
		options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test(dataProvider = "dataProvider")
	public void demoCRMTCs(String cred,String fname,String lname,String email,String phone,String adrs,String city,String state, String country, String zip, String url) throws InterruptedException {
		driver.navigate().to(url);
		driver.findElementById("login_user").sendKeys(cred);
		driver.findElementById("login_pass").sendKeys(cred);
		Select eleTheme = new Select(driver.findElementById("login_theme"));
		eleTheme.selectByVisibleText("Claro Theme");
		driver.findElementById("login_button").click();
		System.out.println("Login: Button clicked");

		try {
			driver.findElementByXPath("//div[contains(text(),'Sales & Marketing')]").click();
			System.out.println("Sales and Marketing clicked");
			driver.findElementByXPath("//div[contains(text(),'Create Contact')]").click();
			Thread.sleep(5000);
			driver.findElementById("DetailFormsalutation-input-label").click();
			driver.findElementByXPath("//div[@id='DetailFormsalutation-input-popup']//div[@class='menu-option single']")
					.click();
			driver.findElementById("DetailFormfirst_name-input").sendKeys(fname);
			driver.findElementById("DetailFormlast_name-input").sendKeys(lname);
			driver.findElementById("DetailFormemail1-input").sendKeys(email);
			driver.findElementById("DetailFormphone_work-input").sendKeys(phone);
			System.out.println("Basic Details entered");
			driver.findElementById("DetailFormlead_source-input-label").click();
			driver.findElementByXPath("//div[text()='Public Relations']").click();
			System.out.println("lead role as Public related selected");
			driver.findElementById("DetailFormbusiness_role-input-label").click();
			driver.findElementByXPath("//div[text()='Sales']").click();
			System.out.println("Business role as Sales selected");
			driver.findElementById("DetailFormprimary_address_street-input").sendKeys(adrs);
			driver.findElementById("DetailFormprimary_address_city-input").sendKeys(city);
			driver.findElementById("DetailFormprimary_address_state-input").sendKeys(state);
			driver.findElementById("DetailFormprimary_address_country-input").sendKeys(country, Keys.TAB);
			driver.findElementById("DetailFormprimary_address_postalcode-input").sendKeys(zip);
			driver.findElementByXPath("//button[@class='input-button group-left first group-left first']").click();
			System.out.println("Contact Created");
		} catch (Exception e) {
			System.out.println("Via catchBlock as pop didn't occured");
			// Thread.sleep(3000);
			driver.findElementByXPath("//div[@class='uii uii-cancel uii-lg active-icon dialog-close']").click();
			System.out.println("Popup handled");

		}

		action = new Actions(driver);
		Thread.sleep(2000);
		action.moveToElement(driver.findElementByXPath("(//div[@class='menu-label'])[1]")).perform();
		driver.findElementByXPath("//div[text()='Meetings']").click();
		Thread.sleep(2000);
//		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[text()='Create']"))).click();			
		driver.findElementByXPath("//span[text()='Create']").click();
		Thread.sleep(2000);
		driver.findElementById("DetailFormname-input").sendKeys("Project Status");
//		wait.until(ExpectedConditions.visibilityOf(driver.findElementById("DetailFormname-input"))).sendKeys("Project Status");
		Thread.sleep(3000);
		action.moveToElement(driver.findElementById("DetailFormstatus-input")).click().perform();
		Thread.sleep(2000);

		driver.findElementByXPath("//div[@id='DetailFormstatus-input-popup']//div[text()='Planned']").click();
		driver.findElementById("DetailFormdate_start-input").click();
// get current date time with Date()
		Date date = new Date();
// Get only the date(and not month,year,time,etc)
		DateFormat dateFormat = new SimpleDateFormat("dd");
// Now format the date
		String today = dateFormat.format(date);
		int tommorow = Integer.parseInt(today) + 1;
		driver.findElementByXPath("//div[@id='DetailFormdate_start-calendar-text']//input")
				.sendKeys("2020,4," + tommorow, Keys.RETURN);
		driver.findElementByXPath("//div[@id='DetailFormdate_start-calendar-text']//input").sendKeys("15:00",Keys.RETURN);
		driver.findElementById("DetailFormduration-time").clear();
		driver.findElementById("DetailFormduration-time").sendKeys("1", Keys.TAB);
		driver.findElementByXPath("//span[text()=' Add Participants']").click();
		driver.findElementByXPath("(//input[@class='input-text'])[4]").sendKeys(fname);
		Thread.sleep(2000);
		driver.findElementByXPath("//div[@id='app-search']//div[@class='option-cell input-label ']").click();
		driver.findElementByXPath("(//span[text()='Save'])[2]").click();
		System.out.println("Meeting created");

		action.moveToElement(driver.findElementByXPath("//div[contains(text(),'Sales & Marketing')]")).perform();
		driver.findElementByXPath("//div[text()='Contacts']").click();
		Thread.sleep(2000);

		driver.findElementById("filter_text").sendKeys(fname, Keys.RETURN);
		Thread.sleep(2000);
		driver.findElementByXPath("(//table[@class='listView']//tr)[2]/td[3]").click();

		if (driver.findElementByXPath("//a[@class='listViewNameLink']").isDisplayed()) {
			System.out.println("Meetings created successfully");
		} else {
			System.out.println("Meetings not  created ");

		}

	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
/*
 * 24/04/2020 ============ 1) Go to https://demo.1crmcloud.com/ 2) Give username
 * as admin and password as admin 3) Choose theme as Claro Theme 4) Click on
 * Sales and Marketting 5) Click Create contact 6) Select Title and type First
 * name, Last Name, Email and Phone Numbers 7) Select Lead Source as
 * "Public Relations" 8) Select Business Roles as "Sales" 9) Fill the Primary
 * Address, City, State, Country and Postal Code and click Save 10) Mouse over
 * on Today's Activities and click Meetings 11) Click Create 12) Type Subject as
 * "Project Status" , Status as "Planned" 13) Start Date & Time as tomorrow 3 pm
 * and Duration as 1hr 14) Click Add paricipants, add your created Contact name
 * and click Save 15) Go to Sales and Marketting-->Contacts 16) search the lead
 * Name and click the name from the result 17) Check weather the Meeting is
 * assigned to the contact under Activities Section.
 */