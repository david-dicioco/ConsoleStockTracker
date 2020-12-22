//Amazon
/*You can add steps such as:
1)Log into account
2)Set Postal Code
- then search for the product
 */

//Later change the names of methods here

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

import Pages.AmazonPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AmazonSteps {

	private WebDriver driver = null;
	private AmazonPage amazonPage;
	private Document doc;
	private String projectPath;
	private int defaultSleepTimer = 3000;
	private Element div;
	private Element urlLink;

	@Given("Browser is open - Amazon")
	public void browser_is_open_amazon() throws IOException {

		/*projectPath = System.getProperty("user.dir");
		System.out.println("Project path is:" + projectPath);

		System.setProperty("webdriver.chrome.driver", projectPath+"/src/test/resources/drivers/chromedriver.exe");

		driver = new ChromeDriver();*/		
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	//may not need
		//driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);	
		
		driver = TestRunner.driver;
		projectPath = TestRunner.projectPath;
	}

	@And("User enters Amazon homepage")
	public void user_enters_amazon_page() throws InterruptedException {

		driver.navigate().to("https://www.amazon.ca/");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@When("^User inputs a (.*) in Amazon search box$")
	public void user_enters_a_text_in_search_box_amazon(String product) throws InterruptedException {

		amazonPage = new AmazonPage(driver);
		amazonPage.enterProduct(product);
	}

	@And("User clicks Amazon search button")
	public void user_clicks_amazon_search_button() throws InterruptedException {

		amazonPage.clickSearchButton();
		Thread.sleep(defaultSleepTimer);
	}

	@And("^User is on Amazon (.*) results page$")
	public void user_is_on_product_results_page(String product) throws InterruptedException {

		if(!amazonPage.verifyProductResultsPage(product)) {
			System.out.println("User not in " + product + " results page");
			//driver.close();
			//driver.quit();
			Assert.fail();
		}
	}

	@And("^User clicks Amazon (.*) link$")
	public void click_product_link(String product) throws InterruptedException {

		amazonPage.clickProductLink(product);
		Thread.sleep(defaultSleepTimer);
	}

	//TODO: Put results for product stock in custom HTML
	@Then("^User verifies (.*) stock in Amazon$")
	public void verify_product_is_in_stock(String product) throws Exception {
		
		File input = new File(projectPath+"/target/ConsoleStockReport.html");
		doc = Jsoup.parse(input, "UTF-8", "");
		//System.out.println("TestReport Title: " + doc.title());

		if(amazonPage.verifyProductOutOfStock()) {
			
			System.out.println(" =========== Product is: OUT OF STOCK =========== ");
			
			if (product.equalsIgnoreCase("PlayStation 4")) {
				div = doc.select("span[id=Amazon-PS4]").first();
			} else if (product.equalsIgnoreCase("Xbox One Console")) {
				div = doc.select("span[id=Amazon-XboxOne]").first();
			} else if (product.equalsIgnoreCase("PlayStation 5")) {
				div = doc.select("span[id=Amazon-PS5]").first();
			} else if (product.equalsIgnoreCase("PlayStation 5 Digital Edition")) {
				div = doc.select("span[id=Amazon-PS5-Digital]").first();
			} else if (product.equalsIgnoreCase("Xbox Series X")) {
				div = doc.select("span[id=Amazon-XBX]").first();
			} else if (product.equalsIgnoreCase("Xbox Series S")) {
				div = doc.select("span[id=Amazon-XBS]").first();
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
				div = doc.select("span[id=Amazon-PS4]").first();
				urlLink = doc.select("a[id=Amazon-PS4-URL]").first();
			} else if (product.equalsIgnoreCase("Xbox One Console")) {
				div = doc.select("span[id=Amazon-XboxOne]").first();
				urlLink = doc.select("a[id=Amazon-XboxOne-URL]").first();
			} else if (product.equalsIgnoreCase("PlayStation 5")) {
				div = doc.select("span[id=Amazon-PS5]").first();
				urlLink = doc.select("a[id=Amazon-PS5-URL]").first();
			} else if (product.equalsIgnoreCase("PlayStation 5 Digital Edition")) {
				div = doc.select("span[id=Amazon-Amazon-PS5-Digital]").first();
				urlLink = doc.select("a[id=Amazon-PS5-Digital-URL]").first();
			} else if (product.equalsIgnoreCase("Xbox Series X")) {
				div = doc.select("span[id=Amazon-XBX]").first();
				urlLink = doc.select("a[id=Amazon-XBX-URL]").first();
			} else if (product.equalsIgnoreCase("Xbox Series S")) {
				div = doc.select("span[id=Amazon-XBS]").first();
				urlLink = doc.select("a[id=Amazon-XBS-URL]").first();
			} 
			
			div.text("IN STOCK - ");
			urlLink.attr("href", driver.getCurrentUrl());
			urlLink.text(driver.getCurrentUrl());
			div.attr("style", "color:green");
			
			BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(projectPath+"/target/ConsoleStockReport.html"), "UTF-8"));
			htmlWriter.write(doc.toString());
			htmlWriter.close();
		}
		
		//driver.close();
		//driver.quit();
	}
}
