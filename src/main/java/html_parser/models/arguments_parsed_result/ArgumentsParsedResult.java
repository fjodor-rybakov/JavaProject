package html_parser.models.arguments_parsed_result;

import html_parser.interfaces.IHtmlQueue;

public class ArgumentsParsedResult {
    private IHtmlQueue htmlQueue;
    private String outFileName;

    public ArgumentsParsedResult(IHtmlQueue htmlQueue, String outFileName) {
        this.htmlQueue = htmlQueue;
        this.outFileName = outFileName;
    }

    public String getOutFileName() {
        return outFileName;
    }

    public IHtmlQueue getHtmlQueue() {
        return htmlQueue;
    }
}
