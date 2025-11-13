package academy.dictionary.providers;

import academy.dictionary.WordEntry;
import academy.dictionary.WordsDictionary;
import academy.enums.Category;
import academy.enums.Difficulty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class JsonDictionaryProvider extends WordsDictionary {

    public JsonDictionaryProvider(String filename) {
        super(new Random(), loadWordsMapFromJson(filename));
    }

    private static Map<Category, Map<Difficulty, List<WordEntry>>> loadWordsMapFromJson(String filename) {
        Map<Category, Map<Difficulty, List<WordEntry>>> wordsMap = new HashMap<>();

        try (InputStream inputStream = JsonDictionaryProvider.class.getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found in resources: " + filename);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> wordEntries = objectMapper.readValue(
                inputStream,
                new TypeReference<List<Map<String, String>>>() {}
            );

            for (Map<String, String> entry : wordEntries) {
                Category category = parseCategory(entry.get("category"));
                Difficulty difficulty = parseDifficulty(entry.get("difficulty"));
                String word = entry.get("word");
                String hint = entry.get("hint") != null ? entry.get("hint") : "";

                if (category != null && difficulty != null) {
                    WordEntry wordEntry = new WordEntry(word, hint);
                    wordsMap
                        .computeIfAbsent(category, k -> new EnumMap<>(Difficulty.class))
                        .computeIfAbsent(difficulty, k -> new ArrayList<>())
                        .add(wordEntry);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dictionary from JSON", e);
        }

        return wordsMap;
    }

    private static Category parseCategory(String value) {
        if (value == null) return null;
        for (Category category : Category.values()) {
            if (category.getValue().equalsIgnoreCase(value.trim())) {
                return category;
            }
        }
        return null;
    }

    private static Difficulty parseDifficulty(String value) {
        if (value == null) return null;
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.getValue().equalsIgnoreCase(value.trim())) {
                return difficulty;
            }
        }
        return null;
    }
}
