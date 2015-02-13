package org.ly.report;

import com.google.common.base.Preconditions;
import org.ly.persistence.DataEntry;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by scott on 15-2-13.
 */

class AddressInfo implements Comparable<AddressInfo> {
    private final Position position;
    private final Provider provider;

    AddressInfo(Position position, Provider provider) {
        this.position = position;
        this.provider = provider;
    }

    public Provider getProvider() {
        return provider;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public int compareTo(AddressInfo o) {
        if (o.provider.equals(this.provider)) {
            return this.position.compareTo(o.getPosition());
        } else {
            return o.provider.compareTo(o.getProvider());
        }
    }

    @Override
    public String toString() {
        return provider.name()+"-"+position.getPosition();
    }
}



public class ReportGenerator {
    public List<ReportEntry> dataToReport(Collection<DataEntry> des) {
        List<String> providers = Arrays.asList("http://www.baidu.com", "http://www.haosou.com");
        List<String> positions = Arrays.asList("top", "right", "content");
        List<String> keywordList = des.stream().map(de -> de.getKeyword()).distinct().collect(Collectors.toList());

        return keywordList.stream().map(keyword -> {
            ReportEntry re = new ReportEntry(keyword);
            List<DataEntry> keywordDataEntry = des.stream().filter(de -> de.getKeyword().equals(keyword)).collect(Collectors.toList());
            providers.forEach(provider -> positions.forEach(position -> {
                AddressInfo addressInfo = new AddressInfo(Position.fromString(position), Provider.fromString(provider));
                List<String> urls = keywordDataEntry.stream().filter(de -> de.getPosition().equals(position) && de.getSearchEngine().equals(provider)).map(de -> de.getUrl()).collect(Collectors.toList());
                re.addUrls(addressInfo, urls);
            }));
            return re;
        }).collect(Collectors.toList());
    }
}

