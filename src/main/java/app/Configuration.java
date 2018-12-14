package app;

import app.interfaces.IConfiguration;
import app.interfaces.ITag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration implements IConfiguration {
    private Document configuration;

    public Configuration(String configName) throws IOException {
        configuration = Jsoup.parse(new File(configName), "UTF-8");

    }

    @Override
    public List<ITag> getByTagName(String string) {
        List<ITag> result = new ArrayList<>();

        List<Element> elements = configuration.select(string);
        for (Element tag : elements) {
            result.add(new Tag(tag));
        }

        return result;
    }
}
