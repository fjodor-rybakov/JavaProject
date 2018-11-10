package utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public ArrayList<String> checkAllLinks(ArrayList<String> allLinks, String protocol, String domain) {
        String template = "/(ftp|http|https):\\/\\/(\\w+:?\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?/";
        Pattern pattern = Pattern.compile(template);
        ArrayList<String> resultArrayLinks = new ArrayList<String>();
        String restoringUrl;

        for (String item : allLinks) {
            Matcher matcher = pattern.matcher(item);

            if (item.isEmpty()) {
                continue;
            }

            if (item.indexOf('#') != -1) {
                continue;
            }

            if (matcher.matches()) {
                resultArrayLinks.add(item);
            } else {
                restoringUrl = this.restoreUrl(item, protocol, domain);
                resultArrayLinks.add(restoringUrl);
            }
        }

        return resultArrayLinks;
    }

    private String restoreUrl(String url, String protocol, String domain) {
        if (url.charAt(0) == '.' && url.charAt(1) == '.') {
            return protocol + "//" + domain + url.substring(2);
        }

        if (url.charAt(0) == '.') {
            return protocol + "//" + domain + url.substring(1);
        }

        if (url.charAt(0) == '/' && url.charAt(1) == '/') {
            return protocol + url;
        }

        if (url.charAt(0) == '/') {
            return protocol + "//" + domain + url;
        } else {
            return protocol + "//" + domain + "/" + url;
        }
    }
}
