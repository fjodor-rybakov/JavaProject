package html_parser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import html_parser.Enums.ParamType;
import html_parser.interfaces.IHtmlParser;
import html_parser.interfaces.IParam;
import html_parser.interfaces.IParams;
import html_parser.interfaces.IReport;
import html_parser.models.Link;
import html_parser.models.Params;
import html_parser.models.LinksSource;
import html_parser.sevices.HtmlParser;
import html_parser.sevices.Report;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.Request;
import utils.interfaces.IResponse;

public class Main {
    public static void main(String args[]) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        IHtmlParser htmlParser = new HtmlParser(executor, new LinksValidation());

        try {
            IParams params = new Params(args);
            IReport report = new Report(params.getReportName());
            Document doc = new Document("");
            for (IParam param : params.getParams()) {
                LinksSource linksSource = new LinksSource(param.getName());
                if (param.getType() == ParamType.Link) {
                    Future<IResponse> response = executor.submit(new Request(new URL(param.getName())));
                    IResponse result = response.get();
                    doc = Jsoup.parse(result.getBody(), "UTF-8", "");
                } else if (param.getType() == ParamType.File) {
                    doc = Jsoup.parse(new File(param.getName()), "UTF-8");
                }
                List<Link> links = htmlParser.getLinks(doc, param);
                linksSource.setLinks(links);
                report.printToReport(linksSource);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        executor.shutdown();
    }
}
