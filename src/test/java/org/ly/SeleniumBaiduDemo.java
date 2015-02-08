/**
 * Aaron.ffp Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package org.ly;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * UI自动化功能测试脚本：元素定位示例-通过百度首页搜索框及登录链接实例演示
 *    1.通过 ID 查找元素
 *    2.通过 name 查找元素
 *    3.通过 xpath 查找元素
 *    4.通过 cssSelector 查找元素
 *    5.通过 linkText 查找元素
 *    6.通过 className 查找元素
 *    7.通过 partialLinkText 查找元素
 *    8.通过 tagName 查找元素（查找的结果为元素数组）
 * 
 * @author Aaron.ffp
 * @version V1.0.0: autoUISelenium main.java.aaron.sele.demo Selenium_LocationDemo.java, 2015-1-25 22:59:55 Exp $
 */
public class SeleniumBaiduDemo {
    
    private static WebDriver webdriver;
    private static String baseUrl;                  // 百度首页网址
    private static WebElement search;               // 百度搜索框-页面元素
    private static String element_id;               // 百度搜索框-ID
    private static String element_name;             // 百度搜索框-name
    private static String element_xpath;            // 百度搜索框-xpath
    private static String element_cssSelector;      // 百度搜索框-cssSelector
    private static String element_linkText;         // 登录链接-linkText
    private static String element_className;        // 百度搜索框-className
    private static String element_partialLinkText;  // 登录链接-partialLinkText
    private static String element_tagName;          // 百度搜索框-tagName
    
    /**
     * Chrome WebDriver 设置, 网址及搜索内容初始化, 打开 Chrome 浏览器
     * 
     * @author Aaron.ffp
     * @version V1.0.0: autoUISelenium main.java.aaron.sele.demo Selenium_LocationDemo.java chromeStart, 2015-1-25 23:03:38 Exp $
     *
     */
    public static void chromeStart(){
        /* 设定 chrome 启动文件的位置, 若未设定则取默认安装目录的 chrome */
        System.setProperty("webdriver.chrome.bin", "/usr/bin/chromedriver");
        /* 设定 chrome webdirver 的位置 */
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        /* 百度首页网址赋值 */
        baseUrl = "http://www.baidu.com/";
        
        element_id = "kw";                   // 百度搜索框-ID
        element_name = "wd";                 // 百度搜索框-name
        element_xpath = "//input[@id='kw']"; // 百度搜索框-xpath
        element_cssSelector = ".s_ipt";      // 百度搜索框-cssSelector
        element_linkText = "登录";             // 登录链接-linkText
        element_className = "s_ipt";         // 百度搜索框-className
        element_partialLinkText = "录";       // 登录链接-partialLinkText
        element_tagName = "input";           // 百度搜索框-tagName
        
        /* 启动 chrome 浏览器 */
        webdriver = new ChromeDriver(chromeOptions());
    }
    
    /**
     * 主方法入口
     * @author Aaron.ffp
     * @version V1.0.0: autoUISelenium main.java.aaron.sele.demo Selenium_LocationDemo.java main, 2015-1-25 22:59:55 Exp $
     * 
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        /* 启动 chrome */
        chromeStart();
        /* 打开百度 */
        webdriver.get(baseUrl);
        /* 等待加载 */
        webdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        /* 通过 ID 查找元素 */
        try {
            search = webdriver.findElement(By.id(element_id));
            search.clear();
            search.sendKeys("通过 ID 查找元素");
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        /* 通过 name 查找元素 */
        try {
            search = webdriver.findElement(By.name(element_name));
            search.clear();
            search.sendKeys("通过 name 查找元素");
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        /* 通过 xpath 查找元素 */
        try {
            search = webdriver.findElement(By.xpath(element_xpath));
            search.clear();
            search.sendKeys("通过 xpath 查找元素");
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        /* 通过 cssSelector 查找元素 */
        try {
            search = webdriver.findElement(By.cssSelector(element_cssSelector));
            search.clear();
            search.sendKeys("通过 cssSelector 查找元素");
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        } catch (InvalidElementStateException iese){
            iese.printStackTrace();
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        /* 通过 linkText 查找元素 */
        try {
            search = webdriver.findElement(By.linkText(element_linkText));
            System.out.println(search.getText());;
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        /* 通过 className 查找元素 */
        try {
            search = webdriver.findElement(By.className(element_className));
            search.clear();
            search.sendKeys("通过 className 查找元素");
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        /* 通过 partialLinkText 查找元素 */
        try {
            search = webdriver.findElement(By.partialLinkText(element_partialLinkText));
            System.out.println(search.getText());
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        }
        
        TimeUnit.SECONDS.sleep(5);
        
        /* 通过 tagName 查找元素
         * 此种方法返回的为一个 WebElement 数组，需要根据测试需求进行相应的处理
         */
        try {
            webdriver.get(baseUrl);
            
            List<WebElement> tagName = webdriver.findElements(By.tagName(element_tagName));
            
            for (WebElement webElement : tagName) {
                if ("wd".equals(webElement.getAttribute("name"))) {
                    webElement.sendKeys("通过 tagName 查找元素");
                }
            }
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        /* 关闭 chrome */
//        chromeQuit();
    }

    /**
     * 设置 Chrome 浏览器的启动参数, 设置启动后浏览器窗口最大化, 忽略认证错误警示
     * 
     * @author Aaron.ffp
     * @version V1.0.0: autoUISelenium main.java.aaron.sele.demo Selenium_LocationDemo.java chromeOptions, 2015-1-25 23:03:54 Exp $
     * 
     * @search = ChromeOptions Chrome 参数设置
     */
    public static ChromeOptions chromeOptions(){
         ChromeOptions options = new ChromeOptions();
         DesiredCapabilities capabilities = DesiredCapabilities.chrome();
         capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
         /* 浏览器最大化 */
         options.addArguments("--test-type", "--start-maximized");
         /* 忽略 Chrome 浏览器的认证错误 */
         options.addArguments("--test-type", "--ignore-certificate-errors");
         
         return options;
     }
    
    /**
     * 关闭并退出 Chrome
     * 
     * @author Aaron.ffp
     * @version V1.0.0: autoUISelenium main.java.aaron.sele.demo Selenium_LocationDemo.java chromeQuit, 2015-1-25 23:04:19 Exp $
     */
    public static void chromeQuit(){
        /* 关闭 chrome */
        webdriver.close();
        /* 退出 chrome */
        webdriver.quit();
    }
}