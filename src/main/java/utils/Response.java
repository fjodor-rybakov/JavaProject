package utils;

import org.jsoup.nodes.Document;
import utils.interfaces.IResponse;

import java.io.InputStream;

public class Response implements IResponse {
    private Document body;
    private int statusCode;
    private String url;

    Response(Document body, String url, int statusCode) {
        this.body = body;
        this.url = url;
        this.statusCode = statusCode;
    }

    public Document getBody() {
        return body;
    }

    public int getStatusCode() {return statusCode;}

    public String getURL() {
        return url;
    }
}
