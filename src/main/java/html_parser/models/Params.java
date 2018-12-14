package html_parser.models;

import java.util.List;
import html_parser.Enums.ParamType;
import html_parser.interfaces.IParam;
import html_parser.interfaces.IParams;

import java.util.ArrayList;

public class Params implements IParams {
    private List<IParam> params;
    private String reportName = "";

    public Params(String[] args) {
        params = new ArrayList<>();
        ParamType mode = null;
        for (String arg : args) {
            switch (arg) {
                case ("--files"):
                    mode = ParamType.File;
                    break;
                case ("--links"):
                    mode = ParamType.Link;
                    break;
                case ("--out"):
                    mode = ParamType.OutFile;
                    break;
                default:
                    if (mode == null) {
                        throw new IllegalArgumentException("Print so: *.exe --files|--links <link|file...> --out report.csv");
                    }
                    addParam(arg, mode);
            }
        }
    }

    private void addParam(String value, ParamType mode) {
        if (mode == ParamType.OutFile) {
            if (!reportName.equals("")) {
                throw new IllegalArgumentException("Out file can be only single");
            }
            reportName = value.equals("") ? "report" : value;
            return;
        }
        Param param = new Param(value, mode);
        params.add(param);
    }

    public List<IParam> getParams() {
        return params;
    }

    public String getReportName() {
        return reportName;
    }
}
