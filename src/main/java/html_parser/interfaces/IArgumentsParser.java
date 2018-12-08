package html_parser.interfaces;

import html_parser.models.arguments_parsed_result.ArgumentsParsedResult;
import java.util.List;

public interface IArgumentsParser {
    ArgumentsParsedResult getParsed(List<String> args);
}
