package utils;

import utils.interfaces.IRequest;
import utils.interfaces.IResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class StatusCodeRequest implements IRequest {
    private URL url;
    private int statusCode;

    public StatusCodeRequest(URL url) {
        this.url = url;
    }

    @Override
    public IResponse call() {
        writeResult();
        return new Response(null, url.toString(), statusCode);
    }

    private void writeResult() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            statusCode = connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            statusCode = 500;
        }
    }
}
