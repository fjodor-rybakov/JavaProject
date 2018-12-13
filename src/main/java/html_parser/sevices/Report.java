package html_parser.sevices;

import com.opencsv.CSVWriter;
import html_parser.models.Link;
import html_parser.models.Source;
import java.io.FileWriter;

public class Report {
    private String fileName;

    public Report(String filename) throws Exception {
        this.fileName = filename + ".csv";
        new CSVWriter(new FileWriter(fileName));
    }

    public void add(Source source) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(fileName,true));
        String[] record = new String[2];
        for (Link link: source.getAllLinks()) {
            record[0] = link.getName();
            record[1] = String.valueOf(link.getStatus());
            System.out.println(record[0] + " " + record[1]);
            writer.writeNext(record);
        }
    }
}
