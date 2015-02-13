package org.ly.report;

import com.google.common.base.Preconditions;
import com.google.gson.internal.LinkedHashTreeMap;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.*;

/**
 * Created by scott on 15-2-14.
 */

enum Provider {
    BAIDU("http://www.baidu.com"),
    QIHU("http://www.haosou.com");

    private String url;

    private Provider(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public static Provider fromString(String str) {
        Preconditions.checkNotNull(str);
        for (Provider p : Provider.values()) {
            if (p.getUrl().equals(str)) {
                return p;
            }
        }
        throw new RuntimeException("cannot parse string to enum:" + str);
    }
}

enum Position {
    TOP("top"), RIGHT("right"), CONTENT("content");
    private String position;

    private Position(String pos) {
        this.position = pos;
    }

    public String getPosition() {
        return this.position;
    }

    public static Position fromString(String str) {
        Preconditions.checkNotNull(str);
        for (Position p : Position.values()) {
            if (p.getPosition().equals(str)) {
                return p;
            }
        }
        throw new RuntimeException("cannot parse string to enum");
    }
}

public class ReportEntry {
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private String keyword;

    public Map<AddressInfo, List<String>> getUrls() {
        return urls;
    }

    public void setUrls(Map<AddressInfo, List<String>> urls) {
        this.urls = urls;
    }

    private Map<AddressInfo, List<String>> urls = new LinkedHashTreeMap<>();


    public ReportEntry(String key) {
        this.keyword = key;
    }

    public void addUrl(AddressInfo addrInfo, String url) {
        if (!urls.containsKey(addrInfo)) {
            urls.put(addrInfo, Arrays.asList(url));
        } else {
            urls.get(addrInfo).add(url);
        }
    }

    public void addUrls(AddressInfo addrInfo, Collection<String> urlList) {
        if (!urls.containsKey(addrInfo)) {
            urls.put(addrInfo, new ArrayList<>(urlList));
        } else {
            urls.get(addrInfo).addAll(urlList);
        }
    }

    public HSSFRow toRow() {
        throw new UnsupportedOperationException();
        //TODO:
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(keyword).append(",");

        urls.entrySet().forEach(
                entry ->
                        sb.append(entry.getKey().toString()).append(",").append(entry.getValue().size())
        );
        return sb.toString();
    }
}
