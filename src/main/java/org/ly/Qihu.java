package org.ly;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.ly.extractor.*;
import org.ly.persistence.DataEntry;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Qihu extends Crawler{

    public Qihu() {
        super(qihuExtractors);
        searchEngineUrl = "http://www.haosou.com";
        inputBlockName = "input";
        readyCheckBlock = "main";
    }

    private static Map<String, Extractor> qihuExtractors = new HashMap<>();
    static {
        qihuExtractors.put("top", new QihuTopExtractor());
        qihuExtractors.put("right", new QihuRightExtractor());
        qihuExtractors.put("content", new QihuContentExtractor());
    }
} 
