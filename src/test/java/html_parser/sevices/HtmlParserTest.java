package html_parser.sevices;

import html_parser.LinksValidation;
import html_parser.enums.ParamType;
import html_parser.models.Link;
import html_parser.models.LinksSource;
import html_parser.models.Param;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class HtmlParserTest {
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private HtmlParser htmlParser = new HtmlParser(executor, new LinksValidation());

    @Test
    public void shouldGetLinks() {
        try {
            Document doc = Jsoup.parse(new File("example.html"), "UTF-8");
            Param param = new Param("example.html", ParamType.File);
            param.setDomain("http://example.com");
            List<Link> result = htmlParser.getLinks(doc, param);
            List<Integer> resultStatus = new ArrayList<>();
            List<Integer> expectResultStatus = Arrays.asList(200, 200, 404);
            result.forEach(value -> resultStatus.add(value.getStatus()));
            List<String> resultName = new ArrayList<>();
            List<String> expectResultName = Arrays.asList("https://yandex.ru", "https://mail.ru/", "http://example.com/news");
            result.forEach(value -> resultName.add(value.getName()));

            assertArrayEquals(expectResultName.toArray(), resultName.toArray());
            assertArrayEquals(expectResultStatus.toArray(), resultStatus.toArray());
        } catch (Exception ignored) {
        }
    }

    @Test
    public void shouldCheckFilterNormalLinks() {
        try {
            Document doc = Jsoup.parse(new File("example.html"), "UTF-8");
            Param param = new Param("example.html", ParamType.File);
            param.setDomain("http://example.com");
            List<Link> result = htmlParser.getLinks(doc, param);
            LinksSource linksSource = new LinksSource("example.html", ParamType.File);
            linksSource.setLinks(result);
            List<String> expectNormalLinksName = Arrays.asList("https://yandex.ru", "https://mail.ru/");
            List<String> resultNormalLinksName = new ArrayList<>();
            linksSource.getNormalLinks().forEach(value -> resultNormalLinksName.add(value.getName()));
            assertArrayEquals(expectNormalLinksName.toArray(), resultNormalLinksName.toArray());

            List<Integer> expectNormalLinksStatus = Arrays.asList(200, 200);
            List<Integer> resultNormalLinksStatus = new ArrayList<>();
            linksSource.getNormalLinks().forEach(value -> resultNormalLinksStatus.add(value.getStatus()));
            assertArrayEquals(expectNormalLinksStatus.toArray(), resultNormalLinksStatus.toArray());
        } catch (Exception ignored) {
        }
    }

    @Test
    public void shouldCheckFilterBrokenLinks() {
        try {
            Document doc = Jsoup.parse(new File("example.html"), "UTF-8");
            Param param = new Param("example.html", ParamType.File);
            param.setDomain("http://example.com");
            List<Link> result = htmlParser.getLinks(doc, param);
            LinksSource linksSource = new LinksSource("example.html", ParamType.File);
            linksSource.setLinks(result);
            List<String> expectBrokenLinksName = Arrays.asList("http://example.com/news");
            List<String> resultBrokenLinksName = new ArrayList<>();
            linksSource.getBrokenLinks().forEach(value -> resultBrokenLinksName.add(value.getName()));
            assertArrayEquals(expectBrokenLinksName.toArray(), resultBrokenLinksName.toArray());

            List<Integer> expectBrokenLinksStatus = Arrays.asList(404);
            List<Integer> resultBrokenLinksStatus = new ArrayList<>();
            linksSource.getBrokenLinks().forEach(value -> resultBrokenLinksStatus.add(value.getStatus()));
            assertArrayEquals(expectBrokenLinksStatus.toArray(), resultBrokenLinksStatus.toArray());
        } catch (Exception ignored) {
        }
    }

    @Test
    public void shouldGetAllLinks() {
        try {
            Document doc = Jsoup.parse(new File("example.html"), "UTF-8");
            Param param = new Param("example.html", ParamType.File);
            param.setDomain("http://example.com");
            List<Link> result = htmlParser.getLinks(doc, param);
            LinksSource linksSource = new LinksSource("example.html", ParamType.File);
            linksSource.setLinks(result);
            List<String> expectResultName = Arrays.asList("https://yandex.ru", "https://mail.ru/", "http://example.com/news");
            List<String> resultName = new ArrayList<>();
            linksSource.getAllLinks().forEach(value -> resultName.add(value.getName()));
            assertArrayEquals(expectResultName.toArray(), resultName.toArray());

        } catch (Exception ignored) {
        }
    }
}