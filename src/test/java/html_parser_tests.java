import app.AppBaseConsts;
import app.Configuration;
import app.interfaces.IConfiguration;
import html_parser.LinksValidation;
import html_parser.enums.ParamType;
import html_parser.interfaces.IParam;
import html_parser.interfaces.IParams;
import html_parser.interfaces.IReport;
import html_parser.models.LinksSource;
import html_parser.models.Param;
import html_parser.models.Params;
import html_parser.sevices.HtmlParser;
import html_parser.sevices.Report;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import html_parser.models.Link;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class html_parser_tests {
    private LinksValidation linksValidation = new LinksValidation();
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private HtmlParser htmlParser = new HtmlParser(executor, new LinksValidation());

    @Test
    public void shouldCorrectDataValidator() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("https://vk.com", "https://mail.ru", "https://www.youtube.com", "https://www.google.ru")
        );
        ArrayList<String> result = linksValidation.getValidLinks(input, "", "");
        assertArrayEquals(result.toArray(), input.toArray());
    }

    @Test
    public void shouldIncorrectDataValidator() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("https://vk.com", "/fave", "./friends", "../im", "", "https://vk.com/#cost", "/", "//vk.com/music")
        );
        ArrayList<String> result = linksValidation.getValidLinks(input, "https", "vk.com");
        ArrayList<String> expect = new ArrayList<>(
                Arrays.asList("https://vk.com", "https://vk.com/fave", "https://vk.com/friends", "https://vk.com/im", "https://vk.com", "https://vk.com/music")
        );
        assertArrayEquals(result.toArray(), expect.toArray());
    }

    @Test
    public void shouldParseParams() {
        String[] args = new String[]{"--files", "example.html", "http://example.com", "--links", "https://google.com", "--out", "example.csv"};
        IParams params = new Params(args);
        List<String> result = new ArrayList<>();
        for (IParam param : params.getParams()) {
            result.add(param.getName());
        }
        result.add(params.getReportName());
        List<String> expect = Arrays.asList("example.html", "https://google.com", "example.csv");
        assertArrayEquals(expect.toArray(), result.toArray());
    }

    @Test
    public void shouldErrorEmptyArgumentParseParams() {
        String[] args = new String[]{"--files", "example.html", "http://example.com", "--links", "https://google.com", "--out", "example.csv", "qwe.csv"};
        try {
            new Params(args);
        } catch (Exception err) {
            String expectMessage = "Out file can be only single";
            assertEquals(expectMessage, err.getMessage());
        }
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
    public void shouldCheckLinkSource() {
        Param param = new Param("example.html", ParamType.File);
        param.setDomain("http://example.com");
        LinksSource linksSource = new LinksSource("example.html", ParamType.File);
        String expectResultName = "example.html";
        assertEquals(expectResultName, linksSource.getName());
        ParamType expectResultParamType = ParamType.File;
        assertEquals(expectResultParamType, linksSource.getType());
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
    public void PrintReportIfSourceIsFile() {
        LinksSource source = new LinksSource("index.html", ParamType.File);
        List<Link> links = new ArrayList<>();
        links.add(new Link("link", 200));
        source.setLinks(links);
        try {
            IReport report = new Report(source);
            report.printReport();

            Scanner scanner = new Scanner(new File("index.csv"));
            String result = scanner.nextLine();

            assertEquals("\"link\",200", result);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void PrintReportIfSourceIsLink() {
        LinksSource source = new LinksSource("https://link.com", ParamType.File);
        List<Link> links = new ArrayList<>();
        links.add(new Link("link", 200));
        source.setLinks(links);
        try {
            IReport report = new Report(source);
            report.printReport();

            Scanner scanner = new Scanner(new File("link.csv"));
            String result = scanner.nextLine();

            assertEquals("\"link\",200", result);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void GetThreadsCountFromConfiguration() {
        try {
            IConfiguration configuration = new Configuration("src/test/java/properties.test.xml");
            String threadsStr = configuration
                    .getByTagName("app-settings")
                    .get(0)
                    .getByTagName("thread-pools")
                    .get(0)
                    .getBody();
            assertEquals("3", threadsStr);
        } catch (IOException ignored) {
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
