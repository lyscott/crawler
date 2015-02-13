package org.ly;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ly.extractor.Extractor;
import org.ly.persistence.DataEntry;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by scott on 15-2-12.
 */
public abstract class Crawler {
    protected String searchEngineUrl = "";
    protected String inputBlockName = "";
    protected String readyCheckBlock = "";
    protected static Logger LOGGER = LoggerFactory.getLogger(Crawler.class);

    private Map<String, Extractor> extractors; // Position -> Extractor
    private WebDriver webDriver;

    public Crawler(Map<String, Extractor> extractors) {
        System.getProperties().setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        this.extractors = extractors;
        this.webDriver = new ChromeDriver();
        webDriver.manage().window().setSize(new Dimension(1920, 1080));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
    }

    public String getSearchedHtmlSource(String keyWord, int counter) throws Exception {
        webDriver.get("about:blank");
        webDriver.get(this.searchEngineUrl);
        if (counter >= 3) {
            throw new Exception("Retry over limit");
        }
        try {
            WebElement searchBlock = webDriver.findElement(By.id(this.inputBlockName));
            searchBlock.click();
            searchBlock.clear();
            searchBlock.sendKeys(keyWord);
            searchBlock.sendKeys(Keys.RETURN);
            //webDriver.quit();
            new WebDriverWait(webDriver, 5).until(
                    ExpectedConditions.presenceOfElementLocated(By.id(this.readyCheckBlock)));
            System.out.println("got html source by search key word " + keyWord);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            webDriver.close();
            webDriver = new ChromeDriver();
            //return getSearchedHtmlSource(keyWord, ++counter);
            throw e;
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
