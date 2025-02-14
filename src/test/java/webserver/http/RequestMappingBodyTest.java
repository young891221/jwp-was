/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.http;

import org.junit.jupiter.api.Test;
import request.HttpRequest;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class RequestMappingBodyTest {

    @Test
    public void read_request_body() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"));
        HttpRequest httpRequest = new HttpRequest(bufferedReader);
        String body = httpRequest.getBody();

        assertThat(body).isNotEmpty();
        assertThat(httpRequest.getBodyMap()).containsKeys("userId", "password", "name");
    }
}
