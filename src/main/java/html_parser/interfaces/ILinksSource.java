package html_parser.interfaces;

import html_parser.models.Link;

import java.util.List;

public interface ILinksSource {
    void setLinks(List<Link> links);

    List<Link> getBrokenLinks();

    List<Link> getNormalLinks();

    List<Link> getAllLinks();

    String getName();
}

