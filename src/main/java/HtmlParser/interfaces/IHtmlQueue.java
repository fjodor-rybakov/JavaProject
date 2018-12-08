package htmlparser.interfaces;

public interface IHtmlQueue {
    void addFileName(String fileName);
    void addLinkName(String linkName);
    String getFileName();
    String getLinkName();
}
