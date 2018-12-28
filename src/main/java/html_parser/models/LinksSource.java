package html_parser.models;

import html_parser.enums.ParamType;
import html_parser.interfaces.ILinksSource;

import java.util.ArrayList;
import java.util.List;

public class LinksSource implements ILinksSource {
    private String name;
    private ParamType type;
    private List<Link> brokenLinks = new ArrayList<>();
    private List<Link> normalLinks = new ArrayList<>();
    private List<Link> allLinks = new ArrayList<>();


    public LinksSource(String name, ParamType type) {
        this.type = type;
        this.name = name;
    }

    public void setLinks(List<Link> links) {
        normalLinks.clear();
        brokenLinks.clear();
        for (Link link : links) {
            if (link.getStatus() < 400) {
                normalLinks.add(link);
            } else {
                brokenLinks.add(link);
            }
        }
        allLinks = links;
    }

    public List<Link> getBrokenLinks() {
        return brokenLinks;
    }

    public List<Link> getNormalLinks() {
        return normalLinks;
    }

    public List<Link> getAllLinks() {
        return allLinks;
    }

    public String getName() {
        return name;
    }

    public ParamType getType() {
        return type;
    }
}
