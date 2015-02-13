package org.ly.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by scott on 15-2-13.
 */
public class BaiduTopExtractor implements Extractor {
    @Override
    public List<String> extract(Document document, String keyword) {
        List<Element> elements = document.select("table[id~=(.00.)]").stream().map((table) -> {
            Elements es = table.select("div[id=tools_]");
            if(es.size() == 0) {
                es = table.getElementsByClass("icons");
            }
            return es;
        }).collect(Collectors.toList()).stream().flatMap(e->e.stream()).collect(Collectors.toList());

        return elements.stream()
                .map((e) -> {
                    String url = e.previousElementSibling().getElementsByTag("span").text();
                    return url;
                })
                .collect(Collectors.toList());
    }
}
