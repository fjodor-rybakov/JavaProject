package html_parser.interfaces;

import java.util.List;

import java.util.ArrayList;

public interface ILinksValidation {
    ArrayList<String> getValidLinks(List<String> allLinks, String protocol, String domain);
}
