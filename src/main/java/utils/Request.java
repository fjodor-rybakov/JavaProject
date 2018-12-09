package utils;

import utils.interfaces.IRequest;

import java.io.IOException;
import java.net.URL;

public class Request implements IRequest {
    private URL url;

    public Request(URL url) {
        this.url = url;
    }

    @Override
    public Response call() throws IOException {
        return new Response(url.openStream());
    }
}
