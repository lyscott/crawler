package org.ly.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by scott on 16-2-13.
 */
public class BaiduContentExtractor implements Extractor {

    @Override
    public List<String> extract(Document document, String keyword) {
        Elements elements = document.select("div[id~=(30.)] div:eq(2) span:eq(0)");
        return elements.stream()
                .map((e) -> {
                    String url = e.text();
                    return url;
                })
                .collect(Collectors.toList());
    }
}
