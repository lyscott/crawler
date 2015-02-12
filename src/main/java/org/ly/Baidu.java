package org.ly;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.ly.persistence.DataEntry;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    public void getTheAd(String htmlSource, String keyword){
        Document htmlDocument = Jsoup.parse(htmlSource);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CWL");
        EntityManager em = emf.createEntityManager();
        List<String> topElements = this.exactTopAds(htmlDocument,em,keyword);
        logger.info("baidu top Ads");
        logger.info("{}", topElements);
        List<String> rightElements = this.exactRightAds(htmlDocument,em,keyword);
        logger.info("baidu right Ads");
        logger.info("{}", rightElements);
        List<String> contentElements = this.exactContentAds(htmlDocument,em,keyword);
        logger.info("baidu contentElements Ads");
        logger.info("{}", contentElements);
        em.close();
        emf.close();
    }
    public List<String> exactTopAds(Document jsoup,EntityManager em,String keyword){
        //with error currently
        Elements elements = jsoup.select("table[id~=(.00.)] a[class=ipYENq]");
        /*List<Elements> elementDivs = elements.stream()
                .map((e) -> e.select("#tools_"))
                .collect(Collectors.toList());*/

        return elements.stream()
                .map((e) -> {
                	String url = "";
                	url = e.getElementsByTag("span").text();
                	DataEntry de = new DataEntry();
                    de.setUrl(url);
                    de.setPosition("Top");
                    de.setCreateTime(Calendar.getInstance());
                    de.setSearchEngine("baidu");
                    de.setKeyword(keyword);
                    em.getTransaction().begin();
                    em.persist(de);
                    em.getTransaction().commit();
                	return url;
                })
                .collect(Collectors.toList());
    }
    public List<String> exactRightAds(Document jsoup,EntityManager em,String keyword){
        Elements elements = jsoup.select("#ec_im_container font[size=-1][class~=^()");
       
        return elements.stream()
                .map((e) -> {
                    String url = e.text();
                    DataEntry de = new DataEntry();
                    de.setUrl(url);
                    de.setPosition("Right");
                    de.setCreateTime(Calendar.getInstance());
                    de.setSearchEngine("baidu");
                    de.setKeyword(keyword);
                    em.getTransaction().begin();
                    em.persist(de);
                    em.getTransaction().commit();
                    return url;
                })
                .collect(Collectors.toList());
    }
    public List<String> exactContentAds(Document jsoup,EntityManager em,String keyword){
         Elements elements = jsoup.select("div[id~=(30.)] div:eq(2) span:eq(0)");
        
        return elements.stream()
                .map((e) -> {
                	String url = e.text();
                    DataEntry de = new DataEntry();
                    de.setUrl(url);
                    de.setPosition("Content");
                    de.setCreateTime(Calendar.getInstance());
                    de.setSearchEngine("baidu");
                    de.setKeyword(keyword);
                    em.getTransaction().begin();
                    em.persist(de);
                    em.getTransaction().commit();
                    return url;
                })
                .collect(Collectors.toList());
    }
} 
