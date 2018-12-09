package utils;

import utils.interfaces.IResponse;

import java.io.InputStream;

public class Response implements IResponse {
    private InputStream body;

    public Response(InputStream body) {
        this.body = body;
    }

    @Override
    public InputStream getBody() {
        return body;
    }
}
