//TODO Notes:
//COMPLETED - Project will search for consoles including: PlayStation 5, PlayStation 5 Digital Edition, Xbox Series X, Xbox Series S
//COMPLETED - Create an HTML page displaying the stock status for each product in each store
//1) Take screenshots for the last step and place them in a folder specific to a store
//2) Locations will be within Toronto

//TAGS:
//@AllFeatures
//@BestBuyFeature
//@AmazonFeature

//IN PROGRESS:
//@EBGamesFeature

package StepDefinitions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

//import cucumber.api.*;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/Features", glue={"StepDefinitions"}, monochrome=true,
plugin = { 	"pretty",  "junit:target/JUnitReports/report.xml",
		"json:target/JSONReports/report.json",
"html:target/HTMLReports"},
tags="@AllFeatures")
public class TestRunner {
	
	public static WebDriver driver = null;
	public static String projectPath;
	public static Properties propertyFile;
	
	@BeforeClass
	public static void writeValuesInHTMLReport() throws IOException {
		
		//Load data from property file
		FileReader reader = new FileReader("db.properties");
	    propertyFile = new Properties();
	    propertyFile.load(reader);
		
	    //Before running everything (scenarios, steps, etc.) replace content of "TestReport.html" to default values
		projectPath = System.getProperty("user.dir");
		System.out.println("Project path is:" + projectPath);
		System.setProperty("webdriver.chrome.driver", projectPath+"/src/test/resources/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		
		String projectPath = System.getProperty("user.dir");
		File input = new File(projectPath+"/target/ConsoleStockReport_DefaultValues.html");
		Document doc = Jsoup.parse(input, "UTF-8", "");
		//System.out.println("Doc: \n" + doc.toString());

		BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(projectPath+"/target/ConsoleStockReport.html"), "UTF-8"));
		htmlWriter.write(doc.toString());
		htmlWriter.close();
	}

	@AfterClass
	public static void writeProductStockStatus_HTMLReport() throws IOException {
		
		driver.close();
		driver.quit();

		//Put completion date and time in report
		String projectPath = System.getProperty("user.dir");
		File input = new File(projectPath+"/target/ConsoleStockReport.html");
		Document doc = Jsoup.parse(input, "UTF-8", "");
		Element div = doc.select("span[id=date]").first();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String currentDate= dateFormat.format(date);
		//System.out.println("FINAL Current date and time is " + currentDate);
		div.text(currentDate);

		BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(projectPath+"/target/ConsoleStockReport.html"), "UTF-8"));
		htmlWriter.write(doc.toString());
		htmlWriter.close();
	}
}