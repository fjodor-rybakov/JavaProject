package html_parser.interfaces;

public interface IHtmlQueue {
    void addFileName(String fileName);
    void addLinkName(String linkName);
    String getFileName();
    String getLinkName();
}
