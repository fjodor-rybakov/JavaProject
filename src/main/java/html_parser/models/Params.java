package html_parser.models;

import html_parser.interfaces.IParam;

import java.util.ArrayList;

public class Params {
    private ArrayList<IParam> params = new ArrayList<>();
    private String reportName = "report";

    public Params(String[] args) {
        String mode = "";
        for (String arg : args) {
            switch (arg) {
                case ("--files"):
                    mode = "file";
                    break;
                case ("--links"):
                    mode = "link";
                    break;
                case ("--out"):
                    mode = "out";
                    break;
                default: addParam(arg, mode);
            }
        }
    }

    private void addParam(String value, String mode) {
        if (mode.equals("out")) {
            reportName = value.equals("") ? "report" : value;
            return;
        }
        Param param = new Param(value, mode);
        params.add(param);
    }

    public ArrayList<IParam> getParams() {
        return params;
    }

    public String getReportName() {
        return reportName;
    }
}
