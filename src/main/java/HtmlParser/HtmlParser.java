package HtmlParser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utils;

public class HtmlParser {
    public static void main(String argc[]) {
        Utils utils = new Utils();

        Document htmlFile = null;
        try {
            htmlFile = Jsoup.parse(new File("src/main/java/HtmlParser/example.html"), "ISO-8859-1");
            Elements links = htmlFile.select("a");
            for (Element link : links) {
                System.out.println(link.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
