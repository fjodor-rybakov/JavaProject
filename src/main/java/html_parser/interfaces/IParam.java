package html_parser.interfaces;

import html_parser.enums.ParamType;

public interface IParam {
    ParamType getType();
    String getName();
    String getDomain();
    void setDomain(String domain);
}
