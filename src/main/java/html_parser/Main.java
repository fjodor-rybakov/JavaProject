package html_parser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Request;
import utils.interfaces.IResponse;

public class Main {
    public static void main(String argc[]) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        LinksValidation linkValidation = new LinksValidation();

        try {
            Future<IResponse> response = executor.submit(new Request(new URL("https://mail.ru/")));
            Document doc = Jsoup.parse(response.get().getBody(), "UTF-8", "");
            Elements links = doc.select("a");
            List<String> linksStr = new ArrayList<>();
            for (Element link : links) {
                linksStr.add(link.attr("href"));
            }
            linksStr = linkValidation.getValidLinks(linksStr, "https", "mail.ru");
            for (String linkstr: linksStr) {
                System.out.println(linkstr);
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
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
