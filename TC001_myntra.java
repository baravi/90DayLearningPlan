package vanillaScript;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC001_myntra {
	public static ChromeDriver driver;
	int totalCategory = 0;

	@BeforeMethod
	public void launchBrowser() {

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test
	public void myntraTest() throws InterruptedException {

		driver.get("https://www.myntra.com/");
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElementByXPath("//a[text()='Women']")).perform();
		driver.findElementByXPath("//a[text()='Jackets & Coats']").click();

		String total = driver.findElementByXPath("//span[@class='title-count']").getText().replaceAll("\\D", "");
		int totalProductCount = Integer.parseInt(total);
		System.out.println("Total number of products " + totalProductCount);

		List<WebElement> eleCategories = driver.findElementsByXPath("//span[@class='categories-num']");
		System.out.println("Total cateogry " + eleCategories.size());

		for (WebElement each : eleCategories) {
			String text = each.getText();
			text = text.replace("(", "");
			text = text.replace(")", "");
			int integerText = Integer.parseInt(text);
			totalCategory = totalCategory + integerText;

		}
		System.out.println("Sum of each category is " + totalCategory);
		if (totalProductCount == totalCategory) {
			System.out.println("Count  match..!");
			driver.findElementByXPath("(//div[@class='common-checkboxIndicator'])[2]").click();
			driver.findElementByXPath("//div[@class='brand-more']").click();
			driver.findElementByXPath("//input[@class='FilterDirectory-searchInput']").sendKeys("MANGO");
			driver.findElementByXPath("//ul[@class='FilterDirectory-list']/li[2]").click();
			Thread.sleep(3000);
			driver.findElementByXPath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']").click();
			List<WebElement> eachProductName = driver.findElementsByXPath("//h3[@class='product-brand']");
			for (WebElement each : eachProductName) {

				if (each.getText().equalsIgnoreCase("MANGO")) {
					System.out.println("All products are related to the Brand - MANGO");
				} else {
					System.out.println("TC's Fail. Product Brand mis match..!");
					break;

				}

			}
			action.moveToElement(driver.findElementByXPath("//div[@class='sort-sortBy']")).perform();
			driver.findElementByXPath("(//label[@class='sort-label '])[3]").click();
			action.moveToElement(driver.findElementByXPath("//ul[@class='filter-summary-filterList']")).perform();
			Thread.sleep(3000);

			List<WebElement> priceOfEachProduct = driver
					.findElementsByXPath("//span[@class='product-discountedPrice']");
			System.out.println("Price of first listed product : " + priceOfEachProduct.get(0).getText());
			action.moveToElement(driver.findElementByXPath("//span[@class='product-discountedPrice']")).perform();
			driver.findElementByXPath(
					"(//span[@class='product-actionsButton product-wishlist product-prelaunchBtn'])[1]").click();
			System.out.println("Product added to the WishList && TC01 -Pass");

		} else
			System.err.println("TC's Fail. Count mis match..!");

	}

	@AfterSuite
	public void closeBrowser() {
		driver.close();
	}

}

/*
 * 1) Open https://www.myntra.com/ 2) Mouse over on WOMEN (Actions ->
 * moveToElement 3) Click Jackets & Coats 4) Find the total count of item (top)
 * -> getText -> String
 * 
 * String str = driver.findElementByClassName("title-count").getText(); split,
 * String text = str.replaceAll("\\D","") -> String Integer.parseInt(text) ->
 * int
 * 
 * 5) Validate the sum of categories count matches 6) Check Coats 7) Click +
 * More option under BRAND 8) Type MANGO and click checkbox 9) Close the pop-up
 * x 10) Confirm all the Coats are of brand MANGO findElements (brand) ->
 * List<WebElement> foreach -> getText of each brand compare > if(condition) 11)
 * Sort by Better Discount 12) Find the price of first displayed item
 * findElements (price) -> List<WebElement> get(0) -> WebElement -> getText ->
 * String -> int 13) Mouse over on size of the first item 14) Click on WishList
 * Now 15) Close Browser
 */
