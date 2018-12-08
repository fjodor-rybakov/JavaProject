package html_parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String argc[]) {
        LinksValidation linkValidation = new LinksValidation();

        try {
            Document doc = Jsoup.connect("https://mail.ru/").get();
            Elements links = doc.select("a");
            List<String> linksStr = new ArrayList<>();
            for (Element link : links) {
                linksStr.add(link.attr("href"));
            }
            linksStr = linkValidation.getValidLinks(linksStr, "https", "mail.ru");
            for (String linkstr: linksStr) {
                System.out.println(linkstr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Document htmlFile = null;
        try {
            htmlFile = Jsoup.parse(new File("src/main/java/html_parser/example.html"), "ISO-8859-1");
            Elements links = htmlFile.select("a");
            for (Element link : links) {
                System.out.println(link.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}
