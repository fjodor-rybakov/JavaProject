package html_parser.sevices;

import html_parser.interfaces.IHtmlParser;
import html_parser.interfaces.ILinksValidation;
import html_parser.interfaces.IParam;
import html_parser.models.Link;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Request;
import utils.Response;
import utils.StatusCodeRequest;
import utils.interfaces.IResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class HtmlParser implements IHtmlParser {
    private ExecutorService executor;
    private ILinksValidation linksValidation;
    private URL url;
    private IParam doc;

    public HtmlParser(ExecutorService executor, ILinksValidation linksValidation, IParam param) throws Exception {
        this.executor = executor;
        this.linksValidation = linksValidation;
        doc = param;
        if (param.getType().equals("link")) {
            this.url = new URL(param.getName());
        }
    }

    @Override
    public List<Link> getLinks(Document html) {
        List<Link> result = new ArrayList<>();

        List<String> stringLinks = getStringLinks(html);
        if (doc.getType().equals("link")) {
            stringLinks = linksValidation.getValidLinks(stringLinks, url.getProtocol(), url.getHost());
        } else {
            stringLinks = linksValidation.getValidLinks(stringLinks, "", "");
        }
        List<Future<IResponse>> responseFutures = new ArrayList<>();
        for(String linkString: stringLinks) {
            try {
                Future<IResponse> responseFuture = executor.submit(new StatusCodeRequest(new URL(linkString)));
                responseFutures.add(responseFuture);
            } catch (MalformedURLException ignored) {
            }
        }

        for(Future<IResponse> responseFuture: responseFutures) {
            try {
                IResponse response = responseFuture.get();
                result.add(new Link(response.getURL(), response.getStatusCode()));
            } catch (InterruptedException | ExecutionException ignored) {
            }
        }

        return result;
    }

    private List<String> getStringLinks(Document html) {
        Elements links = html.select("a");
        List<String> linksStr = new ArrayList<>();
        for (Element link : links) {
            linksStr.add(link.attr("href"));
            linksStr.add(link.attr("src"));
        }

        return linksStr;
    }
}
