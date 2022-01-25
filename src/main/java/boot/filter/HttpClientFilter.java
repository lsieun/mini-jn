package boot.filter;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpClientFilter {
    public static URL testURL(URL url) throws IOException {
        if (null == url) {
            return null;
        }

        String str = url.toString();
        if ("https://account.jetbrains.com/lservice/rpc/validateKey.action".equals(str)) {
            throw new SocketTimeoutException("connect timed out");
        }

        return url;
    }
}