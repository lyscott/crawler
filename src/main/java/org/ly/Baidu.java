package org.ly;

import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Baidu {
  
	private static String searchEngineUrl = "http://www.baidu.com";
	private static Logger logger = LoggerFactory.getLogger(Baidu.class);
	
	public String getSearchedHtmlSource(String keyWord) {
		System.getProperties().setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().window().setSize(new Dimension(1920, 1080));
		webDriver.get(Baidu.searchEngineUrl);

		 WebElement searchBlock = webDriver.findElement(By.id("kw"));
		 searchBlock.click();
		 searchBlock.clear();
		 searchBlock.sendKeys(keyWord);
		 searchBlock.sendKeys(Keys.RETURN);
		 try {
			 new WebDriverWait(webDriver, 15).until(
					    ExpectedConditions.presenceOfElementLocated(By.id("content_left")));
			 System.out.print("got html source by search key word "+keyWord);
		 } catch (Exception e) {
			 System.out.print("exception occurs, search key word "+keyWord);
			 logger.error(e.getMessage());
		 }
		 String htmlSourceString = webDriver.getPageSource();
//		 logger.info(htmlSourceString);
		 return htmlSourceString;
	}
	
	public void getTheTopAd(String htmlSource){
		Document htmlDocument = Jsoup.parse(htmlSource); 
		List<String> elements = this.exact(htmlDocument);
		logger.info("{}", elements);
	}
	public List<String> exact(Document jsoup){
		Elements elements = jsoup.select("div[id~=tools_(.00.)]");
		return elements.stream()
                .map((e) -> e.previousElementSibling().getElementsByTag("span").text())
                .collect(Collectors.toList());
	}
} 
