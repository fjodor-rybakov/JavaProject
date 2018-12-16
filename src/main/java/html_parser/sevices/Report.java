package html_parser.sevices;

import html_parser.enums.ParamType;
import html_parser.interfaces.IReport;
import html_parser.models.Link;
import html_parser.models.LinksSource;

import java.io.FileWriter;
import java.util.List;

public class Report implements IReport {
    private String fileName;
    private LinksSource linksSource;
    private FileWriter writer;

    public Report(LinksSource source) throws Exception {
        this.linksSource = source;
        writer = new FileWriter(getFileName());
    }

    private String getFileName() {
        String filename = "";
        if (linksSource.getType() == ParamType.Link) {
            filename = linksSource.getName().split("//")[1].split("\\.")[0];
        } else {
            filename = linksSource.getName().split("\\.")[0];
        }
        filename += ".csv";
        return filename;
    }

    public void printReport() throws Exception {
        List<Link> normalLinks = linksSource.getNormalLinks();
        List<Link> brokenLinks = linksSource.getBrokenLinks();

        if (normalLinks.size() != 0) {
            writeLinksToFile(normalLinks);
        }

        if (brokenLinks.size() != 0) {
            writeLinksToFile(brokenLinks);
        }
        writer.close();
    }

    private void writeLinksToFile(List<Link> links) throws Exception {
        String record = "";

        for (Link link : links) {
            record = "\"" + link.getName() + "\"," + link.getStatus() + "\n";
            writer.write(record);
        }
    }
}
