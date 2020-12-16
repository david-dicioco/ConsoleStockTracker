package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;

public class BestBuyPage {
	
	protected WebDriver driver;
	
	//Retrieve these values from a property file
	private String bestBuyURL = "https://www.bestbuy.ca/en-ca";
	private String bestBuy_ProductOutofStock_Text = "Sold out"; //for PS5 -> "sold out"
	private String bestBuy_ProductInStock_Text = "Available to ship"; //Include this as well -> "Available for free store pickup"
	private By bestBuySearchBox_Name = By.name("search");
	private By bestBuySearchButton_ClassName = By.className("searchButton_T4-BG");
	private By bestBuyLink_PS4_PartialLinkText = By.partialLinkText("PlayStation 4 1TB Console");
	private By bestBuyLink_PS5_PartialLinkText = By.partialLinkText("PlayStation 5 Console");
	private By bestBuyLink_PS5_Digital_PartialLinkText = By.partialLinkText("PlayStation 5 Digital Edition Console");
	private By bestBuyLink_XboxOne_PartialLinkText = By.partialLinkText("Xbox One S 1TB Console");
	private By bestBuyLink_XboxSeriesX_PartialLinkText = By.partialLinkText("Xbox Series X 1TB");
	private By bestBuyLink_XboxSeriesS_PartialLinkText = By.partialLinkText("Xbox Series S 512GB Console");
	
	public BestBuyPage (WebDriver driver) {
		this.driver = driver;
		if(!driver.getCurrentUrl().equals(bestBuyURL)){
			throw new IllegalStateException("This is not the BestBuy Homepage. The current page url is "+ driver.getCurrentUrl());
		}
	}
	
	public void enterProduct(String product) {
		driver.findElement(bestBuySearchBox_Name).sendKeys(product);
	}
	
	public void clickSearchButton() {
		driver.findElement(bestBuySearchButton_ClassName).click();
	}
	
	/*public boolean verifyProductResultsPage(String product) {
		return driver.getPageSource().contains("results for") && driver.getPageSource().contains("\""+ product + "\"");
	}*/
	
	public void clickProductLink(String product) {
		
		if (product.equalsIgnoreCase("PlayStation 4")) {
			driver.findElement(bestBuyLink_PS4_PartialLinkText).click();
		} else if (product.equalsIgnoreCase("Xbox One Console")) {
			driver.findElement(bestBuyLink_XboxOne_PartialLinkText).click();
		} else if (product.equalsIgnoreCase("PlayStation 5")) {
			driver.findElement(bestBuyLink_PS5_PartialLinkText).click();
		} else if (product.equalsIgnoreCase("PlayStation 5 Digital Edition")) {
			driver.findElement(bestBuyLink_PS5_Digital_PartialLinkText).click();
		} else if (product.equalsIgnoreCase("Xbox Series X")) {
			driver.findElement(bestBuyLink_XboxSeriesX_PartialLinkText).click();
		} else if (product.equalsIgnoreCase("Xbox Series S")) {
			driver.findElement(bestBuyLink_XboxSeriesS_PartialLinkText).click();
		} else {
			System.out.println("No such link available!");
			Assert.fail(); //if product link is not available, fail this step
		}
	}
	
	public boolean verifyProductOutofStock() throws Exception {
		
		String bodyText = driver.findElement(By.tagName("body")).getText();
		//System.out.println("BodyText:" +bodyText);
		//Boolean verifyStock = bodyText.contains(bestBuy_ProductInStock_Text);
		//System.out.println("Is ProductInStock_Text present?: " +verifyStock);
	
		//if (driver.getPageSource().contains(bestBuy_ProductOutofStock_Text) && !driver.getPageSource().contains(bestBuy_ProductInStock_Text)) {
		//if text in current website contains ProductOutofStock_Text and doesn't contain ProductInStock_Text, then ProductOutofStock is true
		if (bodyText.contains(bestBuy_ProductInStock_Text)) {
			return false;
		} else if ((bodyText.contains(bestBuy_ProductOutofStock_Text) || bodyText.contains(bestBuy_ProductOutofStock_Text.toLowerCase()))) {
			return true;
		} else {
			throw new Exception("Cannot find either text for ProductInStock or ProductOutofStock");
		}
	}

}
