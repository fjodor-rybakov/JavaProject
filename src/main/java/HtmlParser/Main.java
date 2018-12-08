package htmlparser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String argc[]) {
        LinksValidation linkValidation = new LinksValidation();

        Document htmlFile = null;
        try {
            htmlFile = Jsoup.parse(new File("src/main/java/htmlparser/example.html"), "ISO-8859-1");
            Elements links = htmlFile.select("a");
            for (Element link : links) {
                System.out.println(link.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
