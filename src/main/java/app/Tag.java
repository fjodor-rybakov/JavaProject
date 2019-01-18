package app;

import app.interfaces.ITag;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class Tag implements ITag {
    private Element element;

    public Tag(Element element){
        this.element = element;
    }

    public ITag getSingleByTagName(String string) {
        List<ITag> result = new ArrayList<>();

        List<Element> elements = element.select(string);
        for (Element tag : elements) {
            result.add(new Tag(tag));
        }

        if (result.size() == 0) {
            return null;
        }

        return result.get(0);
    }

    public String getBody() {
        return element.html();
    }
}
