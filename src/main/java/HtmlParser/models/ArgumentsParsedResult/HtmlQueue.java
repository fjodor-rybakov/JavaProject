package htmlparser.models.ArgumentsParsedResult;

import htmlparser.interfaces.IHtmlQueue;

import java.util.ArrayDeque;
import java.util.Queue;

public class HtmlQueue implements IHtmlQueue {
    private Queue<String> fileNames;
    private Queue<String> linkNames;

    public HtmlQueue() {
        fileNames = new ArrayDeque<String>();
        linkNames = new ArrayDeque<String>();
    }

    @Override
    public void addFileName(String fileName) {
        fileNames.add(fileName);
    }

    @Override
    public void addLinkName(String linkName) {
        linkNames.add(linkName);
    }

    @Override
    public String getFileName() {
        if (fileNames.isEmpty()) {
            return null;
        }

        return fileNames.poll();
    }

    @Override
    public String getLinkName() {
        if (linkNames.isEmpty()) {
            return null;
        }

        return linkNames.poll();
    }
}
