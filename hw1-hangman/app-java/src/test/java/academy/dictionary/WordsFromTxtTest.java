package academy.dictionary;

import academy.dictionary.providers.TxtDictionaryProvider;
import academy.enums.Category;
import academy.enums.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordsFromTxtTest {
    private TxtDictionaryProvider dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new TxtDictionaryProvider("dictionary.txt");
    }

    @Test
    void getAllWordsByCategoryAndDifficulty_AnimalsEasy_ShouldContainExpectedWords() {
        Category category = Category.ANIMALS;
        Difficulty difficulty = Difficulty.EASY;


        List<String> words = dictionary.getAllWordsByCategoryAndDifficulty(category, difficulty);

        assertNotNull(words);
        assertFalse(words.isEmpty());
        assertTrue(words.contains("кот"));
        assertFalse(words.contains("хомяк"));
    }
}
