package html_parser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import html_parser.interfaces.IHtmlParser;
import html_parser.interfaces.IParam;
import html_parser.models.Link;
import html_parser.models.Params;
import html_parser.models.Source;
import html_parser.sevices.HtmlParser;
import html_parser.sevices.Report;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.Request;
import utils.interfaces.IResponse;

public class Main {
    public static void main(String args[]) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            Params params = new Params(args);
            Report report = new Report(params.getReportName());
            Document doc = new Document("");
            for (IParam param : params.getParams()) {
                Source source = new Source(param.getName());
                if (param.getType().equals("link")) {
                    Future<IResponse> response = executor.submit(new Request(new URL(param.getName())));
                    IResponse result = response.get();
                    doc = Jsoup.parse(result.getBody(), "UTF-8", "");
                } else if (param.getType().equals("file")) {
                    doc = Jsoup.parse(new File(param.getName()), "UTF-8");
                }
                IHtmlParser htmlParser = new HtmlParser(executor, new LinksValidation(), param);
                List<Link> links = htmlParser.getLinks(doc);
                source.setLinks(links);
                report.add(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
