package utils.interfaces;

import org.jsoup.nodes.Document;

import java.io.InputStream;

public interface IResponse {
    Document getBody();
    int getStatusCode();
    String getURL();
}
