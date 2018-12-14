package html_parser.interfaces;

import html_parser.models.LinksSource;

import java.io.IOException;

public interface IReport {
    void printToReport(LinksSource linksSource) throws IOException;
}

