package org.ly;

import java.util.Calendar;
import java.util.Date;
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

public class QiHu {
  
	private static String searchEngineUrl = "http://www.haosou.com";
	private static Logger logger = LoggerFactory.getLogger(QiHu.class);
	
	public String getSearchedHtmlSource(String keyWord) {
		System.getProperties().setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().window().setSize(new Dimension(1920, 1080));
		webDriver.get(QiHu.searchEngineUrl);

		 WebElement searchBlock = webDriver.findElement(By.id("input"));
		 searchBlock.click();
		 searchBlock.clear();
		 searchBlock.sendKeys(keyWord);
		 searchBlock.sendKeys(Keys.RETURN);
		 try {
			 new WebDriverWait(webDriver, 5).until(
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
        logger.info("qihu top Ads");
        logger.info("{}", topElements);
        List<String> rightElements = this.exactRightAds(htmlDocument,em,keyword);
        logger.info("qihu right Ads");
        logger.info("{}", rightElements);
        List<String> contentElements = this.exactContentAds(htmlDocument,em,keyword);
        logger.info("qihu contentElements Ads");
        logger.info("{}", contentElements);
        em.close();
        emf.close();
    }
    public List<String> exactTopAds(Document jsoup,EntityManager em,String keyword){
        //with error currently
        Elements elements = jsoup.select("div[id=m-spread-left] cite");

        return elements.stream()
                .map((e) -> {
                    int telIndex = e.text().indexOf("/TEL:");

                    String url = "";
                    if(telIndex!=-1){
                        url = e.text().substring(0,telIndex);
                    }
                    else{
                        url = e.text();
                    }
                    DataEntry de = new DataEntry();
                    de.setUrl(url);
                    de.setPosition("Top");
                    de.setCreateTime(Calendar.getInstance());
                    de.setSearchEngine("奇虎");
//                    de.setKeyword(keyword);
                    em.getTransaction().begin();
                    em.persist(de);
                    em.getTransaction().commit();
                    return url;
                })
                .collect(Collectors.toList());
    }
    public List<String> exactRightAds(Document jsoup,EntityManager em,String keyword){
        //with error currently
        Elements elements = jsoup.select("div[id=right_show] ul cite");
        /*List<Elements> elementDivs = elements.stream()
                .map((e) -> e.select("#tools_"))
                .collect(Collectors.toList());*/

        return elements.stream()
                .map((e) -> {
                    String url = "";
                    if (e.text().indexOf("http://e.360.cn")==-1) {
                        url = e.text();
                        DataEntry de = new DataEntry();
                        de.setUrl(url);
                        de.setPosition("Right");
                        de.setCreateTime(Calendar.getInstance());
                    de.setSearchEngine("奇虎");
//                    de.setKeyword(keyword);
                        em.getTransaction().begin();
                        em.persist(de);
                        em.getTransaction().commit();
                    } else {
                        url = "1111";
                    }
                    return url;
                }).collect(Collectors.toList());
    }
    public List<String> exactContentAds(Document jsoup,EntityManager em,String keyword){
        //with error currently
        Elements elements = jsoup.select("div[id=sunrise] a");
        /*List<Elements> elementDivs = elements.stream()
                .map((e) -> e.select("#tools_"))
                .collect(Collectors.toList());*/

        return elements.stream()
                .map((e) -> {
                    String url = e.text();
                    DataEntry de = new DataEntry();
                    de.setUrl(url);
                    de.setPosition("Content");
                    de.setCreateTime(Calendar.getInstance());
                    de.setSearchEngine("奇虎");
//                    de.setKeyword(keyword);
                    em.getTransaction().begin();
                    em.persist(de);
                    em.getTransaction().commit();
                    return url;
                })
                .collect(Collectors.toList());
    }
} 
