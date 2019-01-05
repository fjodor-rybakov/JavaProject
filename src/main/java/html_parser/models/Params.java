package html_parser.models;

import java.util.List;

import html_parser.enums.ParamType;
import html_parser.interfaces.IParam;
import html_parser.interfaces.IParams;

import java.util.ArrayList;

public class Params implements IParams {
    private List<IParam> params;

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
                default:
                    if (mode == null) {
                        throw new IllegalArgumentException("Print so: *.exe --files|--links <link|file...> --out report.csv");
                    }
                    addParam(arg, mode);
                    if (mode == ParamType.File) {
                        mode = ParamType.FileDomain;
                    } else if (mode == ParamType.FileDomain) {
                        mode = ParamType.File;
                    }
            }
        }
    }

    private void addParam(String value, ParamType mode) {
        IParam param;
        if (mode == ParamType.FileDomain) {
            int lastI = params.size() == 0 ? 0: params.size() - 1;
            param = params.get(lastI);
            params.remove(lastI);
            param.setDomain(value);
        } else {
            param = new Param(value, mode);
        }
        params.add(param);
    }

    public List<IParam> getParams() {
        return params;
    }
}
