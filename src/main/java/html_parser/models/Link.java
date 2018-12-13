package html_parser.models;

public class Link {
    private String name;
    private int status;

    public Link( String name, int status ) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }
}
