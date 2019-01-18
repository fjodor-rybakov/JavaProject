package utils;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import utils.interfaces.IRequest;
import utils.interfaces.IResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
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
        Connection.Response response = null;
        try {
            response =  Jsoup
                    .connect(url.toExternalForm())
                    .followRedirects(false)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                    .timeout(5500)
                    .execute();

            statusCode = response.statusCode();
        } catch (SocketTimeoutException e) {
            statusCode = 100;
        } catch (HttpStatusException e) {
            statusCode = e.getStatusCode();
        } catch (IOException e) {
            statusCode = 500;
        }
    }
}
