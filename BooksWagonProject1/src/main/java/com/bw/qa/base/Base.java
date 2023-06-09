package com.bw.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Listeners;

import com.bw.qa.pages.NewArrivalsPage;
import com.bw.qa.util.TestUtil;
import com.bw.qa.util.CustomListener;

@Listeners(CustomListener.class)
public class Base {
	public static WebDriver driver;
	public static Properties prop;
	

	public static Logger log = Logger.getLogger(Base.class);
	
	public Base() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					"D:\\Bridgelabz Program\\TestingAPI\\BooksWagonProject1\\src\\main\\java\\com\\bw\\qa\\config\\config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {
		String browserName = prop.getProperty("browser");
		
		if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"D:\\Bridgelabz Program\\TestingAPI\\BooksWagonProject1\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();

		} else if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"D:\\Bridgelabz Program\\TestingAPI\\BooksWagonProject1\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}


		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));

	
	}
	
	public void takeScreenshotAtEndOfTest(String testMethodName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			
			FileUtils.copyFile(scrFile, new File("D:\\Bridgelabz Program\\TestingAPI\\BooksWagonProject1\\screenshots\\"+testMethodName+"_"+".png"));
		}catch(IOException exp) {
			exp.printStackTrace();
		}
	}

}
