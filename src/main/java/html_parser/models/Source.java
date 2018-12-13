package html_parser.models;

import java.util.ArrayList;
import java.util.List;

public class Source {
    private String name;
    private List<Link> brokenLinks = new ArrayList<>();
    private List<Link> normalLinks = new ArrayList<>();
    private List<Link> allLinks = new ArrayList<>();


    public Source(String name) {
        this.name = name;
    }

    public void setLinks(List<Link> links) {
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
}
