package utils;

import utils.interfaces.IResponse;

import java.io.InputStream;

public class Response implements IResponse {
    private InputStream body;
    private int statusCode;
    private String url;

    Response(InputStream body, String url, int statusCode) {
        this.body = body;
        this.url = url;
        this.statusCode = statusCode;
    }

    public InputStream getBody() {
        return body;
    }

    public int getStatusCode() {return statusCode;}

    public String getURL() {
        return url;
    }
}
