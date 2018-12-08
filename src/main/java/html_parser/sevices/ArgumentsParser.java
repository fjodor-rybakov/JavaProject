package html_parser.sevices;

import html_parser.interfaces.IArgumentsParser;
import html_parser.interfaces.IHtmlQueue;
import html_parser.models.arguments_parsed_result.ArgumentsParsedResult;
import html_parser.models.arguments_parsed_result.HtmlQueue;

import java.util.List;

public class ArgumentsParser implements IArgumentsParser {
    private String outFileName = "";
    private IHtmlQueue htmlQueue;

    private enum ArgumentState {
        Files,
        Links,
        OutFile,
        NoState
    }

    ArgumentsParser() {
        this.htmlQueue = new HtmlQueue();
    }


    @Override
    public ArgumentsParsedResult getParsed(List<String> args) {
        ArgumentState state = ArgumentState.NoState;
        for (String arg : args) {
            switch (arg) {
                case "--files":
                    state = ArgumentState.Files;
                    break;
                case "--links":
                    state = ArgumentState.Links;
                    break;
                case "--out":
                    state = ArgumentState.OutFile;
                    break;
                default:
                    locateArgument(arg, state);
                    break;
            }
        }

        return new ArgumentsParsedResult(htmlQueue, outFileName);
    }

    private void locateArgument(String argumnet, ArgumentState state) {
        switch (state) {
            case NoState:
                throw new IllegalArgumentException("Print so: *.exe --files|--links <link|file...> --out report.csv");
            case Files:
                htmlQueue.addFileName(argumnet);
                break;
            case Links:
                htmlQueue.addLinkName(argumnet);
            case OutFile:
                if (!outFileName.equals("")) {
                    throw new IllegalArgumentException("Out file can be only single");
                }
                outFileName = argumnet;
                break;
        }
    }
}
