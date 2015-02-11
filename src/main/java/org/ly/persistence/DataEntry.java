package org.ly.persistence;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by scott on 15-2-11.
 */
@Entity
@Table(name = "DataEntry")
public class DataEntry {
    @Id @GeneratedValue
    private Long id;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    String position;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Column
    private String keyword;
    @Column
    private String url;
    @Column
    private Calendar createTime;
    @Column
    private String searchEngine;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public String getSearchEngine() {
        return searchEngine;
    }

    public void setSearchEngine(String searchEngine) {
        this.searchEngine = searchEngine;
    }

    public String toString() {
        return id.toString();
    }

}
