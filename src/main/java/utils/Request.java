package utils;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.interfaces.IRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

public class Request implements IRequest {
    private URL url;
    private Document body;
    private int statusCode;

    public Request(URL url) {
        this.url = url;
    }

    public Response call() {
        writeResult();
        return new Response(body, url.toString(), statusCode);
    }

    private void writeResult() {
        body = null;
        try {
            Connection.Response  response =  Jsoup
                    .connect(url.toExternalForm())
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                    .timeout(3000)
                    .execute();

            body = response.parse();
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
