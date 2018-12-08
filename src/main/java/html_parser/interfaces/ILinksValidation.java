package html_parser.interfaces;

import java.util.ArrayList;

public interface ILinksValidation {
    ArrayList<String> getValidLinks(ArrayList<String> allLinks, String protocol, String domain);
}
