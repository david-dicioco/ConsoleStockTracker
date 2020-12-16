package StepDefinitions;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.cucumber.java.en.*;

public class EBGamesSteps {

	WebDriver driver = null;

	@Given("browser is open - eb games")
	public void bestbuy_browser_is_open() {

		System.out.println("Step 1 - browser is open - eb games");

		String projectPath = System.getProperty("user.dir");
		System.out.println("Project path is:" + projectPath);

		System.setProperty("webdriver.chrome.driver", projectPath+"/src/test/resources/drivers/chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
	}

	@Given("user is on EB Games page")
	public void user_is_on_eb_games_page() {

		System.out.println("Step 2 - user is on ebgames page");

		//Playstaion 5
		driver.navigate().to("https://www.ebgames.ca/SearchResult/QuickSearchHeaderPlatform?platform=453&rootGenre=74");
		driver.manage().window().maximize();

		//driver.navigate().to("https://www.ebgames.ca/");
		//driver.manage().window().maximize();
	}


	@And("click on product")
	public void click_on_product() throws InterruptedException {

		System.out.println("Step 3 - click on product");

		driver.findElement(By.className("prodImg")).click();

		Thread.sleep(2000);
	}

	@When("user enters product page")
	public void user_enters_product_page() {

		System.out.println("Step 4 - user enters product page");

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).perform();
		System.out.println("Title for product is present: " + driver.getPageSource().contains("PlayStation 5"));
	}

	@Then("user check product stock")
	public void user_check_product_stock() {

		System.out.println("Step 5 - user check product stock");

		System.out.println("Out of Stock is present: " + driver.getPageSource().contains("Out of Stock"));
		if(driver.getPageSource().contains("Out of Stock")) {
			System.out.println("Current Page contains: Out of Stock");
			
			driver.close();
			driver.quit();
			Assert.fail();
		} else {
			//fail test step
			System.out.println("Current Page DOES NOT contains: Out of Stock");
		}
		
		driver.close();
		driver.quit();
	}



	/*@When("user hovers over product button")
	public void user_hovers_over_product_button() {

		System.out.println("Step 3 - user hovers over product button");

		WebElement ele = driver.findElement(By.className("PS5"));
	    Actions action = new Actions(driver);
	    action.moveByOffset(500, 500);
	    //action.moveToElement(ele, 10, 10);
	    action.build().perform();
	    //action.moveToElement(ele).build().perform();
	}*/




}
