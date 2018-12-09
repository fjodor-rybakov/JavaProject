package utils;

import utils.interfaces.IResponse;

import java.io.InputStream;

public class Response implements IResponse {
    private InputStream body;
    private int statusCode;
    private String url;

    public Response(InputStream body, String url, int statusCode) {
        this.body = body;
        this.url = url;
        this.statusCode = statusCode;
    }

    @Override
    public InputStream getBody() {
        return body;
    }

    @Override
    public int getStatusCode() {return statusCode;}

    @Override
    public String getURL() {
        return url;
    }
}
