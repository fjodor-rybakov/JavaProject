package html_parser.models;

import html_parser.enums.ParamType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinksSourceTest {
    @Test
    public void shouldCheckLinkSource() {
        Param param = new Param("example.html", ParamType.File);
        param.setDomain("http://example.com");
        LinksSource linksSource = new LinksSource("example.html", ParamType.File);
        String expectResultName = "example.html";
        assertEquals(expectResultName, linksSource.getName());
        ParamType expectResultParamType = ParamType.File;
        assertEquals(expectResultParamType, linksSource.getType());
    }
}