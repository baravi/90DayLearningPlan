package vanillaScript;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TC011_SnapDeal {
	ChromeDriver driver;
	ChromeOptions options;
	DesiredCapabilities cap;
	WebDriverWait wait;
	Actions act;
	int total = 0;

	@Test
	public void snapDealTcs() throws InterruptedException {
		act.moveToElement(driver.findElementByXPath("//span[contains(text(),'Toys,')]")).perform();
		driver.findElementByXPath("(//span[contains(text(),'Toys')])[2]").click();
		driver.findElementByXPath("//div[contains(text(),'Educational Toys')]").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//label[@for='avgRating-4.0']")));
		driver.findElementByXPath("//label[@for='avgRating-4.0']").click();
		System.out.println("Rating: selected");
		Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//label[@for='discount-40%20-%2050']")));
		driver.findElementByXPath("//label[@for='discount-40%20-%2050']").click();

		if (driver.findElementByXPath("//span[text()='Deliver to: ']").isEnabled()) {
			System.out.println("Product will be delivered to the give pincode");
		} else {
			System.out.println("Product will not be delivered to the give pincode");
		}
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//input[@placeholder='Enter your pincode']")));
		driver.findElementByXPath("//input[@placeholder='Enter your pincode']").sendKeys("600100");
		driver.findElementByXPath("//button[text()='Check']").click();

		act.moveToElement(
				driver.findElementByXPath("(//section[@class='js-section clearfix dp-widget  dp-fired'])[1]/div[1]"))
				.perform();
		Thread.sleep(3000);
		driver.findElementByXPath("(//div[@class='clearfix row-disc'])[1]//div[@class ='center quick-view-bar  btn btn-theme-secondary  ']")
				.click();
		System.out.println("Quick View: clicked");

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[contains(text(),'view details')]")));
		driver.findElementByXPath("//a[contains(text(),'view details')]").click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@itemprop='price']")));
		String priceString = driver.findElementByXPath("//span[@itemprop='price']").getText();
//		System.out.println(priceString);
		int priceInt = Integer.parseInt(priceString);
		System.out.println("Price of the product is: " + priceInt);
		String dPriceString = driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText();
//		System.out.println(dPriceString);
		dPriceString = dPriceString.replace("(+) Rs ", "");
		int dPriceInt = Integer.parseInt(dPriceString);
		System.out.println("Deleivery price: " + dPriceInt);
		driver.findElementByXPath("//span[text()='add to cart']").click();
		System.out.println("ATO: clicked");

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='you-pay']/span")));
		String youPayString = driver.findElementByXPath("//div[@class='you-pay']/span").getText();
//		System.out.println(youPayString);
		youPayString = youPayString.replace("Rs. ", "");
		int youPayInt = Integer.parseInt(youPayString);
		System.out.println("Total price of 1 product : "+youPayInt);
		total = priceInt + dPriceInt;

		if (total == youPayInt) {
			System.out.println("You Pay is matching");
		} else {
			System.out.println("You Pay is not matching");
		}

		driver.findElementById("inputValEnter").sendKeys("sanitizer", Keys.RETURN);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//p[contains(text(),'BioAyurveda Neem Power  Hand Sanitizer 500 mL Pack of 1')])[1]")));
		driver.findElementByXPath("(//p[contains(text(),'BioAyurveda Neem Power  Hand Sanitizer 500 mL Pack of 1')])[1]").click();

		Set<String> setWindowsList = driver.getWindowHandles();
		List<String> listWindowsList = new ArrayList<String>(setWindowsList);
		int sizeOfList = listWindowsList.size();
		System.out.println("Number of windows active: " + sizeOfList);
		driver.switchTo().window(listWindowsList.get(1));

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@itemprop='price']")));
		String priceString1 = driver.findElementByXPath("//span[@itemprop='price']").getText();
//		System.out.println(priceString1);
		int priceInt1 = Integer.parseInt(priceString1);
		System.out.println("Price of the product is: " + priceInt1);
		String dPriceString1 = driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText();
//		System.out.println(dPriceString1);
		dPriceString1 = dPriceString1.replace("(+) Rs ", "");
		int dPriceInt1 = Integer.parseInt(dPriceString1);
		System.out.println("Deleiver price: " + dPriceInt1);
		driver.findElementByXPath("(//span[text()='ADD TO CART'])[1]").click();
		System.out.println("ATO: clicked");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cs-youpay-amt rfloat']")));
		String totalYouPay = driver.findElementByXPath("//div[@class='cs-youpay-amt rfloat']").getText();
//		System.out.println("Total payable : " + totalYouPay);
		totalYouPay = totalYouPay.replace("Rs. ", "");
		int youPayInt2 = Integer.parseInt(totalYouPay);
		System.out.println("Total payable : " + youPayInt2);

		total = youPayInt + priceInt1 + dPriceInt1;
		System.out.println("Expected total 'you pay' inclusive of two products: " + total);
		if (total == youPayInt2) {
			System.out.println("Total Price is matching: TC-SnapDeal is pass");
		} else {
			System.out.println("Total Price is not matching: TC-SnapDeal is Fail");
		}

	}

	/*
	 * 1) Go to https://www.snapdeal.com/2) Mouse over on Toys, Kids' Fashion & more
	 * and click on Toys 3) Click Educational Toys in Toys & Games ‎4) Click the
	 * Customer Rating 4 star and Up 5) Click the offer as 40-50 6) Check the
	 * availability for the pincode 7) Click the Quick View of the first product 8)
	 * Click on View Details 9) Capture the Price of the Product and Delivery Charge
	 * 10) Validate the You Pay amount matches the sum of (price+deliver charge) 11)
	 * Search for Sanitizer 12) Click on Product
	 * "BioAyurveda Neem Power Hand Sanitizer" 13) Capture the Price and Delivery
	 * Charge 14) Click on Add to Cart 15) Click on Cart 16) Validate the Proceed to
	 * Pay matches the total amount of both the products 17) Close all the windows
	 */
	@BeforeMethod
	public void startApp() {
		driver.get("https://www.snapdeal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	@BeforeClass
	public void setCommonValues() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_83.exe");
		driver = new ChromeDriver();
		options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);
		act = new Actions(driver);
		wait = new WebDriverWait(driver, 30);

	}

	
}
