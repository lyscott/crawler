package org.ly.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.ly.persistence.DataEntry;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by scott on 15-2-13.
 */
public class QihuTopExtractor implements Extractor {
    @Override
    public List<String> extract(Document document, String keyword) {

        Elements elements = document.select("div[id=m-spread-left] cite");
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
                    return url;
                })
                .collect(Collectors.toList());
    }
}
