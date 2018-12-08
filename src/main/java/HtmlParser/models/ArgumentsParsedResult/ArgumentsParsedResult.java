package htmlparser.models.ArgumentsParsedResult;

import htmlparser.interfaces.IHtmlQueue;

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
