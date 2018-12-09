package html_parser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import html_parser.interfaces.IHtmlParser;
import html_parser.models.Link;
import html_parser.sevices.HtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.Request;
import utils.interfaces.IResponse;

public class Main {
    public static void main(String argc[]) {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        LinksValidation linkValidation = new LinksValidation();

        try {
            Future<IResponse> response = executor.submit(new Request(new URL("https://mail.ru/")));
            IResponse result = response.get();
            Document doc = Jsoup.parse(result.getBody(), "UTF-8", "");
            IHtmlParser htmlParser = new HtmlParser(executor);
            List<Link> links = htmlParser.getLinks(doc);
            for (Link link: links) {
                System.out.println(link.getName() + " " + link.getStatus());
            }
            System.out.println(links.size());
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
