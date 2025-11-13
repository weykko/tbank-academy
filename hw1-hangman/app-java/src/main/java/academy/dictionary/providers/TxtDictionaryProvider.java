package academy.dictionary.providers;

import academy.dictionary.WordEntry;
import academy.dictionary.WordsDictionary;
import academy.enums.Category;
import academy.enums.Difficulty;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TxtDictionaryProvider extends WordsDictionary {

    public TxtDictionaryProvider(String filename) {
        super(new Random(), loadWordsMapFromTxt(filename));
    }

    private static Map<Category, Map<Difficulty, List<WordEntry>>> loadWordsMapFromTxt(String filename) {
        Map<Category, Map<Difficulty, List<WordEntry>>> wordsMap = new HashMap<>();

        try (InputStream inputStream = TxtDictionaryProvider.class.getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found in resources: " + filename);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length >= 3) {
                        Category category = parseCategory(parts[0]);
                        Difficulty difficulty = parseDifficulty(parts[1]);
                        String word = parts[2].trim();
                        String hint = parts.length > 3 ? parts[3].trim() : "";

                        if (category != null && difficulty != null) {
                            WordEntry wordEntry = new WordEntry(word, hint);
                            wordsMap
                                .computeIfAbsent(category, k -> new EnumMap<>(Difficulty.class))
                                .computeIfAbsent(difficulty, k -> new ArrayList<>())
                                .add(wordEntry);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dictionary from TXT", e);
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
