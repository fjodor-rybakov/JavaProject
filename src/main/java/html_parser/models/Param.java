package html_parser.models;

import html_parser.interfaces.IParam;

public class Param implements IParam {
    private String type;
    private String name;

    public Param(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
