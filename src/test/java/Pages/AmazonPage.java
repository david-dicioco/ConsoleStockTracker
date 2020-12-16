package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class AmazonPage {
	
	protected WebDriver driver;
	
	//Retrieve these values from a property file
	private String amazonURL = "https://www.amazon.ca/";
	private String amazon_ProductOutofStock_Text = "Currently unavailable.";
	private String amazon_ProductInStock_Text = "Available from these sellers.";
	private By amazonSearchBox_ID = By.id("twotabsearchtextbox");
	private By amazonSearchButton_ClassName = By.className("nav-search-submit");
	private By amazonLink_PS4_LinkText = By.linkText("PlayStation 4 - 1TB Slim - Console Edition");
	private By amazonLink_XboxOne_LinkText = By.partialLinkText("Xbox One S 1TB Console - Xbox One S Edition");
	private By amazonLink_PS5_LinkText = By.linkText("PlayStation 5 Console - PlayStation 5 Edition");
	private By amazonLink_PS5_Digital_LinkText = By.linkText("PlayStation 5 Digital Edition");
	private By amazonLink_XboxSeriesX_LinkText = By.linkText("Xbox Series X");
	private By amazonLink_XboxSeriesS_LinkText = By.linkText("Xbox Series S");
	
	public AmazonPage (WebDriver driver) {
		this.driver = driver;
		if(!driver.getCurrentUrl().equals(amazonURL)){
			throw new IllegalStateException("This is not the Amazon Homepage. The current page url is "+ driver.getCurrentUrl());
		}
	}
	
	public void enterProduct(String product) {
		driver.findElement(amazonSearchBox_ID).sendKeys(product);	
	}
	
	public void clickSearchButton() {
		driver.findElement(amazonSearchButton_ClassName).click();
	}
	
	public boolean verifyProductResultsPage(String product) {
		return driver.getPageSource().contains("results for") && driver.getPageSource().contains("\""+ product + "\"");
	}
	
	public void clickProductLink(String product) {
		
		if (product.equalsIgnoreCase("PlayStation 4")) {
			driver.findElement(amazonLink_PS4_LinkText).click();
		} else if (product.equalsIgnoreCase("Xbox One Console")) {
			driver.findElement(amazonLink_XboxOne_LinkText).click();
		} else if (product.equalsIgnoreCase("PlayStation 5")) {
			driver.findElement(amazonLink_PS5_LinkText).click();
		} else if (product.equalsIgnoreCase("PlayStation 5 Digital Edition")) {
			driver.findElement(amazonLink_PS5_Digital_LinkText).click();
		} else if (product.equalsIgnoreCase("Xbox Series X")) {
			driver.findElement(amazonLink_XboxSeriesX_LinkText).click();
		} else if (product.equalsIgnoreCase("Xbox Series S")) {
			driver.findElement(amazonLink_XboxSeriesS_LinkText).click();
		} else {
			System.out.println("No such link available!");
			Assert.fail(); //if product link is not available, fail this step
		}
	}
	
	public boolean verifyProductOutOfStock() throws Exception {
		
		String bodyText = driver.findElement(By.tagName("body")).getText();
		//System.out.println("BodyText:" +bodyText);
		//Boolean verifyStock = bodyText.contains(amazon_ProductInStock_Text);
		//System.out.println("Is ProductInStock_Text present?: " +verifyStock);
	
		//return driver.getPageSource().contains(amazon_ProductOutofStock_Text);
		if (bodyText.contains(amazon_ProductInStock_Text)) {
			return false;
		} else if (bodyText.contains(amazon_ProductOutofStock_Text)) {
			return true;
		} else {
			throw new Exception("Cannot find either text for ProductInStock or ProductOutofStock");
		}
	}
	
	//Getters and Setters
	public String getAmazonURL() {
		return amazonURL;
	}

	public void setAmazonURL(String amazonURL) {
		this.amazonURL = amazonURL;
	}
	
}
