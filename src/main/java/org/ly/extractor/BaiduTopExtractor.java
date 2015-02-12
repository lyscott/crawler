package org.ly.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by scott on 15-2-13.
 */
public class BaiduTopExtractor implements Extractor {
    @Override
    public List<String> extract(Document document, String keyword) {
        Elements elements = document.select("table[id~=(.00.)] div[id=tools_],span[id^=icon_]");

        return elements.stream()
                .map((e) -> {
                    String url = e.previousElementSibling().getElementsByTag("span").text();
                    return url;
                })
                .collect(Collectors.toList());
    }
}
