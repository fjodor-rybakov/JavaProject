package html_parser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LinksValidationTest {
    private LinksValidation linksValidation = new LinksValidation();

    @Test
    public void shouldCorrectDataValidator() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("https://vk.com", "https://mail.ru", "https://www.youtube.com", "https://www.google.ru")
        );
        ArrayList<String> result = linksValidation.getValidLinks(input, "", "");
        assertArrayEquals(result.toArray(), input.toArray());
    }

    @Test
    public void shouldIncorrectDataValidator() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("https://vk.com", "/fave", "./friends", "../im", "", "https://vk.com/#cost", "/", "//vk.com/music")
        );
        ArrayList<String> result = linksValidation.getValidLinks(input, "https", "vk.com");
        ArrayList<String> expect = new ArrayList<>(
                Arrays.asList("https://vk.com", "https://vk.com/fave", "https://vk.com/friends", "https://vk.com/im", "https://vk.com", "https://vk.com/music")
        );
        assertArrayEquals(result.toArray(), expect.toArray());
    }
}