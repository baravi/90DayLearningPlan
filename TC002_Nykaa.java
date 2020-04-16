package vanillaScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TC002_Nykaa {
	public static RemoteWebDriver driver;

	@Test
	public void nykaaTC() {
		try {
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");

			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			driver.get("https://www.nykaa.com/");

			Actions action = new Actions(driver);
			action.moveToElement(driver.findElementByXPath("//a[text()='brands']")).build().perform();
			action.moveToElement(driver.findElementByXPath("//a[text()='Popular']")).build().perform();
			driver.findElementByXPath("//li[@class='brand-logo menu-links'][5]//img").click();

			Set<String> windowHandles = driver.getWindowHandles();
			List<String> listWindows = new ArrayList<String>(windowHandles);
			System.out.println("Total number of windows opened after clicking the brand " + listWindows.size());
			driver.switchTo().window(listWindows.get(1));

			System.out.println("Current title expected to be contains L'OrealParis: " + driver.getTitle());
			driver.findElementByXPath("//div[text()='Category']").click();

			driver.findElementByXPath("//span[contains(text(),'Shampoo')]").click();
			driver.findElementByXPath("//span[contains(text(),'Paris Colour Protect Shampoo')]").click();

			Set<String> windowHandles2 = driver.getWindowHandles();
			List<String> listWindows2 = new ArrayList<String>(windowHandles2);

			System.out.println("Second windowHandles: " + listWindows2.size());
//	System.out.println("old Second windowHandles: "+listWindows.size());
//		listWindows2.get(2);
			driver.switchTo().window(listWindows2.get(2));
			driver.findElementByXPath("//span[text()='175ml']").click();
			String priceOfProduct = driver.findElementByXPath("//span[@class='post-card__content-price-offer']")
					.getText();
			priceOfProduct = priceOfProduct.replaceAll("[^0-9]", "");

			System.out.println("Price of the product: " + priceOfProduct);

			driver.findElementByXPath(
					"//button[@class='combo-add-to-btn prdt-des-btn js--toggle-sbag nk-btn nk-btn-rnd atc-simple m-content__product-list__cart-btn  ']")
					.click();

			driver.findElementByXPath("//div[@class='AddBagIcon']").click();

			String grandTotal = driver.findElementByXPath("//div[@class='value medium-strong']").getText();
			grandTotal = grandTotal.replaceAll("[^0-9]", "");

			System.out.println("Grand Total is : " + grandTotal);

			driver.findElementByXPath("//div[@class='second-col']").click();

			driver.findElementByXPath("//button[text()='CONTINUE AS GUEST']").click();

			String warningMessage = driver.findElementByXPath("//div[@class='message']").getText();
			System.out.println("Warning to the user : " + warningMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());

		}
		/*
		 * 1) Go to https://www.nykaa.com/ 2) Mouseover on Brands and Mouseover on
		 * Popular 3) Click L'Oreal Paris 4) Go to the newly opened window and check the
		 * title contains L'Oreal Paris 5) Click sort By and select customer top rated
		 * 6) Click Category and click Shampoo 7) check whether the Filter is applied
		 * with Shampoo 8) Click on L'Oreal Paris Colour Protect Shampoo 9) GO to the
		 * new window and select size as 175ml 10) Print the MRP of the product 11)
		 * Click on ADD to BAG 12) Go to Shopping Bag 13) Print the Grand Total amount
		 * 14) Click Proceed 15) Click on Continue as Guest 16) Print the warning
		 * message (delay in shipment) 17) Close all windows
		 */

	}

	@AfterMethod
	public void closeDriver() {
		System.out.println("Completed");
		driver.quit();
	}

}
