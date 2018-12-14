package html_parser.models;

import html_parser.Enums.ParamType;
import html_parser.interfaces.IParam;

public class Param implements IParam {
    private ParamType type;
    private String name;

    Param(String name, ParamType type) {
        this.name = name;
        this.type = type;
    }

    public ParamType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
