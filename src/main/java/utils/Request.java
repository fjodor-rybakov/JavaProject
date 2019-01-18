package utils;

import utils.interfaces.IRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Request implements IRequest {
    private URL url;
    private InputStream body;
    private int statusCode;

    public Request(URL url) {
        this.url = url;
    }

    public Response call() {
        writeResult();
        return new Response(body, url.toString(), statusCode);
    }

    private void writeResult() {
        try {
            HttpURLConnection.setFollowRedirects(false);
            URLConnection connection =  url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.connect();
            body = connection.getInputStream();
            statusCode = ((HttpURLConnection) connection).getResponseCode();
        } catch (IOException e) {
            statusCode = 500;
            body = null;
        }
    }
}
