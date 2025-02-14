/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.http;

import org.junit.jupiter.api.Test;
import request.HttpMethod;
import request.HttpRequest;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class HttpRequestTest {

    @Test
    void read_header() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("GET /index.html HTTP/1.1\n" +
                "Host: www.nowhere123.com\n" +
                "Accept: image/gif, image/jpeg, */*\n" +
                "Accept-Language: en-us\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)"));
        HttpRequest httpRequest = new HttpRequest(bufferedReader);

        assertThat(httpRequest.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getHost()).isEqualTo(" www.nowhere123.com");
        assertThat(httpRequest.getAccept()).isEqualTo(" image/gif, image/jpeg, */*");
        assertThat(httpRequest.getAcceptLanguage()).isEqualTo(" en-us");
        assertThat(httpRequest.getAcceptEncoding()).isEqualTo(" gzip, deflate");
        assertThat(httpRequest.getUserAgent()).isEqualTo(" Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
    }

    @Test
    void invalid_header() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("GIT /index.html HTTP/1.1\n"));
        assertThrows(IllegalArgumentException.class, () -> new HttpRequest(bufferedReader));
    }
}
