package html_parser.interfaces;

import html_parser.models.Link;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.util.List;

public interface IHtmlParser {
    List<Link> getLinks(Document html, IParam param) throws MalformedURLException;
}
