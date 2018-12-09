package utils;

import utils.interfaces.IRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request implements IRequest {
    private URL url;
    private InputStream body;
    private int statusCode;

    public Request(URL url) {
        this.url = url;
    }

    @Override
    public Response call() {
        writeResult();
        return new Response(body, url.toString(), statusCode);
    }

    private void writeResult() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            body = connection.getInputStream();
            statusCode = connection.getResponseCode();
        } catch (IOException e) {
            statusCode = 500;
            body = null;
        }
    }
}
