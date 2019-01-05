package html_parser.models;

import html_parser.enums.ParamType;
import html_parser.interfaces.IParam;
import html_parser.interfaces.IParams;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParamsTest {
    @Test
    public void shouldParseParams() {
        String[] args = new String[]{"--files", "example.html", "http://example.com", "--links", "https://google.com"};
        IParams params = new Params(args);
        List<String> result = new ArrayList<>();
        for (IParam param : params.getParams()) {
            result.add(param.getName());
        }
        List<String> expect = Arrays.asList("example.html", "https://google.com");
        assertArrayEquals(expect.toArray(), result.toArray());
    }

    @Test
    public void shouldGetParamTypes() {
        String[] args = new String[]{"--files", "example.html", "http://example.com", "--links", "https://google.com"};
        IParams params = new Params(args);
        List<ParamType> result = new ArrayList<>();
        for (IParam param : params.getParams()) {
            result.add(param.getType());
        }
        List<ParamType> expect = Arrays.asList(ParamType.File, ParamType.Link);
        assertArrayEquals(expect.toArray(), result.toArray());
    }
}