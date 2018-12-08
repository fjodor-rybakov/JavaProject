package htmlparser.interfaces;

import htmlparser.models.Link;
import org.w3c.dom.Document;

import java.util.List;

public interface IHtmlParser {
    List<Link> getLinks(Document html);
}
