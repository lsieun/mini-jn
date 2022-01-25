package boot.filter;

import java.util.HashMap;
import java.util.Map;

public class LinkedTreeMapFilter {
    private static final Map<Object, Object> map = new HashMap<>();

    static {
        map.put("licenseeName","Tom");
        map.put("gracePeriodDays","30");
        map.put("paidUpTo","2022-12-31");
    }

    public static Object testPut(Object k, Object v) {
        if (null == k) {
            return v;
        }

        if (map.containsKey(k)) {
            return map.get(k);
        }

        return v;
    }
}