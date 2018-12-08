package htmlparser.interfaces;

import htmlparser.models.ArgumentsParsedResult.ArgumentsParsedResult;

import java.util.List;

public interface IArgumentsParser {
    ArgumentsParsedResult getParsed(List<String> args);
}
