package html_parser.sevices;

import html_parser.enums.ParamType;
import html_parser.interfaces.IReport;
import html_parser.models.Link;
import html_parser.models.LinksSource;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {
    @Test
    public void printReportIfSourceIsFile() {
        LinksSource source = new LinksSource("index.html", ParamType.File);
        List<Link> links = new ArrayList<>();
        links.add(new Link("link", 200));
        source.setLinks(links);
        try {
            IReport report = new Report(source);
            report.printReport();

            Scanner scanner = new Scanner(new File("index.csv"));
            String result = scanner.nextLine();

            assertEquals("\"link\",200", result);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void printReportIfSourceIsLink() {
        LinksSource source = new LinksSource("https://link.com", ParamType.File);
        List<Link> links = new ArrayList<>();
        links.add(new Link("link", 200));
        source.setLinks(links);
        try {
            IReport report = new Report(source);
            report.printReport();

            Scanner scanner = new Scanner(new File("link.csv"));
            String result = scanner.nextLine();

            assertEquals("\"link\",200", result);
        } catch (Exception ignored) {
        }
    }
}