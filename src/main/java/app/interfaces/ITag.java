package app.interfaces;

import java.util.List;

public interface ITag {
    List<ITag> getByTagName(String string);
    String getBody();
}
