package html_parser.sevices;

import html_parser.enums.ParamType;
import html_parser.interfaces.IHtmlParser;
import html_parser.interfaces.ILinksValidation;
import html_parser.interfaces.IParam;
import html_parser.models.Link;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.StatusCodeRequest;
import utils.interfaces.IResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class HtmlParser implements IHtmlParser {
    private ExecutorService executor;
    private ILinksValidation linksValidation;

    public HtmlParser(ExecutorService executor, ILinksValidation linksValidation) {
        this.executor = executor;
        this.linksValidation = linksValidation;
    }

    public List<Link> getLinks(Document html, IParam param) throws MalformedURLException {
        List<Link> result = new ArrayList<>();

        Set<String> uniqueListLinks = new HashSet<>(getStringLinks(html));
        List<String> stringLinks = new ArrayList<>(uniqueListLinks);
        if (param.getType() == ParamType.Link) {
            URL url = new URL(param.getName());
            stringLinks = linksValidation.getValidLinks(stringLinks, url.getProtocol(), url.getHost());
        } else if (param.getType() == ParamType.File) {
            String[] urlParts = param.getDomain().split("//");
            String protocol = urlParts[0].substring(0, urlParts[0].length() - 1);
            String domain = urlParts[1];
            stringLinks = linksValidation.getValidLinks(stringLinks, protocol, domain);
        }
        else {
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
        }
        links = html.select("img");
        for (Element link : links) {
            linksStr.add(link.attr("src"));
        }

        return linksStr;
    }
}
