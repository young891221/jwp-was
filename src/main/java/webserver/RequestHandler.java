package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import handler.RequestEngine;
import session.SessionManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private SessionManager sessionManager;

    RequestHandler(Socket connectionSocket, SessionManager sessionManager) {
        this.connection = connectionSocket;
        this.sessionManager = sessionManager;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            HttpRequest httpRequest = new HttpRequest(bufferedReader)
                    .setIfExistSession(sessionManager);
            logger.debug(httpRequest.toString());

            RequestEngine requestEngine = new RequestEngine();
            HttpResponse httpResponse = requestEngine.run(httpRequest);
            httpResponse.write(out);
        } catch (Exception e) {
            logger.error("main error", e);
        }
    }

}
