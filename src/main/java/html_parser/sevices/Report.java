package html_parser.sevices;

import com.opencsv.CSVWriter;
import html_parser.interfaces.IReport;
import html_parser.models.Link;
import html_parser.models.LinksSource;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Report implements IReport {
    private String fileName;

    public Report(String filename) throws Exception {
        this.fileName = filename + ".csv";
        new CSVWriter(new FileWriter(fileName));
    }

    public void printToReport(LinksSource linksSource) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(fileName,true));
        List<Link> normalLinks = linksSource.getNormalLinks();
        List<Link> brokenLinks = linksSource.getBrokenLinks();
        writer.writeNext(stringsToArr("Parent doc name: " + linksSource.getName()));
        writer.writeNext(stringsToArr("All links quantity: " + linksSource.getAllLinks().size()));

        if (normalLinks.size() != 0) {
            writer.writeNext(stringsToArr(""));
            writer.writeNext(stringsToArr("Normal links"));
            writer.writeNext(stringsToArr("quantity: " + normalLinks.size()));
            writer.writeNext(stringsToArr(""));
            writeLinksToFile(writer, normalLinks);
        }

        if (brokenLinks.size() != 0) {
            writer.writeNext(stringsToArr(""));
            writer.writeNext(stringsToArr("Broken links"));
            writer.writeNext(stringsToArr("quantity: " + brokenLinks.size()));
            writer.writeNext(stringsToArr(""));
            writeLinksToFile(writer, brokenLinks);
        }

        writer.writeNext(stringsToArr(""));
        writer.close();
    }

    private String[] stringsToArr(String ... strings) {
        return strings;
    }

    private void writeLinksToFile(CSVWriter writer, List<Link> links) {
        String[] records = new String[2];

        for (Link link: links) {
            records[0] = link.getName();
            records[1] = String.valueOf(link.getStatus());
            writer.writeNext(records);
        }
    }
}
