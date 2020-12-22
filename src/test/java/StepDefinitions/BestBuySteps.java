package StepDefinitions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Pages.BestBuyPage;
import io.cucumber.java.en.*;

public class BestBuySteps {
	
	private WebDriver driver = null;
	private BestBuyPage bestbuyPage;
	private Document doc;
	private String projectPath;
	private int defaultSleepTimer = 2000;
	private Element div;
	private Element urlLink;

	@Given("Browser is open - BestBuy")
	public void browser_is_open_bestbuy() {

		/*projectPath = System.getProperty("user.dir");
		System.out.println("Project path is:" + projectPath);

		System.setProperty("webdriver.chrome.driver", projectPath+"/src/test/resources/drivers/chromedriver.exe");

		driver = new ChromeDriver();*/
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	//may not need
		//driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		
		driver = TestRunner.driver;
		projectPath = TestRunner.projectPath;
	}
	
	@And("User enters BestBuy homepage")
	public void user_is_on_bestbuy_search_page() {
		driver.navigate().to("https://www.bestbuy.ca/en-ca");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	@When("^User inputs a (.*) in BestBuy search box$")
	public void user_enters_product_in_search_box(String product) throws InterruptedException {
		
		bestbuyPage = new BestBuyPage(driver);
		bestbuyPage.enterProduct(product);	
		//Thread.sleep(defaultSleepTimer);
	}
	
	@And("User clicks BestBuy search button")
	public void user_clicks_bestbuy_search_button() throws InterruptedException {
		
		bestbuyPage.clickSearchButton();
		Thread.sleep(defaultSleepTimer);
	}
	
	@Then("^User clicks BestBuy (.*) link$")
	public void user_is_navigated_to_product_results_click_product(String product) throws InterruptedException {
		
		bestbuyPage.clickProductLink(product);
		Thread.sleep(defaultSleepTimer);
	}
	
	//TODO: Whatever the result is, put in custom HTML
	@And("^User verifies (.*) stock in BestBuy$")
	public void confirm_product_is_in_stock(String product) throws Exception {
		
		File input = new File(projectPath+"/target/ConsoleStockReport.html");
		doc = Jsoup.parse(input, "UTF-8", "");
		//System.out.println("TestReport Title: " + doc.title());
		
		if(bestbuyPage.verifyProductOutofStock()) {
			
			System.out.println(" =========== Product is: OUT OF STOCK =========== ");
			
			if (product.equalsIgnoreCase("PlayStation 4")) {
				div = doc.select("span[id=BestBuy-PS4]").first();
			} else if (product.equalsIgnoreCase("Xbox One Console")) {
				div = doc.select("span[id=BestBuy-XboxOne]").first();
			} else if (product.equalsIgnoreCase("PlayStation 5")) {
				div = doc.select("span[id=BestBuy-PS5]").first();
			} else if (product.equalsIgnoreCase("PlayStation 5 Digital Edition")) {
				div = doc.select("span[id=BestBuy-PS5-Digital]").first();
			} else if (product.equalsIgnoreCase("Xbox Series X")) {
				div = doc.select("span[id=BestBuy-XBX]").first();
			} else if (product.equalsIgnoreCase("Xbox Series S")) {
				div = doc.select("span[id=BestBuy-XBS]").first();
			}
			
			div.text("OUT OF STOCK");
			div.attr("style", "color:red");
			
			BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(projectPath+"/target/ConsoleStockReport.html"), "UTF-8"));
			htmlWriter.write(doc.toString());
			htmlWriter.close();

			//driver.close();
			//driver.quit();
			Assert.fail(); //if product is not in stock, fail this step
			
		} else {
			
			System.out.println("=========== Product is: IN STOCK ===========");
			
			
			if (product.equalsIgnoreCase("PlayStation 4")) {
				div = doc.select("span[id=BestBuy-PS4]").first();
				urlLink = doc.select("a[id=BestBuy-PS4-URL]").first();
				
			} else if (product.equalsIgnoreCase("Xbox One Console")) {
				div = doc.select("span[id=BestBuy-XboxOne]").first();
				urlLink = doc.select("a[id=BestBuy-XboxOne-URL]").first();
				
			} else if (product.equalsIgnoreCase("PlayStation 5")) {
				div = doc.select("span[id=BestBuy-PS5]").first();
				urlLink = doc.select("a[id=BestBuy-PS5-URL]").first();
				
			} else if (product.equalsIgnoreCase("PlayStation 5 Digital Edition")) {
				div = doc.select("span[id=BestBuy-PS5-Digital]").first();
				urlLink = doc.select("a[id=BestBuy-PS5-Digital-URL]").first();
				
			} else if (product.equalsIgnoreCase("Xbox Series X")) {
				div = doc.select("span[id=BestBuy-XBX]").first();
				urlLink = doc.select("a[id=BestBuy-XBX-URL]").first();
				
			} else if (product.equalsIgnoreCase("Xbox Series S")) {
				div = doc.select("span[id=BestBuy-XBS]").first();
				urlLink = doc.select("a[id=Amazon-XBS-URL]").first();
				
			} 
			
			div.text("IN STOCK - ");
			urlLink.attr("href", driver.getCurrentUrl());
			urlLink.text(driver.getCurrentUrl());
			div.attr("style", "color:green");
			
			BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(projectPath+"/target/ConsoleStockReport.html"), "UTF-8"));
			htmlWriter.write(doc.toString());
			htmlWriter.close();
			
			//driver.close();
			//driver.quit();
		}
	}
}
