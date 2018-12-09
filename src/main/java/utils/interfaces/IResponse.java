package utils.interfaces;

import java.io.InputStream;

public interface IResponse {
    InputStream getBody();
    int getStatusCode();
    String getURL();
}
