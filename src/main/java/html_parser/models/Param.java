package html_parser.models;

import html_parser.enums.ParamType;
import html_parser.interfaces.IParam;

public class Param implements IParam {
    private ParamType type;
    private String name;
    private String domain;

    public Param(String name, ParamType type) {
        this.name = name;
        this.type = type;
        domain = "";
    }

    public ParamType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDomain() { return  domain; }

    public void setDomain(String domain) {this.domain = domain;}
}
