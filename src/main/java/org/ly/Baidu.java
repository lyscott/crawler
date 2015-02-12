package org.ly;

import org.ly.extractor.BaiduContentExtractor;
import org.ly.extractor.BaiduRightExtractor;
import org.ly.extractor.BaiduTopExtractor;
import org.ly.extractor.Extractor;

import java.util.HashMap;
import java.util.Map;

public class Baidu extends Crawler{



    private static Map<String, Extractor> baiduExtractors = new HashMap<>();
    static {
        baiduExtractors.put("top", new BaiduTopExtractor());
        baiduExtractors.put("right", new BaiduRightExtractor());
        baiduExtractors.put("content", new BaiduContentExtractor());
    }

    public Baidu() {
        super(baiduExtractors);
        searchEngineUrl = "http://www.baidu.com";
        inputBlockName = "kw";
        readyCheckBlock = "content_left";
    }

} 
