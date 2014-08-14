import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;


public class BundleData {
    public List<NameValuePair> createBundleAddons(List<OrderLineDO> lineItems) {
        return newArrayList(getBundleAddons(newArrayList(getCommunityWiFiLineItems(lineItems))));
    }

    private Collection<NameValuePair> getBundleAddons(List<OrderLineDO> communityWiFiLineItems) {
        return transform(communityWiFiLineItems, new Function<OrderLineDO, NameValuePair>() {
            int index = 1;
            @Override
            public NameValuePair apply(OrderLineDO orderLineDO) {
                return new NameValuePair("Bundle Addon" + index++, "Community WiFi");
            }
        });
    }

    private Collection<OrderLineDO> getCommunityWiFiLineItems(List<OrderLineDO> lineItems) {
        return filter(lineItems, new Predicate<OrderLineDO>(){
            @Override
            public boolean apply(OrderLineDO orderLineDO) {
                return orderLineDO.getLineItemType() == LineTypeDO.COMMUNITY_WIFI;
            }
        });
    }


    public static void main(String[] args) {
        BundleData bundleData = new BundleData();

        List<OrderLineDO> dos = newArrayList();
        dos.add(new OrderLineDO(LineTypeDO.COMMUNITY_WIFI));
        dos.add(new OrderLineDO(LineTypeDO.COMMUNITY_WIFI));
        dos.add(new OrderLineDO(LineTypeDO.NONE));
        dos.add(new OrderLineDO(LineTypeDO.NONE));

        List<NameValuePair> pairs = bundleData.createBundleAddons(dos);

        System.err.println(pairs.get(0).getName() + ":" + pairs.get(0).getValue());
        System.err.println(pairs.get(1).getName() + ":" + pairs.get(1).getValue());
    }
}


enum LineTypeDO implements Predicate<Object> {
    NONE, COMMUNITY_WIFI;

    @Override
    public boolean apply(Object o) {
        return true;
    }
}

class OrderLineDO {
    private LineTypeDO lineType;

    public OrderLineDO(LineTypeDO lineType) {
        this.lineType = lineType;
    }

    public LineTypeDO getLineItemType() {
        return this.lineType;
    }
}

class NameValuePair {
    private String name;
    private String value;

    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return this.value;
    }
}
