package di;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import di.common.ConstantesDI;
import okhttp3.Cookie;
import okhttp3.Cookie.Builder;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.*;
import java.util.Map.Entry;

public final class JavaNetCookieJar implements CookieJar {
    private final CookieHandler cookieHandler;

    public JavaNetCookieJar(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (this.cookieHandler != null) {
            List<String> cookieStrings = new ArrayList<>();

            for (Cookie cookie : cookies) {
                cookieStrings.add(cookie.toString());
            }

            Map<String, List<String>> multimap = Collections.singletonMap(ConstantesDI.SET_COOKIE, cookieStrings);

            try {
                this.cookieHandler.put(url.uri(), multimap);
            } catch (IOException var6) {
                Platform.get().log(5, ConstantesDI.SAVING_COOKIES_FAILED_FOR + url.resolve(ConstantesDI.LINK), var6);
            }
        }

    }

    public List<Cookie> loadForRequest(HttpUrl url) {
        Map<String, List<String>> headers = Collections.emptyMap();

        Map<String, List<String>> cookieHeaders;
        try {
            cookieHeaders = this.cookieHandler.get(url.uri(), headers);
        } catch (IOException var10) {
            Platform.get().log(5, ConstantesDI.LOADING_COOKIES_FAILED_FOR + url.resolve(ConstantesDI.LINK), var10);
            return Collections.emptyList();
        }

        List<Cookie> cookies = null;
        Iterator<Entry<String, List<String>>> var5 = cookieHeaders.entrySet().iterator();

        while (true) {
            Entry<String, List<String>> entry;
            String key;
            do {
                do {
                    if (!var5.hasNext()) {
                        return cookies != null ? Collections.unmodifiableList(cookies) : Collections.emptyList();
                    }

                    entry = var5.next();
                    key = entry.getKey();
                } while (!ConstantesDI.COOKIE.equalsIgnoreCase(key) && !ConstantesDI.COOKIE_2.equalsIgnoreCase(key));
            } while ((entry.getValue()).isEmpty());

            String header;
            for (Iterator<String> var8 = (entry.getValue()).iterator(); var8.hasNext(); cookies.addAll(this.decodeHeaderAsJavaNetCookies(url, header))) {
                header = var8.next();
                if (cookies == null) {
                    cookies = new ArrayList<>();
                }
            }
        }
    }

    private List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl url, String header) {
        List<Cookie> result = new ArrayList<>();
        int pos = 0;

        int pairEnd;
        for (int limit = header.length(); pos < limit; pos = pairEnd + 1) {
            pairEnd = Util.delimiterOffset(header, pos, limit, ConstantesDI.DELIMITERS);
            int equalsSign = Util.delimiterOffset(header, pos, pairEnd, ConstantesDI.DELIMITER);
            String name = Util.trimSubstring(header, pos, equalsSign);
            if (!name.startsWith(ConstantesDI.$)) {
                String value = equalsSign < pairEnd ? Util.trimSubstring(header, equalsSign + 1, pairEnd) : "";
                if (value.startsWith(ConstantesDI.SUFFIX) && value.endsWith(ConstantesDI.SUFFIX)) {
                    value = value.substring(1, value.length() - 1);
                }

                result.add((new Builder()).name(name).value(value).domain(url.host()).build());
            }
        }

        return result;
    }
}
