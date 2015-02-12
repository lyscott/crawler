package org.ly;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ly.extractor.Extractor;
import org.ly.persistence.DataEntry;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by scott on 15-2-12.
 */
public abstract class Crawler {

    protected static Logger LOGGER = LoggerFactory.getLogger(Crawler.class);
    protected String searchEngineUrl = "";
    protected String inputBlockName = "";
    protected String readyCheckBlock = "";

    private Map<String,Extractor> extractors; // Position -> Extractor
    private WebDriver webDriver;

    public Crawler(Map<String, Extractor> extractors) {
        System.getProperties().setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
        this.extractors = extractors;
        this.webDriver =  new ChromeDriver();
        webDriver.manage().window().setSize(new Dimension(920, 180));
    }

    public String getSearchedHtmlSource(String keyWord) {
        webDriver.get(this.searchEngineUrl);
        WebElement searchBlock = webDriver.findElement(By.id(this.inputBlockName));
        searchBlock.click();
        searchBlock.clear();
        searchBlock.sendKeys(keyWord);
        searchBlock.sendKeys(Keys.RETURN);
        try {
            new WebDriverWait(webDriver, 5).until(
                    ExpectedConditions.presenceOfElementLocated(By.id(this.readyCheckBlock)));
            System.out.print("got html source by search key word "+keyWord);
        } catch (Exception e) {
            System.out.print("exception occurs, search key word "+keyWord);
            LOGGER.error(e.getMessage());
        }
        String htmlSourceString = webDriver.getPageSource();
        return htmlSourceString;
    }

    public List<DataEntry> crawl(String htmlSource, String keyword) {
        Document htmlDocument = Jsoup.parse(htmlSource);
        return this.extractors.entrySet().stream().map((e) -> {
            String position = e.getKey();
            Extractor extractor = e.getValue();
            List<DataEntry> des = extractor.extract(htmlDocument, keyword).stream().map(
                    (url) ->
                            new DataEntry(position,
                                    keyword,
                                    url,
                                    searchEngineUrl,
                                    new Timestamp(System.currentTimeMillis()))).collect(Collectors.toList());
            return des;

        }).collect(Collectors.toList())
                .stream().flatMap(e -> e.stream()).collect(Collectors.toList());
    }

    public void close() {
        webDriver.close();
    }

}
